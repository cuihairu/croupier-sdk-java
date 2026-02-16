package io.github.cuihairu.croupier.sdk.invoker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static io.github.cuihairu.croupier.sdk.invoker.InvokerException.ErrorCode;
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
    @DisplayName("invoke() should return mock response without transport")
    void testInvokeWithoutTransport() throws InvokerException {
        invoker.connect();
        String response = invoker.invoke("test.function", "{}");
        assertNotNull(response);
        assertTrue(response.contains("\"status\":\"ok\""));
        assertTrue(response.contains("test.function"));
    }

    @Test
    @DisplayName("invoke() with options should return mock response without transport")
    void testInvokeWithOptionsWithoutTransport() throws InvokerException {
        invoker.connect();
        InvokeOptions options = InvokeOptions.builder().build();
        String response = invoker.invoke("test.function", "{}", options);
        assertNotNull(response);
        assertTrue(response.contains("\"status\":\"ok\""));
        assertTrue(response.contains("test.function"));
    }

    @Test
    @DisplayName("startJob() should return job ID and track job")
    void testStartJob() throws InvokerException {
        invoker.connect();
        String jobId = invoker.startJob("test.function", "{}");
        assertNotNull(jobId);
        assertTrue(jobId.startsWith("test.function-"));
        assertTrue(invoker.hasJob(jobId));
        assertEquals(1, invoker.getActiveJobCount());
    }

    @Test
    @DisplayName("startJob() with options should return job ID")
    void testStartJobWithOptions() throws InvokerException {
        invoker.connect();
        InvokeOptions options = InvokeOptions.builder()
            .idempotencyKey("key-123")
            .timeout(5000)
            .build();
        String jobId = invoker.startJob("test.function", "{\"param\":\"value\"}", options);
        assertNotNull(jobId);
        assertTrue(invoker.hasJob(jobId));
    }

    @Test
    @DisplayName("startJob() should auto-connect if not connected")
    void testStartJobAutoConnect() throws InvokerException {
        assertFalse(invoker.isConnected());
        String jobId = invoker.startJob("test.function", "{}");
        assertNotNull(jobId);
        assertTrue(invoker.isConnected());
        assertTrue(invoker.hasJob(jobId));
    }

    @Test
    @DisplayName("streamJob() should return Publisher")
    void testStreamJob() throws InvokerException {
        invoker.connect();
        String jobId = invoker.startJob("test.function", "{}");
        Publisher<JobEventInfo> publisher = invoker.streamJob(jobId);
        assertNotNull(publisher);
    }

    @Test
    @DisplayName("streamJob() with invalid job ID should emit error")
    void testStreamJobInvalidId() throws InterruptedException {
        Publisher<JobEventInfo> publisher = invoker.streamJob("invalid-job-id");

        AtomicReference<Throwable> error = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);

        publisher.subscribe(new Subscriber<JobEventInfo>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);
            }

            @Override
            public void onNext(JobEventInfo event) {
                // Should not receive events
            }

            @Override
            public void onError(Throwable t) {
                error.set(t);
                latch.countDown();
            }

            @Override
            public void onComplete() {
                latch.countDown();
            }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS));
        assertNotNull(error.get());
        assertTrue(error.get() instanceof InvokerException);
    }

    @Test
    @DisplayName("cancelJob() should cancel running job")
    void testCancelJob() throws InvokerException {
        invoker.connect();
        String jobId = invoker.startJob("test.function", "{}");
        assertTrue(invoker.hasJob(jobId));

        invoker.cancelJob(jobId);
        // Job should be removed from active jobs after cancellation
        assertFalse(invoker.hasJob(jobId));
    }

    @Test
    @DisplayName("cancelJob() with invalid job ID should throw exception")
    void testCancelJobInvalidId() {
        InvokerException exception = assertThrows(InvokerException.class, () ->
                invoker.cancelJob("invalid-job-id"));
        assertEquals(ErrorCode.NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("cancelJob() on completed job should throw exception")
    void testCancelJobCompleted() throws InvokerException {
        invoker.connect();
        String jobId = invoker.startJob("test.function", "{}");

        // Simulate job completion
        invoker.simulateJobProgress(jobId, 100, "Job completed");

        InvokerException exception = assertThrows(InvokerException.class, () ->
                invoker.cancelJob(jobId));
        assertEquals(ErrorCode.FAILED_PRECONDITION, exception.getErrorCode());
    }

    @Test
    @DisplayName("invoke() should auto-connect if not connected")
    void testInvokeAutoConnect() throws InvokerException {
        assertFalse(invoker.isConnected());
        // This should auto-connect and return a response
        String response = invoker.invoke("test.function", "{}");
        assertNotNull(response);
        // Should be connected after auto-connect
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

    @Test
    @DisplayName("Job should emit events")
    void testJobEvents() throws Exception {
        invoker.connect();

        List<JobEventInfo> events = new ArrayList<>();
        CountDownLatch eventLatch = new CountDownLatch(1); // Wait for at least 1 event

        // Start the job - this will publish "started" event
        String jobId = invoker.startJob("test.function", "{}");

        // Subscribe to the job
        invoker.streamJob(jobId).subscribe(new Subscriber<JobEventInfo>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(JobEventInfo event) {
                events.add(event);
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

        // Give the subscription time to register
        Thread.sleep(100);

        // Simulate job progress to trigger "progress" event
        invoker.simulateJobProgress(jobId, 50, "Processing");

        assertTrue(eventLatch.await(5, TimeUnit.SECONDS),
            "Should receive at least 1 event, got: " + events.size() + ", events: " +
            events.stream().map(e -> e.getType()).collect(java.util.stream.Collectors.joining(", ")));

        // Verify we received the progress event
        JobEventInfo progressEvent = events.stream()
            .filter(e -> "progress".equals(e.getType()))
            .findFirst()
            .orElse(null);

        assertNotNull(progressEvent, "Should have received a progress event");
        assertEquals(jobId, progressEvent.getJobId());
        assertEquals(50, progressEvent.getProgress());
        assertFalse(progressEvent.isDone());
    }

    @Test
    @DisplayName("Job should complete with progress 100")
    void testJobCompletion() throws Exception {
        invoker.connect();
        String jobId = invoker.startJob("test.function", "{}");

        List<JobEventInfo> events = new ArrayList<>();
        CountDownLatch completedLatch = new CountDownLatch(1);

        invoker.streamJob(jobId).subscribe(new Subscriber<JobEventInfo>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(JobEventInfo event) {
                events.add(event);
                if (event.isDone()) {
                    completedLatch.countDown();
                }
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onComplete() {
            }
        });

        // Simulate job completion
        invoker.simulateJobProgress(jobId, 100, "Job completed successfully");

        assertTrue(completedLatch.await(5, TimeUnit.SECONDS));

        // Find completed event
        JobEventInfo completedEvent = events.stream()
            .filter(e -> "completed".equals(e.getType()))
            .findFirst()
            .orElse(null);
        assertNotNull(completedEvent);
        assertEquals(jobId, completedEvent.getJobId());
        assertEquals(100, completedEvent.getProgress());
        assertTrue(completedEvent.isDone());
        assertEquals(InvokerImpl.JobStatus.COMPLETED, invoker.getJobStatus(jobId));
    }

    @Test
    @DisplayName("Job should handle errors")
    void testJobError() throws Exception {
        invoker.connect();
        String jobId = invoker.startJob("test.function", "{}");

        List<JobEventInfo> events = new ArrayList<>();
        CountDownLatch errorLatch = new CountDownLatch(1);

        invoker.streamJob(jobId).subscribe(new Subscriber<JobEventInfo>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(JobEventInfo event) {
                events.add(event);
                if (event.isDone()) {
                    errorLatch.countDown();
                }
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onComplete() {
            }
        });

        // Simulate job error
        invoker.simulateJobError(jobId, "Something went wrong");

        assertTrue(errorLatch.await(5, TimeUnit.SECONDS));

        // Find error event
        JobEventInfo errorEvent = events.stream()
            .filter(e -> "error".equals(e.getType()))
            .findFirst()
            .orElse(null);
        assertNotNull(errorEvent);
        assertEquals(jobId, errorEvent.getJobId());
        assertNotNull(errorEvent.getError());
        assertTrue(errorEvent.isDone());
        assertEquals(InvokerImpl.JobStatus.ERROR, invoker.getJobStatus(jobId));
    }

    @Test
    @DisplayName("Multiple jobs should be tracked independently")
    void testMultipleJobs() throws InvokerException {
        invoker.connect();

        String job1 = invoker.startJob("function1", "{}");
        String job2 = invoker.startJob("function2", "{}");
        String job3 = invoker.startJob("function3", "{}");

        assertNotEquals(job1, job2);
        assertNotEquals(job2, job3);

        assertEquals(3, invoker.getActiveJobCount());
        assertTrue(invoker.hasJob(job1));
        assertTrue(invoker.hasJob(job2));
        assertTrue(invoker.hasJob(job3));

        // Cancel one job
        invoker.cancelJob(job2);
        assertEquals(2, invoker.getActiveJobCount());
        assertTrue(invoker.hasJob(job1));
        assertFalse(invoker.hasJob(job2));
        assertTrue(invoker.hasJob(job3));
    }

    @Test
    @DisplayName("close() should cancel all active jobs")
    void testCloseCancelsJobs() throws InvokerException {
        invoker.connect();

        String job1 = invoker.startJob("function1", "{}");
        String job2 = invoker.startJob("function2", "{}");

        assertEquals(2, invoker.getActiveJobCount());

        invoker.close();

        assertEquals(0, invoker.getActiveJobCount());
        assertFalse(invoker.isConnected());
    }

    @Test
    @DisplayName("Job status should update correctly")
    void testJobStatusTransitions() throws InvokerException {
        invoker.connect();
        String jobId = invoker.startJob("test.function", "{}");

        assertEquals(InvokerImpl.JobStatus.STARTED, invoker.getJobStatus(jobId));

        invoker.simulateJobProgress(jobId, 50, "Processing");
        assertEquals(InvokerImpl.JobStatus.PROGRESS, invoker.getJobStatus(jobId));

        invoker.simulateJobProgress(jobId, 100, "Done");
        assertEquals(InvokerImpl.JobStatus.COMPLETED, invoker.getJobStatus(jobId));
    }

    @Test
    @DisplayName("Multiple subscribers can stream same job")
    void testMultipleSubscribers() throws Exception {
        invoker.connect();
        String jobId = invoker.startJob("test.function", "{}");

        List<JobEventInfo> events1 = new ArrayList<>();
        List<JobEventInfo> events2 = new ArrayList<>();
        CountDownLatch latch1 = new CountDownLatch(1);
        CountDownLatch latch2 = new CountDownLatch(1);

        invoker.streamJob(jobId).subscribe(new Subscriber<JobEventInfo>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(JobEventInfo event) {
                events1.add(event);
                if (event.isDone()) {
                    latch1.countDown();
                }
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onComplete() {
            }
        });

        invoker.streamJob(jobId).subscribe(new Subscriber<JobEventInfo>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(JobEventInfo event) {
                events2.add(event);
                if (event.isDone()) {
                    latch2.countDown();
                }
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onComplete() {
            }
        });

        invoker.simulateJobProgress(jobId, 100, "Done");

        assertTrue(latch1.await(5, TimeUnit.SECONDS));
        assertTrue(latch2.await(5, TimeUnit.SECONDS));

        assertEquals(events1.size(), events2.size());
    }

    @Test
    @DisplayName("Subscription request should limit events")
    void testSubscriptionRequest() throws Exception {
        invoker.connect();
        String jobId = invoker.startJob("test.function", "{}");

        AtomicInteger eventCount = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(1);

        invoker.streamJob(jobId).subscribe(new Subscriber<JobEventInfo>() {
            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription s) {
                subscription = s;
                // Request only 2 events
                s.request(2);
            }

            @Override
            public void onNext(JobEventInfo event) {
                int count = eventCount.incrementAndGet();
                if (count >= 2) {
                    // Request more after receiving 2
                    subscription.request(1);
                }
                if (event.isDone()) {
                    latch.countDown();
                }
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onComplete() {
            }
        });

        invoker.simulateJobProgress(jobId, 100, "Done");

        assertTrue(latch.await(5, TimeUnit.SECONDS));
        assertTrue(eventCount.get() >= 2);
    }
}
