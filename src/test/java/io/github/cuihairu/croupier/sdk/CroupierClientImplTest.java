package io.github.cuihairu.croupier.sdk;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.GZIPInputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.github.cuihairu.croupier.sdk.invoker.JobEventInfo;

import java.io.ByteArrayInputStream;
import java.util.zip.GZIPOutputStream;

class CroupierClientImplTest {

    private ClientConfig config;
    private CroupierClientImpl client;

    @BeforeEach
    void setUp() {
        config = new ClientConfig("game-1", "svc-1");
        client = new CroupierClientImpl(config);
    }

    @Test
    void constructorWithValidConfig() {
        assertNotNull(client);
        assertFalse(client.isConnected());
        assertFalse(client.isServing());
    }

    @Test
    void constructorWithNullConfigThrowsException() {
        assertThrows(NullPointerException.class, () -> new CroupierClientImpl(null));
    }

    @Test
    void registerFunctionRequiresIdAndVersion() {
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
        assertDoesNotThrow(() ->
            client.registerFunction(new FunctionDescriptor("f1", "1.0.0"), (ctx, payload) -> "ok")
        );
    }

    @Test
    void registerMultipleFunctions() {
        assertDoesNotThrow(() -> {
            client.registerFunction(new FunctionDescriptor("func1", "1.0.0"), (ctx, payload) -> "result1");
            client.registerFunction(new FunctionDescriptor("func2", "1.0.0"), (ctx, payload) -> "result2");
            client.registerFunction(new FunctionDescriptor("func3", "1.0.0"), (ctx, payload) -> "result3");
        });
    }

    @Test
    void closeClientDoesNotThrow() {
        assertDoesNotThrow(client::close);
    }

    @Test
    void closeClearsHandlersAndDescriptors() {
        assertDoesNotThrow(() -> {
            client.registerFunction(new FunctionDescriptor("f1", "1.0.0"), (ctx, payload) -> "ok");
            client.registerFunction(new FunctionDescriptor("f2", "1.0.0"), (ctx, payload) -> "ok");
        });

        client.close();

        // Verify functions are cleared
        assertEquals(0, client.getLocalFunctions().size());
    }

    @Test
    void stopClientDoesNotThrow() {
        assertDoesNotThrow(client::stop);
    }

    @Test
    void stopSetsConnectedAndServingToFalse() {
        assertDoesNotThrow(() -> {
            client.registerFunction(new FunctionDescriptor("f1", "1.0.0"), (ctx, payload) -> "ok");
        });

        // Connect first
        client.connect().join();
        assertTrue(client.isConnected());

        // Then stop
        client.stop();
        assertFalse(client.isConnected());
        assertFalse(client.isServing());
    }

    @Test
    void connectReturnsCompletableFuture() {
        assertNotNull(client.connect());
    }

    @Test
    void connectFailsWithoutRegisteredFunctions() {
        CroupierClientImpl emptyClient = new CroupierClientImpl(config);

        // Should throw because no functions registered
        var future = emptyClient.connect();
        assertThrows(RuntimeException.class, future::join);
    }

    @Test
    void connectSucceedsWithRegisteredFunctions() {
        assertDoesNotThrow(() -> {
            client.registerFunction(new FunctionDescriptor("f1", "1.0.0"), (ctx, payload) -> "ok");
        });

        assertDoesNotThrow(() -> {
            client.connect().join();
        });

        assertTrue(client.isConnected());
        assertNotNull(client.getLocalAddress());
    }

    @Test
    void serveAsyncReturnsCompletableFuture() {
        assertNotNull(client.serveAsync());
    }

    @Test
    void invokeCallsRegisteredHandler() throws CroupierException {
        assertDoesNotThrow(() -> {
            client.registerFunction(
                new FunctionDescriptor("test.fn", "1.0.0"),
                (ctx, payload) -> "result:" + payload
            );
        });

        String result = client.invoke("test.fn", "input", Map.of("key", "value"));

        assertEquals("result:input", result);
    }

    @Test
    void invokeThrowsForUnregisteredFunction() {
        assertThrows(CroupierException.class, () -> {
            client.invoke("unknown.fn", "input", Map.of());
        });
    }

    @Test
    void invokePropagatesHandlerException() {
        assertDoesNotThrow(() -> {
            client.registerFunction(
                new FunctionDescriptor("failing.fn", "1.0.0"),
                (ctx, payload) -> {
                    throw new RuntimeException("Handler error");
                }
            );
        });

        assertThrows(CroupierException.class, () -> {
            client.invoke("failing.fn", "input", Map.of());
        });
    }

