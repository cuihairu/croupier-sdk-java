package io.github.cuihairu.croupier.sdk;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;

/**
 * Tests for FunctionHandler interface and implementations.
 */
class FunctionHandlerTest {

    @Test
    void syncFunctionHandlerExecutes() throws Exception {
        FunctionHandler handler = (context, payload) -> {
            return "{\"result\":\"success\"}".getBytes(StandardCharsets.UTF_8);
        };

		FunctionContext context = new FunctionContext("test-function", "test-game", "test-service");
		byte[] result = handler.apply(context, "{\"input\":\"test\"}".getBytes(StandardCharsets.UTF_8));

		assertArrayEquals("{\"result\":\"success\"}".getBytes(StandardCharsets.UTF_8), result);
    }

    @Test
    void asyncFunctionHandlerExecutes() throws Exception {
        AsyncFunctionHandler handler = (context, payload) -> {
            return CompletableFuture.supplyAsync(() -> {
                return "{\"result\":\"async-success\"}".getBytes(StandardCharsets.UTF_8);
            });
        };

		FunctionContext context = new FunctionContext("test-function", "test-game", "test-service");
		byte[] result = handler.apply(context, "{\"input\":\"test\"}".getBytes(StandardCharsets.UTF_8)).get();

		assertArrayEquals("{\"result\":\"async-success\"}".getBytes(StandardCharsets.UTF_8), result);
    }

    @Test
    void functionHandlerThrowsException() {
        FunctionHandler handler = (context, payload) -> {
            throw new RuntimeException("Test exception");
        };

		FunctionContext context = new FunctionContext("test-function", "test-game", "test-service");

		assertThrows(RuntimeException.class, () -> {
			handler.apply(context, new byte[0]);
		});
    }

    @Test
    void asyncFunctionHandlerThrowsException() {
        AsyncFunctionHandler handler = (context, payload) -> {
            return CompletableFuture.failedFuture(new RuntimeException("Async test exception"));
        };

		FunctionContext context = new FunctionContext("test-function", "test-game", "test-service");

		ExecutionException ex = assertThrows(ExecutionException.class, () -> {
			handler.apply(context, new byte[0]).get();
		});

		assertInstanceOf(RuntimeException.class, ex.getCause());
		assertEquals("Async test exception", ex.getCause().getMessage());
    }

    @Test
    void functionHandlerWithEmptyPayload() throws Exception {
        FunctionHandler handler = (context, payload) -> {
            return new byte[0];
        };

		FunctionContext context = new FunctionContext("test-function", "test-game", "test-service");
		byte[] result = handler.apply(context, new byte[0]);

		assertEquals(0, result.length);
    }

    @Test
    void functionHandlerWithNullPayload() throws Exception {
        FunctionHandler handler = (context, payload) -> {
            return "{\"result\":\"ok\"}".getBytes(StandardCharsets.UTF_8);
        };

		FunctionContext context = new FunctionContext("test-function", "test-game", "test-service");
		byte[] result = handler.apply(context, null);

		assertArrayEquals("{\"result\":\"ok\"}".getBytes(StandardCharsets.UTF_8), result);
    }

    @Test
    void functionHandlerWithLargePayload() throws Exception {
        FunctionHandler handler = (context, payload) -> {
            return payload; // Echo back the payload
        };

		FunctionContext context = new FunctionContext("test-function", "test-game", "test-service");
		byte[] largePayload = new byte[1024 * 1024]; // 1MB
		for (int i = 0; i < largePayload.length; i++) {
			largePayload[i] = (byte)(i % 256);
		}

		byte[] result = handler.apply(context, largePayload);

		assertArrayEquals(largePayload, result);
    }

    @Test
    void functionHandlerContextContainsCorrectInfo() throws Exception {
        FunctionHandler handler = (context, payload) -> {
            assertEquals("test-function", context.getFunctionId());
            assertEquals("test-game", context.getGameId());
            assertEquals("test-service", context.getServiceId());
            return "{\"ok\":true}".getBytes(StandardCharsets.UTF_8);
        };

		FunctionContext context = new FunctionContext("test-function", "test-game", "test-service");
		handler.apply(context, new byte[0]);
    }

    @Test
    void asyncFunctionHandlerWithDelayedResponse() throws Exception {
        AsyncFunctionHandler handler = (context, payload) -> {
            return CompletableFuture.supplyAsync(() -> {
				try {
					Thread.sleep(100); // Simulate delay
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
                return "{\"result\":\"delayed\"}".getBytes(StandardCharsets.UTF_8);
            });
        };

		FunctionContext context = new FunctionContext("test-function", "test-game", "test-service");
		byte[] result = handler.apply(context, new byte[0]).get();

		assertArrayEquals("{\"result\":\"delayed\"}".getBytes(StandardCharsets.UTF_8), result);
    }

    @Test
    void asyncFunctionHandlerWithMultipleHandlers() throws Exception {
        AsyncFunctionHandler handler1 = (context, payload) -> {
            return CompletableFuture.supplyAsync(() -> "{\"handler\":\"1\"}".getBytes(StandardCharsets.UTF_8));
        };

        AsyncFunctionHandler handler2 = (context, payload) -> {
            return CompletableFuture.supplyAsync(() -> "{\"handler\":\"2\"}".getBytes(StandardCharsets.UTF_8));
        };

		FunctionContext context = new FunctionContext("test-function", "test-game", "test-service");

		byte[] result1 = handler1.apply(context, new byte[0]).get();
		byte[] result2 = handler2.apply(context, new byte[0]).get();

		assertArrayEquals("{\"handler\":\"1\"}".getBytes(StandardCharsets.UTF_8), result1);
		assertArrayEquals("{\"handler\":\"2\"}".getBytes(StandardCharsets.UTF_8), result2);
    }

    @Test
    void functionHandlerChaining() throws Exception {
        FunctionHandler handler1 = (context, payload) -> {
            return "{\"step\":\"1\"}".getBytes(StandardCharsets.UTF_8);
        };

        FunctionHandler handler2 = (context, payload) -> {
            String previous = new String(payload, StandardCharsets.UTF_8);
            return (previous + ",\"step\":\"2\"}").getBytes(StandardCharsets.UTF_8);
        };

		FunctionContext context = new FunctionContext("test-function", "test-game", "test-service");

		byte[] result1 = handler1.apply(context, new byte[0]);
		byte[] result2 = handler2.apply(context, result1);

		assertEquals("{\"step\":\"1\",\"step\":\"2\"}", new String(result2, StandardCharsets.UTF_8));
    }
}
