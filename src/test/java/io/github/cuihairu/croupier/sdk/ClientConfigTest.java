package io.github.cuihairu.croupier.sdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ClientConfigTest {

    @Test
    void defaultConstructorCreatesDefaultConfig() {
        ClientConfig config = new ClientConfig();

        assertEquals("localhost:19090", config.getAgentAddr());
        assertEquals("development", config.getEnv());
        assertEquals("1.0.0", config.getServiceVersion());
        assertEquals("java", config.getProviderLang());
        assertEquals("croupier-java-sdk", config.getProviderSdk());
        assertEquals(30, config.getTimeoutSeconds());
        assertTrue(config.isInsecure());
    }

    @Test
    void constructorWithGameIdAndServiceId() {
        ClientConfig config = new ClientConfig("my-game", "my-service");

        assertEquals("my-game", config.getGameId());
        assertEquals("my-service", config.getServiceId());
    }

    @Test
    void settersAndGettersWork() {
        ClientConfig config = new ClientConfig();

        config.setAgentAddr("example.com:8080");
        assertEquals("example.com:8080", config.getAgentAddr());

        config.setGameId("test-game");
        assertEquals("test-game", config.getGameId());

        config.setEnv("production");
        assertEquals("production", config.getEnv());

        config.setServiceId("test-service");
        assertEquals("test-service", config.getServiceId());

        config.setServiceVersion("2.0.0");
        assertEquals("2.0.0", config.getServiceVersion());

        config.setAgentId("agent-1");
        assertEquals("agent-1", config.getAgentId());

        config.setLocalListen("0.0.0.0:9999");
        assertEquals("0.0.0.0:9999", config.getLocalListen());

        config.setControlAddr("control.example.com:8081");
        assertEquals("control.example.com:8081", config.getControlAddr());

        config.setTimeoutSeconds(60);
        assertEquals(60, config.getTimeoutSeconds());

        config.setInsecure(false);
        assertFalse(config.isInsecure());

        config.setCaFile("/path/to/ca.pem");
        assertEquals("/path/to/ca.pem", config.getCaFile());

        config.setCertFile("/path/to/cert.pem");
        assertEquals("/path/to/cert.pem", config.getCertFile());

        config.setKeyFile("/path/to/key.pem");
        assertEquals("/path/to/key.pem", config.getKeyFile());

        config.setProviderLang("kotlin");
        assertEquals("kotlin", config.getProviderLang());

        config.setProviderSdk("croupier-kotlin-sdk");
        assertEquals("croupier-kotlin-sdk", config.getProviderSdk());
    }

    @Test
    void toStringContainsImportantFields() {
        ClientConfig config = new ClientConfig("game1", "svc1");
        config.setAgentAddr("server:9090");
        config.setEnv("staging");

        String str = config.toString();

        assertTrue(str.contains("server:9090"));
        assertTrue(str.contains("game1"));
        assertTrue(str.contains("staging"));
        assertTrue(str.contains("svc1"));
    }
}