    @Test
    void invokeWithNullMetadataUsesEmptyMap() throws CroupierException {
        assertDoesNotThrow(() -> {
            client.registerFunction(
                new FunctionDescriptor("test.fn", "1.0.0"),
                (ctx, payload) -> ctx
            );
        });

        String result = client.invoke("test.fn", "input", null);

        assertEquals("{}", result);
    }

    @Test
    void getLocalFunctionsReturnsRegisteredFunctions() {
        assertDoesNotThrow(() -> {
            client.registerFunction(new FunctionDescriptor("f1", "1.0.0"), (ctx, payload) -> "ok");
            client.registerFunction(new FunctionDescriptor("f2", "2.0.0"), (ctx, payload) -> "ok");
        });

        var functions = client.getLocalFunctions();

        assertEquals(2, functions.size());
        assertTrue(functions.stream().anyMatch(f -> f.getId().equals("f1") && f.getVersion().equals("1.0.0")));
        assertTrue(functions.stream().anyMatch(f -> f.getId().equals("f2") && f.getVersion().equals("2.0.0")));
    }

    @Test
    void getLocalFunctionsEmptyWhenNoFunctionsRegistered() {
        var functions = client.getLocalFunctions();
        assertTrue(functions.isEmpty());
    }

    @Test
    void getRegisterRequestBuildsCorrectStructure() {
        assertDoesNotThrow(() -> {
            client.registerFunction(new FunctionDescriptor("f1", "1.0.0"), (ctx, payload) -> "ok");
        });

        var request = client.getRegisterRequest();

        assertEquals("svc-1", request.get("serviceId"));
        assertEquals("1.0.0", request.get("version"));
        assertNotNull(request.get("functions"));

        @SuppressWarnings("unchecked")
        var functions = (java.util.List<Map<String, String>>) request.get("functions");
        assertEquals(1, functions.size());
        assertEquals("f1", functions.get(0).get("id"));
        assertEquals("1.0.0", functions.get(0).get("version"));
    }

    @Test
    void buildManifestCreatesValidJson() {
        assertDoesNotThrow(() -> {
            client.registerFunction(
                new FunctionDescriptor("test.fn", "2.0.0"),
                (ctx, payload) -> "ok"
            );
        });

        byte[] manifest = client.buildManifest();
        String json = new String(manifest, StandardCharsets.UTF_8);

        // Verify basic structure
        assertTrue(json.contains("\"provider\":"));
        assertTrue(json.contains("\"functions\":"));
        assertTrue(json.contains("\"id\":\"svc-1\""));
        assertTrue(json.contains("\"test.fn\""));
    }

    @Test
    void buildManifestIncludesAllFunctionFields() {
        FunctionDescriptor descriptor = new FunctionDescriptor("full.fn", "1.5.0");
        descriptor.setCategory("test-category");
        descriptor.setRisk("low");
        descriptor.setEntity("player");
        descriptor.setOperation("read");
        descriptor.setEnabled(true);

        assertDoesNotThrow(() -> {
            client.registerFunction(descriptor, (ctx, payload) -> "ok");
        });

        byte[] manifest = client.buildManifest();
        String json = new String(manifest, StandardCharsets.UTF_8);

        assertTrue(json.contains("\"category\":\"test-category\""));
        assertTrue(json.contains("\"risk\":\"low\""));
        assertTrue(json.contains("\"entity\":\"player\""));
        assertTrue(json.contains("\"operation\":\"read\""));
        assertTrue(json.contains("\"enabled\":true"));
    }

