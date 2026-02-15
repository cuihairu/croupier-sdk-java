package io.github.cuihairu.croupier.sdk.invoker;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
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
    public void connect() throws InvokerException {
        if (connected) {
            return;
        }

        try {
            logger.info("Connecting to server/agent at: {}", config.getAddress());

            // TODO: Implement transport connection (NNG, etc.)
            connected = true;

            logger.info("Connected to: {}", config.getAddress());
        } catch (Exception e) {
            logger.error("Connection failed", e);
            throw new InvokerException("Connection failed: " + e.getMessage(), e);
        }
    }

    @Override
    public String invoke(String functionId, String payload) throws InvokerException {
        return invoke(functionId, payload, new InvokeOptions());
    }

    @Override
    public String invoke(String functionId, String payload, InvokeOptions options) throws InvokerException {
        if (!connected) {
            connect();
        }

        try {
            // TODO: Implement via transport layer
            throw new UnsupportedOperationException("Invoke not implemented without transport layer");
        } catch (Exception e) {
            if (e instanceof InvokerException) {
                throw (InvokerException) e;
            }
            throw new InvokerException("Invoke failed: " + e.getMessage(), e);
        }
    }

    @Override
    public String startJob(String functionId, String payload) throws InvokerException {
        return startJob(functionId, payload, new InvokeOptions());
    }

    @Override
    public String startJob(String functionId, String payload, InvokeOptions options) throws InvokerException {
        if (!connected) {
            connect();
        }

        try {
            // TODO: Implement via transport layer
            throw new UnsupportedOperationException("StartJob not implemented without transport layer");
        } catch (Exception e) {
            if (e instanceof InvokerException) {
                throw (InvokerException) e;
            }
            throw new InvokerException("StartJob failed: " + e.getMessage(), e);
        }
    }

    @Override
    public Publisher<JobEventInfo> streamJob(String jobId) {
        // TODO: Implement via transport layer
        throw new UnsupportedOperationException("StreamJob not implemented without transport layer");
    }

    @Override
    public void cancelJob(String jobId) throws InvokerException {
        try {
            // TODO: Implement via transport layer
            throw new UnsupportedOperationException("CancelJob not implemented without transport layer");
        } catch (Exception e) {
            if (e instanceof InvokerException) {
                throw (InvokerException) e;
            }
            throw new InvokerException("CancelJob failed: " + e.getMessage(), e);
        }
    }

    @Override
    public void setSchema(String functionId, Map<String, Object> schema) {
        schemas.put(functionId, schema);
        logger.debug("Set schema for function: {}", functionId);
    }

    @Override
    public void close() throws InvokerException {
        connected = false;
        schemas.clear();
        logger.info("Invoker closed");
    }

    @Override
    public boolean isConnected() {
        return connected;
    }
}
