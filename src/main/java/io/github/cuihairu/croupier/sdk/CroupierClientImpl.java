package io.github.cuihairu.croupier.sdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.zip.GZIPOutputStream;

/**
 * Default implementation of CroupierClient.
 *
 * Note: This is a refactored version without gRPC dependencies.
 * Transport layer should be implemented separately.
 */
public class CroupierClientImpl implements CroupierClient {
    private static final Logger logger = LoggerFactory.getLogger(CroupierClientImpl.class);

    private final ClientConfig config;
    private final Map<String, FunctionHandler> handlers = new ConcurrentHashMap<>();
    private final Map<String, FunctionDescriptor> descriptors = new ConcurrentHashMap<>();

    private final AtomicBoolean connected = new AtomicBoolean(false);
    private final AtomicBoolean serving = new AtomicBoolean(false);

    private String sessionId;
    private String localAddress;

    public CroupierClientImpl(ClientConfig config) {
        this.config = config;
        validateConfig();
        logger.info("Initialized CroupierClient for game '{}' in '{}' environment",
                   config.getGameId(), config.getEnv());
    }

    @Override
    public void registerFunction(FunctionDescriptor descriptor, FunctionHandler handler) throws CroupierException {
        if (serving.get()) {
            throw new CroupierException("Cannot register functions while client is serving");
        }

        validateFunctionDescriptor(descriptor);

        handlers.put(descriptor.getId(), handler);
        descriptors.put(descriptor.getId(), descriptor);

        logger.info("Registered function: {} (version: {})", descriptor.getId(), descriptor.getVersion());
    }