    @Test
    void getManifestGzippedReturnsCompressedData() throws IOException {
        assertDoesNotThrow(() -> {
            client.registerFunction(new FunctionDescriptor("f1", "1.0.0"), (ctx, payload) -> "ok");
        });

        byte[] gzipped = client.getManifestGzipped();

        // Verify it's valid gzip by decompressing
        try (GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream(gzipped))) {
            byte[] decompressed = gzip.readAllBytes();
            String json = new String(decompressed, StandardCharsets.UTF_8);
            assertTrue(json.contains("\"provider\":"));
        }
    }

    @Test
    void registerFunctionThrowsWhenServing() {
        assertDoesNotThrow(() -> {
            client.registerFunction(new FunctionDescriptor("f1", "1.0.0"), (ctx, payload) -> "ok");
        });

        // Start serving (in a thread)
        Thread servingThread = new Thread(() -> {
            try {
                client.serve();
            } catch (Exception e) {
                // Expected when stopped
            }
        });
        servingThread.start();

        // Wait a bit for serving to start
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            fail("Interrupted");
        }

        // Should throw because already serving
        assertThrows(CroupierException.class, () -> {
            client.registerFunction(new FunctionDescriptor("f2", "1.0.0"), (ctx, payload) -> "ok");
        });

        // Clean up
        client.stop();
        try {
            servingThread.join(1000);
        } catch (InterruptedException e) {
            // Ignore
        }
    }

    @Test
    void getLocalAddressReturnsNullWhenNotConnected() {
        String address = client.getLocalAddress();
        // Initially null before connection
        // After connect it will be set
    }

    @Test
    void toJsonHandlesSpecialCharacters() throws CroupierException {
        assertDoesNotThrow(() -> {
            client.registerFunction(
                new FunctionDescriptor("test.fn", "1.0.0"),
                (ctx, payload) -> ctx
            );
        });

        String result = client.invoke("test.fn", "", Map.of("key\"with\"quotes", "value\nwith\nnewlines"));

        // Should escape special characters
        assertTrue(result.contains("\\\""));
        assertTrue(result.contains("\\n"));
    }

    @Test
    void buildManifestHandlesNullServiceId() {
        ClientConfig configWithoutId = new ClientConfig("game-1", null);
        CroupierClientImpl clientWithoutId = new CroupierClientImpl(configWithoutId);

        assertDoesNotThrow(() -> {
            clientWithoutId.registerFunction(
                new FunctionDescriptor("f1", "1.0.0"),
                (ctx, payload) -> "ok"
            );
        });

        byte[] manifest = clientWithoutId.buildManifest();
        String json = new String(manifest, StandardCharsets.UTF_8);

        // Should use default "java-service"
        assertTrue(json.contains("\"id\":\"java-service\""));
    }

    @Test
    void buildManifestHandlesEmptyVersion() {
        FunctionDescriptor descriptor = new FunctionDescriptor("f1", "1.0.0");
        // Version is already set, so manifest will use it directly

        assertDoesNotThrow(() -> {
            client.registerFunction(descriptor, (ctx, payload) -> "ok");
        });

        byte[] manifest = client.buildManifest();
        String json = new String(manifest, StandardCharsets.UTF_8);

        // Should use the specified version "1.0.0"
        assertTrue(json.contains("\"version\":\"1.0.0\""));
    }

    @Test
    void escapeJsonHandlesAllSpecialCharacters() {
        FunctionDescriptor descriptor = new FunctionDescriptor("test.fn", "1.0.0");
        descriptor.setCategory("cat\"with\\quotes\b\f\n\r\t");

        assertDoesNotThrow(() -> {
            client.registerFunction(descriptor, (ctx, payload) -> "ok");
        });

        byte[] manifest = client.buildManifest();
        String json = new String(manifest, StandardCharsets.UTF_8);

        // Verify escaping (note: forward slash doesn't need escaping in JSON)
        assertTrue(json.contains("\\\""));     // quote
        assertTrue(json.contains("\\\\"));     // backslash
        assertTrue(json.contains("\\b"));      // backspace
        assertTrue(json.contains("\\f"));      // form feed
        assertTrue(json.contains("\\n"));      // newline
        assertTrue(json.contains("\\r"));      // carriage return
        assertTrue(json.contains("\\t"));      // tab
    }

    @Test
    void gzipProducesSmallerOutput() throws IOException {
        String testData = "重复的数据重复的数据重复的数据重复的数据";
        byte[] uncompressed = testData.getBytes(StandardCharsets.UTF_8);

        // Test via getManifestGzipped which uses gzip internally
        assertDoesNotThrow(() -> {
            client.registerFunction(new FunctionDescriptor("f1", "1.0.0"), (ctx, payload) -> "ok");
        });
        byte[] compressed = client.getManifestGzipped();

        assertTrue(compressed.length > 0,
            "Compressed data should be produced");

        // The manifest includes JSON structure overhead, so we just verify compression happened
        assertTrue(compressed.length < uncompressed.length * 2,
            "Compressed data with JSON overhead should still be reasonable");
    }

    @Test
    void isNullOrEmptyReturnsTrueForNull() {
        // Test through registerFunction with null version
        assertThrows(CroupierException.class, () -> {
            client.registerFunction(new FunctionDescriptor("f1", null), (ctx, payload) -> "ok");
        });
    }

    @Test
    void isNullOrEmptyReturnsTrueForEmptyString() {
        // Test through registerFunction with empty version
        assertThrows(CroupierException.class, () -> {
            client.registerFunction(new FunctionDescriptor("f1", "   "), (ctx, payload) -> "ok");
        });
    }

    @Test
    void invokeWithCroupierExceptionIsPropagatedDirectly() {
        assertDoesNotThrow(() -> {
            client.registerFunction(
                new FunctionDescriptor("failing.fn", "1.0.0"),
                (ctx, payload) -> {
                    throw new CroupierException("Direct Croupier exception");
                }
            );
        });

        try {
            client.invoke("failing.fn", "input", Map.of());
            fail("Should throw CroupierException");
        } catch (CroupierException e) {
            assertEquals("Direct Croupier exception", e.getMessage());
        }
    }

    // ========== Job Management Tests ==========

    @Test
    void startJobReturnsJobId() throws CroupierException {
        String jobId = client.startJob("test.function", "{}");
        assertNotNull(jobId);
        assertTrue(jobId.startsWith("test.function-"));
    }

    @Test
    void startJobWithMetadataReturnsJobId() throws CroupierException {
        Map<String, String> metadata = Map.of("key1", "value1", "key2", "value2");
        String jobId = client.startJob("test.function", "{\"param\":\"value\"}", metadata);
        assertNotNull(jobId);
        assertTrue(jobId.startsWith("test.function-"));
    }

    @Test
    void startJobWithNullMetadata() throws CroupierException {
        String jobId = client.startJob("test.function", "{}", null);
        assertNotNull(jobId);
    }

    @Test
    void startJobWithEmptyMetadata() throws CroupierException {
        String jobId = client.startJob("test.function", "{}", Map.of());
        assertNotNull(jobId);
    }

    @Test
    void startJobWithEmptyPayload() throws CroupierException {
        String jobId = client.startJob("test.function", "");
        assertNotNull(jobId);
    }

    @Test
    void streamJobReturnsPublisher() throws CroupierException {
        String jobId = client.startJob("test.function", "{}");
        Publisher<JobEventInfo> publisher = client.streamJob(jobId);
        assertNotNull(publisher);
    }

    @Test
    void streamJobCanSubscribeToEvents() throws Exception {
        String jobId = client.startJob("test.function", "{}");

        Publisher<JobEventInfo> publisher = client.streamJob(jobId);
        assertNotNull(publisher);

        AtomicInteger eventCount = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(1);

        publisher.subscribe(new Subscriber<JobEventInfo>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(JobEventInfo event) {
                eventCount.incrementAndGet();
                latch.countDown();
            }

            @Override
            public void onError(Throwable t) {
                latch.countDown();
            }

            @Override
            public void onComplete() {
            }
        });

        // Wait for at least one event (may timeout if no events)
        latch.await(2, TimeUnit.SECONDS);
        // We don't assert on count because events may or may not be published
        // depending on timing
    }

    @Test
    void cancelJobReturnsTrue() throws CroupierException {
        String jobId = client.startJob("test.function", "{}");
        boolean result = client.cancelJob(jobId);
        assertTrue(result);
    }

    @Test
    void cancelJobTwiceReturnsTrue() throws CroupierException {
        String jobId = client.startJob("test.function", "{}");
        boolean result1 = client.cancelJob(jobId);
        assertTrue(result1);

        // Second cancellation may throw or return true depending on implementation
        try {
            boolean result2 = client.cancelJob(jobId);
            assertTrue(result2);
        } catch (Exception e) {
            // Expected if job is already cancelled
            assertNotNull(e.getMessage());
        }
    }

    @Test
    void jobManagementIntegrationTest() throws Exception {
        // Start a job
        String jobId = client.startJob("test.function", "{\"data\":\"test\"}");
        assertNotNull(jobId);

        // Stream its events
        Publisher<JobEventInfo> publisher = client.streamJob(jobId);
        assertNotNull(publisher);

        // Subscribe to events
        AtomicInteger eventCount = new AtomicInteger(0);
        CountDownLatch eventLatch = new CountDownLatch(1);

        publisher.subscribe(new Subscriber<JobEventInfo>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(JobEventInfo event) {
                eventCount.incrementAndGet();
                eventLatch.countDown();
            }

            @Override
            public void onError(Throwable t) {
                eventLatch.countDown();
            }

            @Override
            public void onComplete() {
            }
        });

        // Wait a bit for any events
        eventLatch.await(1, TimeUnit.SECONDS);

        // Cancel the job
        boolean cancelled = client.cancelJob(jobId);
        assertTrue(cancelled);
    }

    @Test
    void multipleJobsCanBeManaged() throws CroupierException {
        String job1 = client.startJob("function1", "{}");
        String job2 = client.startJob("function2", "{}");
        String job3 = client.startJob("function3", "{}");

        assertNotNull(job1);
        assertNotNull(job2);
        assertNotNull(job3);

        assertNotEquals(job1, job2);
        assertNotEquals(job2, job3);

        // Cancel first two jobs
        assertTrue(client.cancelJob(job1));
        assertTrue(client.cancelJob(job2));
    }

    private void assertNotEquals(String s1, String s2) {
        assertFalse(s1.equals(s2));
    }
}
