package io.github.cuihairu.croupier.sdk;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ClientConfigTest {

    @Test
    void constructorWithGameIdAndServiceId() {
        ClientConfig config = new ClientConfig("game1", "svc1");

        assertEquals("game1", config.getGameId());
        assertEquals("svc1", config.getServiceId());
        assertEquals("development", config.getEnv());
        assertEquals("localhost:19090", config.getAgentAddr());
        assertTrue(config.isInsecure());
    }

    @Test
    void settersAndGettersWork() {
        ClientConfig config = new ClientConfig("game1", "svc1");

        config.setAgentAddr("localhost:9999");
        assertEquals("localhost:9999", config.getAgentAddr());

        config.setLocalListen("0.0.0.0:8888");
        assertEquals("0.0.0.0:8888", config.getLocalListen());

        config.setEnv("production");
        assertEquals("production", config.getEnv());

        config.setServiceVersion("2.0.0");
        assertEquals("2.0.0", config.getServiceVersion());

        config.setProviderLang("java");
        assertEquals("java", config.getProviderLang());

        config.setProviderSdk("croupier-sdk");
        assertEquals("croupier-sdk", config.getProviderSdk());

        config.setControlAddr("localhost:8080");
        assertEquals("localhost:8080", config.getControlAddr());

        config.setTimeoutSeconds(30);
        assertEquals(30, config.getTimeoutSeconds());

        config.setInsecure(true);
        assertTrue(config.isInsecure());

        config.setCaFile("/path/to/ca.pem");
        assertEquals("/path/to/ca.pem", config.getCaFile());

        config.setCertFile("/path/to/cert.pem");
        assertEquals("/path/to/cert.pem", config.getCertFile());

        config.setKeyFile("/path/to/key.pem");
        assertEquals("/path/to/key.pem", config.getKeyFile());
    }

    @Test
    void toStringContainsGameAndServiceIds() {
        ClientConfig config = new ClientConfig("test-game", "test-svc");
        String str = config.toString();

        assertTrue(str.contains("test-game"));
        assertTrue(str.contains("test-svc"));
    }

    @Test
    void configWithProductionEnv() {
        ClientConfig config = new ClientConfig("game1", "svc1");
        config.setEnv("production");

        assertEquals("production", config.getEnv());
    }

    @Test
    void configWithStagingEnv() {
        ClientConfig config = new ClientConfig("game1", "svc1");
        config.setEnv("staging");

        assertEquals("staging", config.getEnv());
    }

    @Test
    void configWithCustomTimeout() {
        ClientConfig config = new ClientConfig("game1", "svc1");
        config.setTimeoutSeconds(60);

        assertEquals(60, config.getTimeoutSeconds());
    }

    @Test
    void configWithTlsFiles() {
        ClientConfig config = new ClientConfig("game1", "svc1");
        config.setCaFile("/ca.pem");
        config.setCertFile("/cert.pem");
        config.setKeyFile("/key.pem");

        assertEquals("/ca.pem", config.getCaFile());
        assertEquals("/cert.pem", config.getCertFile());
        assertEquals("/key.pem", config.getKeyFile());
        assertTrue(config.isInsecure());
    }

    @Test
    void multipleConfigInstances() {
        ClientConfig config1 = new ClientConfig("game1", "svc1");
        ClientConfig config2 = new ClientConfig("game2", "svc2");

        assertEquals("game1", config1.getGameId());
        assertEquals("game2", config2.getGameId());
    }
}
