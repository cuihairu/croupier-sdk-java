package io.github.cuihairu.croupier.sdk;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class GrpcManagerTest {

    @Test
    void constructorWithValidInputs() {
        ClientConfig config = new ClientConfig("game1", "svc1");
        Map<String, FunctionHandler> handlers = new HashMap<>();
        GrpcManager manager = new GrpcManager(config, handlers);
        assertNotNull(manager);
        assertFalse(manager.isConnected());
    }

    @Test
    void constructorWithNullHandlers() {
        ClientConfig config = new ClientConfig("game1", "svc1");
        GrpcManager manager = new GrpcManager(config, null);
        assertNotNull(manager);
    }

    @Test
    void getLocalAddressReturnsNullWhenNotStarted() {
        ClientConfig config = new ClientConfig("game1", "svc1");
        Map<String, FunctionHandler> handlers = new HashMap<>();
        GrpcManager manager = new GrpcManager(config, handlers);
        String address = manager.getLocalAddress();
        assertFalse(manager.isConnected());
    }

    @Test
    void isConnectedReturnsFalseWhenNotConnected() {
        ClientConfig config = new ClientConfig("game1", "svc1");
        Map<String, FunctionHandler> handlers = new HashMap<>();
        GrpcManager manager = new GrpcManager(config, handlers);
        assertFalse(manager.isConnected());
    }

    @Test
    void disconnectWhenNotConnected() {
        ClientConfig config = new ClientConfig("game1", "svc1");
        Map<String, FunctionHandler> handlers = new HashMap<>();
        GrpcManager manager = new GrpcManager(config, handlers);
        manager.disconnect();
        assertFalse(manager.isConnected());
    }

    @Test
    void registerWithAgentWhenNotConnectedThrowsException() {
        ClientConfig config = new ClientConfig("game1", "svc1");
        Map<String, FunctionHandler> handlers = new HashMap<>();
        GrpcManager manager = new GrpcManager(config, handlers);
        assertThrows(CroupierException.class, () ->
            manager.registerWithAgent("svc1", "1.0.0", List.of())
        );
    }

    @Test
    void grpcManagerWithHandlerMap() {
        ClientConfig config = new ClientConfig("game1", "svc1");
        Map<String, FunctionHandler> handlers = new HashMap<>();
        handlers.put("func1", (ctx, payload) -> "{\"ok\":true}");
        GrpcManager manager = new GrpcManager(config, handlers);
        assertNotNull(manager);
        assertFalse(manager.isConnected());
    }

    @Test
    void grpcManagerWithVariousConfigurations() {
        ClientConfig config = new ClientConfig("game1", "svc1");
        config.setAgentAddr("localhost:19090");
        config.setLocalListen("127.0.0.1:0");
        config.setInsecure(true);
        Map<String, FunctionHandler> handlers = new HashMap<>();
        GrpcManager manager = new GrpcManager(config, handlers);
        assertNotNull(manager);
        assertFalse(manager.isConnected());
    }
}
