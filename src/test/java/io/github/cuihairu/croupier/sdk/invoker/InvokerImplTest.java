package io.github.cuihairu.croupier.sdk.invoker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for InvokerImpl.
 */
class InvokerImplTest {

    private InvokerConfig config;
    private InvokerImpl invoker;

    @BeforeEach
    void setUp() {
        config = InvokerConfig.builder()
                .address("localhost:19090")
                .insecure(true)
                .timeout(30000)
                .build();
        invoker = new InvokerImpl(config);
    }

    @Test
    @DisplayName("Constructor should initialize with config")
    void testConstructor() {
        assertNotNull(invoker);
        assertFalse(invoker.isConnected());
    }

    @Test
    @DisplayName("connect() should set connected to true")
    void testConnect() throws InvokerException {
        invoker.connect();
        assertTrue(invoker.isConnected());
    }

    @Test
    @DisplayName("connect() should be idempotent")
    void testConnectIdempotent() throws InvokerException {
        invoker.connect();
        assertTrue(invoker.isConnected());

        // Second connect should not throw
        invoker.connect();
        assertTrue(invoker.isConnected());
    }

    @Test
    @DisplayName("close() should set connected to false")
    void testClose() throws InvokerException {
        invoker.connect();
        assertTrue(invoker.isConnected());

        invoker.close();
        assertFalse(invoker.isConnected());
    }

    @Test
    @DisplayName("close() should be idempotent")
    void testCloseIdempotent() throws InvokerException {
        invoker.connect();
        invoker.close();
        assertFalse(invoker.isConnected());

        // Second close should not throw
        invoker.close();
        assertFalse(invoker.isConnected());
    }

    @Test
    @DisplayName("close() without connect should work")
    void testCloseWithoutConnect() throws InvokerException {
        assertFalse(invoker.isConnected());
        invoker.close();
        assertFalse(invoker.isConnected());
    }

    @Test
    @DisplayName("setSchema() should store schema")
    void testSetSchema() {
        Map<String, Object> schema = Map.of(
                "type", "object",
                "properties", Map.of("name", Map.of("type", "string"))
        );

        invoker.setSchema("test.function", schema);
    }

    @Test
    @DisplayName("invoke() should throw InvokerException without transport")
    void testInvokeWithoutTransport() throws InvokerException {
        invoker.connect();
        InvokerException exception = assertThrows(InvokerException.class, () ->
                invoker.invoke("test.function", "{}"));
        assertTrue(exception.getCause() instanceof UnsupportedOperationException);
    }

    @Test
    @DisplayName("invoke() with options should throw InvokerException without transport")
    void testInvokeWithOptionsWithoutTransport() throws InvokerException {
        invoker.connect();
        InvokeOptions options = InvokeOptions.builder().build();
        InvokerException exception = assertThrows(InvokerException.class, () ->
                invoker.invoke("test.function", "{}", options));
        assertTrue(exception.getCause() instanceof UnsupportedOperationException);
    }

    @Test
    @DisplayName("startJob() should throw InvokerException without transport")
    void testStartJobWithoutTransport() throws InvokerException {
        invoker.connect();
        InvokerException exception = assertThrows(InvokerException.class, () ->
                invoker.startJob("test.function", "{}"));
        assertTrue(exception.getCause() instanceof UnsupportedOperationException);
    }

    @Test
    @DisplayName("startJob() with options should throw InvokerException without transport")
    void testStartJobWithOptionsWithoutTransport() throws InvokerException {
        invoker.connect();
        InvokeOptions options = InvokeOptions.builder().build();
        InvokerException exception = assertThrows(InvokerException.class, () ->
                invoker.startJob("test.function", "{}", options));
        assertTrue(exception.getCause() instanceof UnsupportedOperationException);
    }

    @Test
    @DisplayName("streamJob() should throw UnsupportedOperationException without transport")
    void testStreamJobWithoutTransport() {
        assertThrows(UnsupportedOperationException.class, () ->
                invoker.streamJob("job-123"));
    }

    @Test
    @DisplayName("cancelJob() should throw InvokerException without transport")
    void testCancelJobWithoutTransport() {
        InvokerException exception = assertThrows(InvokerException.class, () ->
                invoker.cancelJob("job-123"));
        assertTrue(exception.getCause() instanceof UnsupportedOperationException);
    }

    @Test
    @DisplayName("invoke() should auto-connect if not connected")
    void testInvokeAutoConnect() {
        assertFalse(invoker.isConnected());
        // This will try to connect and then fail with InvokerException
        assertThrows(InvokerException.class, () ->
                invoker.invoke("test.function", "{}"));
        // Should be connected after auto-connect attempt
        assertTrue(invoker.isConnected());
    }

    @Test
    @DisplayName("Invoker with null config should throw NullPointerException")
    void testInvokerWithNullConfig() throws InvokerException {
        InvokerImpl invokerWithNullConfig = new InvokerImpl(null);
        // Should throw InvokerException with NullPointerException cause when trying to connect
        InvokerException exception = assertThrows(InvokerException.class, () ->
                invokerWithNullConfig.connect());
        assertTrue(exception.getCause() instanceof NullPointerException);
    }
}
