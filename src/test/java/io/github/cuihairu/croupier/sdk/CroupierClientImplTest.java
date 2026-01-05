package io.github.cuihairu.croupier.sdk;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CroupierClientImplTest {

    @Test
    void constructorWithValidConfig() {
        ClientConfig config = new ClientConfig("game-1", "svc-1");
        CroupierClientImpl client = new CroupierClientImpl(config);

        assertNotNull(client);
        assertFalse(client.isConnected());
        assertFalse(client.isServing());
    }

    @Test
    void constructorWithNullConfigThrowsException() {
        assertThrows(NullPointerException.class, () -> new CroupierClientImpl(null));
    }

    @Test
    void registerFunctionRequiresIdAndVersion() {
        ClientConfig config = new ClientConfig("game-1", "svc-1");
        CroupierClientImpl client = new CroupierClientImpl(config);

        assertThrows(
            CroupierException.class,
            () -> client.registerFunction(new FunctionDescriptor("", "1.0.0"), (ctx, payload) -> "ok")
        );
        assertThrows(
            CroupierException.class,
            () -> client.registerFunction(new FunctionDescriptor("f1", ""), (ctx, payload) -> "ok")
        );
    }

    @Test
    void registerFunctionAcceptsValidDescriptor() {
        ClientConfig config = new ClientConfig("game-1", "svc-1");
        CroupierClientImpl client = new CroupierClientImpl(config);

        assertDoesNotThrow(() ->
            client.registerFunction(new FunctionDescriptor("f1", "1.0.0"), (ctx, payload) -> "ok")
        );
    }

    @Test
    void registerMultipleFunctions() {
        ClientConfig config = new ClientConfig("game-1", "svc-1");
        CroupierClientImpl client = new CroupierClientImpl(config);

        assertDoesNotThrow(() -> {
            client.registerFunction(new FunctionDescriptor("func1", "1.0.0"), (ctx, payload) -> "result1");
            client.registerFunction(new FunctionDescriptor("func2", "1.0.0"), (ctx, payload) -> "result2");
            client.registerFunction(new FunctionDescriptor("func3", "1.0.0"), (ctx, payload) -> "result3");
        });
    }

    @Test
    void closeClientDoesNotThrow() {
        ClientConfig config = new ClientConfig("game-1", "svc-1");
        CroupierClientImpl client = new CroupierClientImpl(config);

        assertDoesNotThrow(client::close);
    }

    @Test
    void stopClientDoesNotThrow() {
        ClientConfig config = new ClientConfig("game-1", "svc-1");
        CroupierClientImpl client = new CroupierClientImpl(config);

        assertDoesNotThrow(client::stop);
    }

    @Test
    void connectReturnsCompletableFuture() {
        ClientConfig config = new ClientConfig("game-1", "svc-1");
        CroupierClientImpl client = new CroupierClientImpl(config);

        assertNotNull(client.connect());
    }

    @Test
    void serveAsyncReturnsCompletableFuture() {
        ClientConfig config = new ClientConfig("game-1", "svc-1");
        CroupierClientImpl client = new CroupierClientImpl(config);

        assertNotNull(client.serveAsync());
    }
}