    @Override
    public CompletableFuture<Void> connect() {
        if (connected.get()) {
            return CompletableFuture.completedFuture(null);
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                logger.info("Connecting to Croupier Agent: {}", config.getAgentAddr());

                if (handlers.isEmpty()) {
                    throw new CroupierException("Register at least one function before connecting");
                }

                // TODO: Implement transport connection (NNG, etc.)
                connected.set(true);
                localAddress = config.getAgentAddr();

                logger.info("Successfully connected");
                logger.info("Local service address: {}", localAddress);

                return null;
            } catch (Exception e) {
                logger.error("Connection failed", e);
                throw new RuntimeException(new CroupierException("Connection failed", e));
            }
        });
    }

    @Override
    public void serve() throws CroupierException {
        serveAsync().join();
    }

    @Override
    public CompletableFuture<Void> serveAsync() {
        if (!connected.get()) {
            return connect().thenCompose(v -> doServe());
        } else {
            return doServe();
        }
    }

    private CompletableFuture<Void> doServe() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                serving.set(true);
                logger.info("Croupier client service started");
                logger.info("Local service address: {}", localAddress);
                logger.info("Registered functions: {}", handlers.size());

                // Keep serving until stopped
                while (serving.get()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }

                serving.set(false);
                logger.info("Service has stopped");
                return null;
            } catch (Exception e) {
                serving.set(false);
                throw new RuntimeException(new CroupierException("Serving failed", e));
            }
        });
    }

    @Override
    public void stop() {
        serving.set(false);
        connected.set(false);

        logger.info("Stopping Croupier client...");
        logger.info("Client stopped successfully");
    }

    @Override
    public void close() {
        stop();
        handlers.clear();
        descriptors.clear();
    }

    @Override
    public String getLocalAddress() {
        return localAddress;
    }

    @Override
    public boolean isConnected() {
        return connected.get();
    }

    @Override
    public boolean isServing() {
        return serving.get();
    }

    /**
     * Invoke a registered function handler directly.
     */
    public String invoke(String functionId, String payload, Map<String, String> metadata) throws CroupierException {
        FunctionHandler handler = handlers.get(functionId);
        if (handler == null) {
            throw new CroupierException("Function not found: " + functionId);
        }

        String context = toJson(metadata != null ? metadata : Map.of());
        try {
            return handler.handle(context, payload);
        } catch (Exception e) {
            if (e instanceof CroupierException) {
                throw (CroupierException) e;
            }
            throw new CroupierException("Function execution failed: " + e.getMessage(), e);
        }
    }

    private void validateConfig() {
        if (config.getGameId() == null || config.getGameId().trim().isEmpty()) {
            logger.warn("Warning: gameId is required for proper backend separation");
        }

        String env = config.getEnv();
        if (!"development".equals(env) && !"staging".equals(env) && !"production".equals(env)) {
            logger.warn("Warning: Unknown environment '{}'. Valid values: development, staging, production", env);
        }
    }

    private void validateFunctionDescriptor(FunctionDescriptor descriptor) throws CroupierException {
        if (descriptor.getId() == null || descriptor.getId().trim().isEmpty()) {
            throw new CroupierException("Function ID cannot be empty");
        }
        if (descriptor.getVersion() == null || descriptor.getVersion().trim().isEmpty()) {
            throw new CroupierException("Function version cannot be empty");
        }
    }

    /**
     * Get local function descriptors for registration.
     */
    public List<LocalFunctionDescriptor> getLocalFunctions() {
        return descriptors.values().stream()
                .map(desc -> new LocalFunctionDescriptor(desc.getId(), desc.getVersion()))
                .collect(Collectors.toList());
    }

    /**
     * Build a registration request for the agent.
     */
    public Map<String, Object> getRegisterRequest() {
        return Map.of(
            "serviceId", config.getServiceId(),
            "version", config.getServiceVersion(),
            "rpcAddr", localAddress != null ? localAddress : "",
            "functions", getLocalFunctions().stream()
                .map(f -> Map.of("id", f.getId(), "version", f.getVersion()))
                .collect(Collectors.toList())
        );
    }

    /**
     * Build provider manifest JSON.
     */
    public byte[] buildManifest() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\"provider\":{");
        builder.append("\"id\":\"").append(escapeJson(defaultValue(config.getServiceId(), "java-service"))).append("\",");
        builder.append("\"version\":\"").append(escapeJson(defaultVersion(config.getServiceVersion()))).append("\",");
        builder.append("\"lang\":\"").append(escapeJson(defaultValue(config.getProviderLang(), "java"))).append("\",");
        builder.append("\"sdk\":\"").append(escapeJson(defaultValue(config.getProviderSdk(), "croupier-java-sdk"))).append("\"}");

        List<FunctionDescriptor> snapshot = descriptors.values().stream().collect(Collectors.toList());
        StringBuilder functionsBuilder = new StringBuilder();
        boolean first = true;
        for (FunctionDescriptor descriptor : snapshot) {
            if (descriptor == null || isNullOrEmpty(descriptor.getId())) {
                continue;
            }
            if (first) {
                functionsBuilder.append("[");
                first = false;
            } else {
                functionsBuilder.append(",");
            }
            functionsBuilder.append("{");
            functionsBuilder.append("\"id\":\"").append(escapeJson(descriptor.getId())).append("\"");
            functionsBuilder.append(",\"version\":\"").append(escapeJson(defaultVersion(descriptor.getVersion()))).append("\"");
            if (!isNullOrEmpty(descriptor.getCategory())) {
                functionsBuilder.append(",\"category\":\"").append(escapeJson(descriptor.getCategory())).append("\"");
            }
            if (!isNullOrEmpty(descriptor.getRisk())) {
                functionsBuilder.append(",\"risk\":\"").append(escapeJson(descriptor.getRisk())).append("\"");
            }
            if (!isNullOrEmpty(descriptor.getEntity())) {
                functionsBuilder.append(",\"entity\":\"").append(escapeJson(descriptor.getEntity())).append("\"");
            }
            if (!isNullOrEmpty(descriptor.getOperation())) {
                functionsBuilder.append(",\"operation\":\"").append(escapeJson(descriptor.getOperation())).append("\"");
            }
            if (descriptor.isEnabled()) {
                functionsBuilder.append(",\"enabled\":true");
            }
            functionsBuilder.append("}");
        }
        if (!first) {
            functionsBuilder.append("]");
            builder.append(",\"functions\":").append(functionsBuilder);
        }

        builder.append("}");
        return builder.toString().getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Get gzipped manifest.
     */
    public byte[] getManifestGzipped() throws IOException {
        return gzip(buildManifest());
    }

    private String toJson(Map<String, String> map) {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!first) sb.append(",");
            sb.append("\"").append(escapeJson(entry.getKey())).append("\":");
            sb.append("\"").append(escapeJson(entry.getValue())).append("\"");
            first = false;
        }
        sb.append("}");
        return sb.toString();
    }

    private String escapeJson(String value) {
        if (value == null) {
            return "";
        }
        StringBuilder out = new StringBuilder(value.length() + 16);
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            switch (ch) {
                case '"': out.append("\\\""); break;
                case '\\': out.append("\\\\"); break;
                case '\b': out.append("\\b"); break;
                case '\f': out.append("\\f"); break;
                case '\n': out.append("\\n"); break;
                case '\r': out.append("\\r"); break;
                case '\t': out.append("\\t"); break;
                default:
                    if (ch < 0x20) {
                        out.append(String.format("\\u%04x", (int) ch));
                    } else {
                        out.append(ch);
                    }
            }
        }
        return out.toString();
    }

    private String defaultValue(String value, String fallback) {
        return isNullOrEmpty(value) ? fallback : value;
    }

    private String defaultVersion(String version) {
        return isNullOrEmpty(version) ? "1.0.0" : version;
    }

    private byte[] gzip(byte[] payload) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(output)) {
            gzip.write(payload);
        }
        return output.toByteArray();
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
