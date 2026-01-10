package io.github.cuihairu.croupier.sdk.invoker;

import com.google.protobuf.ByteString;
import io.github.cuihairu.croupier.sdk.v1.CancelJobRequest;
import io.github.cuihairu.croupier.sdk.v1.FunctionServiceGrpc;
import io.github.cuihairu.croupier.sdk.v1.InvokeRequest;
import io.github.cuihairu.croupier.sdk.v1.InvokeResponse;
import io.github.cuihairu.croupier.sdk.v1.JobEvent;
import io.github.cuihairu.croupier.sdk.v1.JobStreamRequest;
import io.github.cuihairu.croupier.sdk.v1.StartJobResponse;
import io.grpc.ManagedChannel;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContext;
import io.grpc.stub.StreamObserver;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Implementation of the Invoker interface using gRPC.
 *
 * <p>This class manages connection to the Croupier server/agent and provides
 * methods for synchronous and asynchronous function invocation with automatic
 * reconnection support.</p>
 */
public class InvokerImpl implements Invoker {

    private static final Logger logger = LoggerFactory.getLogger(InvokerImpl.class);
    private static final Random random = new Random();

    private final InvokerConfig config;
    private final Map<String, Map<String, Object>> schemas;
    private final ReentrantLock connectionLock;
    private final ReentrantReadWriteLock schemasLock;
    private final ScheduledExecutorService reconnectExecutor;

    private ManagedChannel channel;
    private FunctionServiceGrpc.InvokerServiceStub asyncStub;
    private FunctionServiceGrpc.InvokerServiceBlockingStub blockingStub;
    private volatile boolean connected;

    // Reconnection state
    private ScheduledFuture<?> reconnectTask;
    private volatile int reconnectAttempts = 0;
    private volatile boolean isReconnecting = false;

