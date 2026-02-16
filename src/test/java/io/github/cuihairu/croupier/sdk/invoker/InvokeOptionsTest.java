package io.github.cuihairu.croupier.sdk.invoker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for InvokeOptions.
 */
class InvokeOptionsTest {

    @Test
    @DisplayName("Create should return default options")
    void testCreate() {
        InvokeOptions options = InvokeOptions.create();

        assertNotNull(options);
    }

    @Test
    @DisplayName("Builder should create options with all fields")
    void testBuilderAllFields() {
        RetryConfig retryConfig = RetryConfig.builder()
                .maxAttempts(5)
                .build();

        InvokeOptions options = InvokeOptions.builder()
                .timeout(30000)
                .idempotencyKey("idem-key-123")
                .header("X-Custom", "value")
                .header("X-Game-ID", "game-123")
                .retry(retryConfig)
                .build();

        assertEquals(30000, options.getTimeout());
        assertEquals("idem-key-123", options.getIdempotencyKey());
        assertEquals("value", options.getHeaders().get("X-Custom"));
        assertEquals("game-123", options.getHeaders().get("X-Game-ID"));
        assertNotNull(options.getRetry());
    }

    @Test
    @DisplayName("Builder with partial values should work")
    void testBuilderPartialValues() {
        InvokeOptions options = InvokeOptions.builder()
                .idempotencyKey("test-key")
                .build();

        assertEquals("test-key", options.getIdempotencyKey());
    }

    @Test
    @DisplayName("Options with same values should be equal")
    void testEquals() {
        InvokeOptions options1 = InvokeOptions.builder()
                .idempotencyKey("key-1")
                .build();

        InvokeOptions options2 = InvokeOptions.builder()
                .idempotencyKey("key-1")
                .build();

        assertEquals(options1, options2);
    }

    @Test
    @DisplayName("toString should contain field values")
    void testToString() {
        InvokeOptions options = InvokeOptions.builder()
                .idempotencyKey("test-key")
                .build();

        String str = options.toString();
        assertTrue(str.contains("test-key"));
    }

    @Test
    @DisplayName("headers should be immutable")
    void testHeadersImmutable() {
        InvokeOptions options = InvokeOptions.builder()
                .header("key", "value")
                .build();

        Map<String, String> headers = options.getHeaders();
        assertThrows(UnsupportedOperationException.class, () ->
                headers.put("new-key", "new-value"));
    }

    @Test
    @DisplayName("builder with headers map should work")
    void testBuilderWithHeadersMap() {
        Map<String, String> headers = Map.of("X-Header-1", "value1", "X-Header-2", "value2");

        InvokeOptions options = InvokeOptions.builder()
                .headers(headers)
                .build();

        assertEquals("value1", options.getHeaders().get("X-Header-1"));
        assertEquals("value2", options.getHeaders().get("X-Header-2"));
    }
}
