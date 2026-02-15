package io.github.cuihairu.croupier.sdk.invoker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of the Invoker interface.
 *
 * <p>Note: This is a refactored version without gRPC dependencies.
 * Transport layer should be implemented separately.</p>
 */
public class InvokerImpl implements Invoker {

    private static final Logger logger = LoggerFactory.getLogger(InvokerImpl.class);

    private final InvokerConfig config;
    private final Map<String, Map<String, Object>> schemas;
    private volatile boolean connected;

    public InvokerImpl(InvokerConfig config) {
        this.config = config;
        this.schemas = new ConcurrentHashMap<>();
        this.connected = false;
    }

    @Override
    public CompletableFuture<Void> connect() {
        if (connected) {
            return CompletableFuture.completedFuture(null);
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                logger.info("Connecting to server/agent at: {}", config.getAddress());

                // TODO: Implement transport connection (NNG, etc.)
                connected = true;

                logger.info("Connected to: {}", config.getAddress());
                return null;
            } catch (Exception e) {
                logger.error("Connection failed", e);
                throw new RuntimeException("Connection failed: " + e.getMessage(), e);
            }
        });
    }

    @Override
    public CompletableFuture<String> invoke(String functionId, String payload, InvokeOptions options) {
        if (!connected) {
            return connect().thenCompose(v -> doInvoke(functionId, payload, options));
        }
        return doInvoke(functionId, payload, options);
    }

    private CompletableFuture<String> doInvoke(String functionId, String payload, InvokeOptions options) {
        return CompletableFuture.supplyAsync(() -> {
            // TODO: Implement via transport layer
            throw new UnsupportedOperationException("Invoke not implemented without transport layer");
        });
    }

    @Override
    public CompletableFuture<String> startJob(String functionId, String payload, InvokeOptions options) {
        if (!connected) {
            return connect().thenCompose(v -> doStartJob(functionId, payload, options));
        }
        return doStartJob(functionId, payload, options);
    }

    private CompletableFuture<String> doStartJob(String functionId, String payload, InvokeOptions options) {
        return CompletableFuture.supplyAsync(() -> {
            // TODO: Implement via transport layer
            throw new UnsupportedOperationException("StartJob not implemented without transport layer");
        });
    }

    @Override
    public CompletableFuture<Void> cancelJob(String jobId) {
        return CompletableFuture.runAsync(() -> {
            // TODO: Implement via transport layer
            throw new UnsupportedOperationException("CancelJob not implemented without transport layer");
        });
    }

    @Override
    public void setSchema(String functionId, Map<String, Object> schema) {
        schemas.put(functionId, schema);
        logger.debug("Set schema for function: {}", functionId);
    }

    @Override
    public CompletableFuture<Void> close() {
        return CompletableFuture.runAsync(() -> {
            connected = false;
            schemas.clear();
            logger.info("Invoker closed");
        });
    }

    @Override
    public boolean isConnected() {
        return connected;
    }
}