    /**
     * Creates a new Invoker with the given configuration.
     *
     * @param config the invoker configuration
     */
    public InvokerImpl(InvokerConfig config) {
        this.config = config != null ? config : InvokerConfig.createDefault();
        this.schemas = new ConcurrentHashMap<>();
        this.connectionLock = new ReentrantLock();
        this.schemasLock = new ReentrantReadWriteLock();
        this.reconnectExecutor = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "croupier-invoker-reconnect");
            t.setDaemon(true);
            return t;
        });
        this.connected = false;
    }

    /**
     * Creates a new Invoker with default configuration.
     */
    public InvokerImpl() {
        this(InvokerConfig.createDefault());
    }

    @Override
    public void connect() throws InvokerException {
        if (connected) {
            return;
        }

        connectionLock.lock();
        try {
            if (connected) {
                return;
            }

            logger.info("Connecting to Croupier server at: {}", config.getAddress());

            try {
                NettyChannelBuilder builder = NettyChannelBuilder.forTarget(config.getAddress());

                if (config.isInsecure()) {
                    builder.usePlaintext();
                } else {
                    builder.sslContext(createSslContext());
                    if (!config.getServerName().isEmpty()) {
                        builder.overrideAuthority(config.getServerName());
                    }
                }

                channel = builder.build();

                // Test connection with a short deadline
                channel.getState(true);

                asyncStub = FunctionServiceGrpc.newStub(channel);
                blockingStub = FunctionServiceGrpc.newBlockingStub(channel);
                connected = true;
                reconnectAttempts = 0;  // Reset on success

                logger.info("Successfully connected to: {}", config.getAddress());

            } catch (SSLException e) {
                logger.error("Failed to create SSL context: {}", e.getMessage());
                scheduleReconnectIfNeeded();
                throw new InvokerException(
                    InvokerException.ErrorCode.CONNECTION_FAILED,
                    "Failed to create SSL context: " + e.getMessage(),
                    e
                );
            } catch (Exception e) {
                logger.error("Failed to connect to {}: {}", config.getAddress(), e.getMessage());
                scheduleReconnectIfNeeded();
                throw new InvokerException(
                    InvokerException.ErrorCode.CONNECTION_FAILED,
                    "Failed to connect to " + config.getAddress() + ": " + e.getMessage(),
                    e
                );
            }

        } finally {
            connectionLock.unlock();
        }
    }

    @Override
    public String invoke(String functionId, String payload) throws InvokerException {
        return invoke(functionId, payload, null);
    }

    @Override
    public String invoke(String functionId, String payload, InvokeOptions options) throws InvokerException {
        ensureConnected();

        options = options != null ? options : InvokeOptions.create();

        // Client-side validation if schema is set
        schemasLock.readLock().lock();
        try {
            Map<String, Object> schema = schemas.get(functionId);
            if (schema != null) {
                validatePayload(payload, schema);
            }
        } finally {
            schemasLock.readLock().unlock();
        }

        InvokeRequest.Builder requestBuilder = InvokeRequest.newBuilder()
            .setFunctionId(functionId)
            .setPayload(ByteString.copyFromUtf8(payload));

        if (options.getIdempotencyKey() != null && !options.getIdempotencyKey().isEmpty()) {
            requestBuilder.setIdempotencyKey(options.getIdempotencyKey());
        }

        for (Map.Entry<String, String> header : options.getHeaders().entrySet()) {
            requestBuilder.putMetadata(header.getKey(), header.getValue());
        }

        InvokeRequest request = requestBuilder.build();

        final int timeout = options.getTimeout() != null ? options.getTimeout() : config.getTimeout();

        return executeWithRetry(() -> {
            InvokeResponse response = blockingStub
                .withDeadlineAfter(timeout, TimeUnit.MILLISECONDS)
                .invoke(request);
            return response.getPayload().toStringUtf8();
        }, options);
    }

    @Override
    public String startJob(String functionId, String payload) throws InvokerException {
        return startJob(functionId, payload, null);
    }

    @Override
    public String startJob(String functionId, String payload, InvokeOptions options) throws InvokerException {
        ensureConnected();

        options = options != null ? options : InvokeOptions.create();

        InvokeRequest.Builder requestBuilder = InvokeRequest.newBuilder()
            .setFunctionId(functionId)
            .setPayload(ByteString.copyFromUtf8(payload));

        if (options.getIdempotencyKey() != null && !options.getIdempotencyKey().isEmpty()) {
            requestBuilder.setIdempotencyKey(options.getIdempotencyKey());
        }

        for (Map.Entry<String, String> header : options.getHeaders().entrySet()) {
            requestBuilder.putMetadata(header.getKey(), header.getValue());
        }

        InvokeRequest request = requestBuilder.build();

        final int timeout = options.getTimeout() != null ? options.getTimeout() : config.getTimeout();

        return executeWithRetry(() -> {
            StartJobResponse response = blockingStub
                .withDeadlineAfter(timeout, TimeUnit.MILLISECONDS)
                .startJob(request);
            return response.getJobId();
        }, options);
    }

    @Override
    public Publisher<JobEventInfo> streamJob(String jobId) {
        return new JobEventPublisher(this, jobId);
    }

    @Override
    public void cancelJob(String jobId) throws InvokerException {
        ensureConnected();

        CancelJobRequest request = CancelJobRequest.newBuilder()
            .setJobId(jobId)
            .build();

        try {
            blockingStub.cancelJob(request);
        } catch (StatusRuntimeException e) {
            if (isConnectionError(e) && config.getReconnect().isEnabled()) {
                connected = false;
                scheduleReconnectIfNeeded();
            }
            throw handleGrpcError(e);
        }
    }

    @Override
    public void setSchema(String functionId, Map<String, Object> schema) {
        schemasLock.writeLock().lock();
        try {
            schemas.put(functionId, new HashMap<>(schema));
        } finally {
            schemasLock.writeLock().unlock();
        }
        logger.debug("Set schema for function: {}", functionId);
    }

    @Override
    public void close() throws InvokerException {
        // Cancel any pending reconnection
        cancelReconnect();

        // Shutdown reconnect executor
        reconnectExecutor.shutdown();

        connectionLock.lock();
        try {
            if (!connected) {
                return;
            }

            connected = false;

            if (channel != null) {
                try {
                    channel.shutdown();
                    if (!channel.awaitTermination(5, TimeUnit.SECONDS)) {
                        channel.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    channel.shutdownNow();
                    Thread.currentThread().interrupt();
                }
                channel = null;
            }

            asyncStub = null;
            blockingStub = null;

            schemas.clear();

            logger.info("Invoker closed");

        } catch (Exception e) {
            throw new InvokerException(
                InvokerException.ErrorCode.INTERNAL,
                "Error during close: " + e.getMessage(),
                e
            );
        } finally {
            connectionLock.unlock();
        }
    }

    @Override
    public boolean isConnected() {
        return connected && channel != null && !channel.isShutdown();
    }

    /**
     * Gets the async stub for internal use (by JobEventPublisher).
     */
    FunctionServiceGrpc.InvokerServiceStub getAsyncStub() {
        return asyncStub;
    }

    /**
     * Gets the configured timeout.
     */
    long getTimeoutMillis() {
        return config.getTimeout();
    }

    private void ensureConnected() throws InvokerException {
        if (!connected) {
            connect();
        }
    }

    private SslContext createSslContext() throws InvokerException, SSLException {
        io.grpc.netty.shaded.io.netty.handler.ssl.SslContextBuilder builder =
            GrpcSslContexts.forClient();

        if (!config.getCaFile().isEmpty()) {
            builder.trustManager(new File(config.getCaFile()));
        }

        if (!config.getCertFile().isEmpty() && !config.getKeyFile().isEmpty()) {
            builder.keyManager(
                new File(config.getCertFile()),
                new File(config.getKeyFile())
            );
        }

        return builder.build();
    }

    private void validatePayload(String payload, Map<String, Object> schema) throws InvokerException {
        if (payload == null || payload.isEmpty()) {
            throw new InvokerException(
                InvokerException.ErrorCode.INVALID_ARGUMENT,
                "Payload cannot be empty"
            );
        }

        // Parse JSON
        Object payloadObj;
        try {
            // Use Gson or similar to parse JSON
            // For now, do basic validation
            if (!payload.trim().startsWith("{") || !payload.trim().endsWith("}")) {
                throw new InvokerException(
                    InvokerException.ErrorCode.INVALID_ARGUMENT,
                    "Invalid JSON payload"
                );
            }
        } catch (Exception e) {
            throw new InvokerException(
                InvokerException.ErrorCode.INVALID_ARGUMENT,
                "Invalid payload: " + e.getMessage()
            );
        }

        // Required field validation
        Object requiredObj = schema.get("required");
        if (requiredObj instanceof Iterable) {
            @SuppressWarnings("unchecked")
            Iterable<String> required = (Iterable<String>) requiredObj;
            for (String field : required) {
                // Simple check - if field is not in payload string
                if (!payload.contains("\"" + field + "\"")) {
                    throw new InvokerException(
                        InvokerException.ErrorCode.INVALID_ARGUMENT,
                        "Payload validation failed: missing required field '" + field + "'"
                    );
                }
            }
        }

        // Type validation for properties (basic implementation)
        Object propertiesObj = schema.get("properties");
        if (propertiesObj instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> properties = (Map<String, Object>) propertiesObj;
            for (Map.Entry<String, Object> entry : properties.entrySet()) {
                String field = entry.getKey();
                Object fieldSchemaObj = entry.getValue();

                if (fieldSchemaObj instanceof Map) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> fieldSchema = (Map<String, Object>) fieldSchemaObj;
                    Object expectedTypeObj = fieldSchema.get("type");

                    if (expectedTypeObj instanceof String) {
                        String expectedType = (String) expectedTypeObj;
                        // Check field exists in payload
                        if (payload.contains("\"" + field + "\":")) {
                            // Basic type validation based on JSON representation
                            if ("string".equals(expectedType)) {
                                // Strings are quoted
                                if (payload.matches("\"" + field + "\":\\s*\\d+")) {
                                    throw new InvokerException(
                                        InvokerException.ErrorCode.INVALID_ARGUMENT,
                                        "Payload validation failed: field '" + field + "' should be string, got number"
                                    );
                                }
                            } else if ("number".equals(expectedType) || "integer".equals(expectedType)) {
                                // Numbers are not quoted (unless in a string)
                                // This is a simplified check - a proper implementation would parse the JSON
                            } else if ("boolean".equals(expectedType)) {
                                // Booleans are true/false
                                if (!payload.contains("\"" + field + "\": true") &&
                                    !payload.contains("\"" + field + "\": false")) {
                                    throw new InvokerException(
                                        InvokerException.ErrorCode.INVALID_ARGUMENT,
                                        "Payload validation failed: field '" + field + "' should be boolean"
                                    );
                                }
                            }
                        }
                    }
                }
            }
        }

        logger.debug("Payload validation passed for {} characters", payload.length());
    }

    private InvokerException handleGrpcError(StatusRuntimeException e) {
        Status status = Status.fromThrowable(e);
        return InvokerException.fromGrpcStatus(
            status.getCode().value(),
            status.getDescription() != null ? status.getDescription() : e.getMessage(),
            e
        );
    }

    /**
     * Reactive Streams Publisher for job events.
     */
    private static class JobEventPublisher implements Publisher<JobEventInfo> {

        private final InvokerImpl invoker;
        private final String jobId;

        JobEventPublisher(InvokerImpl invoker, String jobId) {
            this.invoker = invoker;
            this.jobId = jobId;
        }

        @Override
        public void subscribe(Subscriber<? super JobEventInfo> subscriber) {
            if (!invoker.connected) {
                subscriber.onError(new IllegalStateException("Invoker not connected"));
                return;
            }

            JobEventStreamObserver streamObserver = new JobEventStreamObserver(subscriber, jobId);

            subscriber.onSubscribe(new Subscription() {
                private volatile boolean cancelled = false;

                @Override
                public void request(long n) {
                    // Request is handled by gRPC streaming
                }

                @Override
                public void cancel() {
                    cancelled = true;
                }
            });

            JobStreamRequest request = JobStreamRequest.newBuilder()
                .setJobId(jobId)
                .build();

            invoker.asyncStub.withDeadlineAfter(invoker.getTimeoutMillis(), TimeUnit.MILLISECONDS)
                .streamJob(request, streamObserver);
        }
    }

    /**
     * StreamObserver that adapts gRPC streaming to Reactive Streams.
     */
    private static class JobEventStreamObserver implements StreamObserver<JobEvent> {

        private final Subscriber<? super JobEventInfo> subscriber;
        private final String jobId;
        private volatile boolean completed = false;

        JobEventStreamObserver(Subscriber<? super JobEventInfo> subscriber, String jobId) {
            this.subscriber = subscriber;
            this.jobId = jobId;
        }

        @Override
        public void onNext(JobEvent event) {
            if (completed) {
                return;
            }

            JobEventInfo.Builder builder = JobEventInfo.builder()
                .type(event.getType())
                .jobId(jobId)
                .message(event.getMessage())
                .done(false);

            // Progress is an int field with default value 0
            // Only set if it's non-zero or explicitly meaningful
            if (event.getProgress() > 0) {
                builder.progress(event.getProgress());
            }

            // Payload is a ByteString, check if not empty
            ByteString payloadBytes = event.getPayload();
            if (payloadBytes != null && !payloadBytes.isEmpty()) {
                builder.payload(payloadBytes.toStringUtf8());
            }

            String type = event.getType();
            if ("completed".equals(type) || "error".equals(type) || "cancelled".equals(type)) {
                builder.done(true);
                completed = true;
            }

            if ("error".equals(type)) {
                builder.error(event.getMessage());
            }

            subscriber.onNext(builder.build());

            if (completed) {
                subscriber.onComplete();
            }
        }

        @Override
        public void onError(Throwable t) {
            if (completed) {
                return;
            }
            completed = true;
            subscriber.onError(t);
        }

        @Override
        public void onCompleted() {
            if (completed) {
                return;
            }
            completed = true;
            subscriber.onComplete();
        }
    }

    /**
     * Calculates reconnection delay using exponential backoff with jitter.
     *
     * @param attempt the reconnection attempt number (0-indexed)
     * @return delay in milliseconds
     */
    private long calculateReconnectDelay(int attempt) {
        ReconnectConfig rc = config.getReconnect();
        // Exponential backoff
        long delay = (long) (rc.getInitialDelayMs() * Math.pow(rc.getBackoffMultiplier(), attempt));
        delay = Math.min(delay, rc.getMaxDelayMs());

        // Add jitter to prevent thundering herd
        double jitter = delay * rc.getJitterFactor() * (random.nextDouble() * 2 - 1);
        delay += jitter;

        return Math.max(delay, 0);
    }

    /**
     * Checks if an error is a connection-related error that should trigger reconnection.
     *
     * @param e the exception to check
     * @return true if this is a connection error
     */
    private boolean isConnectionError(StatusRuntimeException e) {
        Status status = Status.fromThrowable(e);
        Status.Code code = status.getCode();

        // UNAVAILABLE (14) - Server unavailable
        // UNKNOWN (2) - Might be connection issue
        return code == Status.Code.UNAVAILABLE || code == Status.Code.UNKNOWN;
    }

    /**
     * Schedules a reconnection attempt if reconnection is enabled.
     */
    private void scheduleReconnectIfNeeded() {
        if (!config.getReconnect().isEnabled() || isReconnecting) {
            return;
        }

        // Check max attempts
        int maxAttempts = config.getReconnect().getMaxAttempts();
        if (maxAttempts > 0 && reconnectAttempts >= maxAttempts) {
            logger.error("Max reconnection attempts reached. Giving up.");
            return;
        }

        isReconnecting = true;

        // Cancel existing task if any
        if (reconnectTask != null && !reconnectTask.isDone()) {
            reconnectTask.cancel(false);
        }

        final int attempt = reconnectAttempts++;
        long delayMs = calculateReconnectDelay(attempt);

        logger.info("Scheduling reconnection attempt {} in {} ms", attempt + 1, delayMs);

        reconnectTask = reconnectExecutor.schedule(() -> {
            try {
                logger.info("Attempting reconnection (attempt {})", attempt + 1);

                // Clear existing connection state
                connected = false;
                if (channel != null) {
                    try {
                        channel.shutdownNow();
                    } catch (Exception e) {
                        logger.debug("Error shutting down channel during reconnect: {}", e.getMessage());
                    }
                    channel = null;
                }

                // Attempt reconnection
                connect();

                logger.info("Reconnection successful");
            } catch (Exception e) {
                logger.warn("Reconnection attempt {} failed: {}", attempt + 1, e.getMessage());
                // Schedule next attempt
                scheduleReconnectIfNeeded();
            } finally {
                isReconnecting = false;
            }
        }, delayMs, TimeUnit.MILLISECONDS);
    }

    /**
     * Cancels any pending reconnection task.
     */
    private void cancelReconnect() {
        if (reconnectTask != null && !reconnectTask.isDone()) {
            reconnectTask.cancel(false);
            reconnectTask = null;
        }
        isReconnecting = false;
    }

    /**
     * Functional interface for retryable operations.
     */
    @FunctionalInterface
    private interface RetryableOperation<T> {
        T execute() throws StatusRuntimeException;
    }

    /**
     * Executes an operation with retry logic.
     *
     * @param operation the operation to execute
     * @param options the invoke options (may contain retry override)
     * @param <T> the return type
     * @return the operation result
     * @throws InvokerException if all attempts fail
     */
    private <T> T executeWithRetry(RetryableOperation<T> operation, InvokeOptions options)
            throws InvokerException {
        // Use options retry if provided, otherwise use config retry
        RetryConfig retryConfig = options.getRetry() != null
            ? options.getRetry()
            : config.getRetry();

        if (!retryConfig.isEnabled()) {
            try {
                return operation.execute();
            } catch (StatusRuntimeException e) {
                throw handleGrpcError(e);
            }
        }

        int maxAttempts = retryConfig.getMaxAttempts();
        Exception lastException = null;

        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            try {
                return operation.execute();
            } catch (StatusRuntimeException e) {
                lastException = e;

                // Check if this error is retryable
                if (!isRetryableError(e) || attempt >= maxAttempts - 1) {
                    throw handleGrpcError(e);
                }

                // Connection errors should trigger reconnection
                if (isConnectionError(e) && config.getReconnect().isEnabled()) {
                    connected = false;
                    scheduleReconnectIfNeeded();
                }

                // Calculate delay and wait
                long delayMs = calculateRetryDelay(attempt, retryConfig);
                logger.warn("Invocation attempt {} failed, retrying in {} ms: {}",
                    attempt + 1, delayMs, e.getStatus().getDescription());

                try {
                    Thread.sleep(delayMs);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new InvokerException(
                        InvokerException.ErrorCode.INTERNAL,
                        "Retry interrupted",
                        ie
                    );
                }
            }
        }

        // Should not reach here, but handle the case
        throw handleGrpcError((StatusRuntimeException) lastException);
    }

    /**
     * Checks if an error should trigger a retry.
     *
     * @param e the exception to check
     * @return true if this error is retryable
     */
    private boolean isRetryableError(StatusRuntimeException e) {
        Status status = Status.fromThrowable(e);
        int codeValue = status.getCode().value();
        return config.getRetry().getRetryableStatusCodes().contains(codeValue);
    }

    /**
     * Calculates retry delay using exponential backoff with jitter.
     *
     * @param attempt the retry attempt number (0-indexed)
     * @param retryConfig the retry configuration
     * @return delay in milliseconds
     */
    private long calculateRetryDelay(int attempt, RetryConfig retryConfig) {
        // Exponential backoff
        long delay = (long) (retryConfig.getInitialDelayMs()
            * Math.pow(retryConfig.getBackoffMultiplier(), attempt));
        delay = Math.min(delay, retryConfig.getMaxDelayMs());

        // Add jitter to prevent thundering herd
        double jitter = delay * retryConfig.getJitterFactor() * (random.nextDouble() * 2 - 1);
        delay += jitter;

        return Math.max(delay, 0);
    }
}
