package io.github.cuihairu.croupier.sdk;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class CroupierClientImplTest {

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
}

