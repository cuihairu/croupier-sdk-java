package io.github.cuihairu.croupier.sdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class CroupierSDKTest {

    @Test
    void createClientWithConfig() {
        ClientConfig config = new ClientConfig("game1", "svc1");
        CroupierClient client = CroupierSDK.createClient(config);

        assertNotNull(client);
    }

    @Test
    void createClientWithGameAndServiceIds() {
        CroupierClient client = CroupierSDK.createClient("game2", "svc2");

        assertNotNull(client);
    }

    @Test
    void createClientWithGameServiceAndAgentAddress() {
        CroupierClient client = CroupierSDK.createClient("game3", "svc3", "localhost:9999");

        assertNotNull(client);
    }

    @Test
    void createClientWithNullConfigThrowsException() {
        assertThrows(NullPointerException.class, () -> CroupierSDK.createClient((ClientConfig) null));
    }

    @Test
    void createClientWithNullGameIdThrowsException() {
        assertThrows(NullPointerException.class, () -> CroupierSDK.createClient(null, "svc"));
    }

    @Test
    void createClientWithNullServiceIdThrowsException() {
        assertThrows(NullPointerException.class, () -> CroupierSDK.createClient("game", null));
    }

    @Test
    void createFunctionDescriptorBuilderReturnsBuilder() {
        FunctionDescriptorBuilder builder = CroupierSDK.createFunctionDescriptorBuilder("test-func", "1.0.0");

        assertNotNull(builder);
    }

    @Test
    void createFunctionDescriptorBuilderWithNullIdThrowsException() {
        assertThrows(NullPointerException.class, () -> CroupierSDK.createFunctionDescriptorBuilder(null, "1.0.0"));
    }

    @Test
    void createFunctionDescriptorBuilderWithNullVersionThrowsException() {
        assertThrows(NullPointerException.class, () -> CroupierSDK.createFunctionDescriptorBuilder("func", null));
    }
}
