package io.github.cuihairu.croupier.sdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class CroupierExceptionTest {

    @Test
    void constructorWithMessage() {
        String message = "Test error message";
        CroupierException exception = new CroupierException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    void constructorWithMessageAndCause() {
        String message = "Error with cause";
        Throwable cause = new RuntimeException("Root cause");
        CroupierException exception = new CroupierException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void constructorWithNullMessage() {
        CroupierException exception = new CroupierException((String) null);

        assertNull(exception.getMessage());
    }

    @Test
    void constructorWithNullCause() {
        CroupierException exception = new CroupierException("Message", null);

        assertEquals("Message", exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void canBeThrownAndCaught() {
        CroupierException thrown = assertThrows(
            CroupierException.class,
            () -> {
                throw new CroupierException("Test exception");
            }
        );

        assertEquals("Test exception", thrown.getMessage());
    }

    @Test
    void exceptionIsRuntimeException() {
        // CroupierException should extend RuntimeException
        assertTrue(new CroupierException("test") instanceof RuntimeException);
    }

    private void assertTrue(boolean condition) {
        if (!condition) {
            throw new AssertionError("Expected true but was false");
        }
    }
}
