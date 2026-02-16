package io.github.cuihairu.croupier.sdk.invoker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for InvokerException.
 */
class InvokerExceptionTest {

    @Test
    @DisplayName("Exception with message should be created")
    void testExceptionWithMessage() {
        InvokerException exception = new InvokerException(
                InvokerException.ErrorCode.CONNECTION_FAILED,
                "Connection failed"
        );

        assertEquals(InvokerException.ErrorCode.CONNECTION_FAILED, exception.getErrorCode());
        assertEquals("Connection failed", exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Exception with message and cause should be created")
    void testExceptionWithMessageAndCause() {
        Throwable cause = new RuntimeException("Underlying error");
        InvokerException exception = new InvokerException(
                InvokerException.ErrorCode.INTERNAL,
                "Internal error",
                cause
        );

        assertEquals(InvokerException.ErrorCode.INTERNAL, exception.getErrorCode());
        assertEquals("Internal error", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    @DisplayName("Error codes should be defined")
    void testErrorCodes() {
        assertNotNull(InvokerException.ErrorCode.CONNECTION_FAILED);
        assertNotNull(InvokerException.ErrorCode.INTERNAL);
        assertNotNull(InvokerException.ErrorCode.TIMEOUT);
        assertNotNull(InvokerException.ErrorCode.NOT_FOUND);
        assertNotNull(InvokerException.ErrorCode.INVALID_ARGUMENT);
    }

    @Test
    @DisplayName("Exception should be throwable")
    void testExceptionIsThrowable() {
        assertThrows(InvokerException.class, () -> {
            throw new InvokerException(InvokerException.ErrorCode.TIMEOUT, "Timeout");
        });
    }

    @Test
    @DisplayName("Exception should preserve error code")
    void testErrorCodePreservation() {
        for (InvokerException.ErrorCode code : InvokerException.ErrorCode.values()) {
            InvokerException exception = new InvokerException(code, "Test message");
            assertEquals(code, exception.getErrorCode());
        }
    }
}
