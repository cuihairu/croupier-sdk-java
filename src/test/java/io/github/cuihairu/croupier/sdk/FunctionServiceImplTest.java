package io.github.cuihairu.croupier.sdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.protobuf.ByteString;
import io.github.cuihairu.croupier.sdk.v1.CancelJobRequest;
import io.github.cuihairu.croupier.sdk.v1.InvokeRequest;
import io.github.cuihairu.croupier.sdk.v1.InvokeResponse;
import io.github.cuihairu.croupier.sdk.v1.JobEvent;
import io.github.cuihairu.croupier.sdk.v1.JobStreamRequest;
import io.github.cuihairu.croupier.sdk.v1.StartJobResponse;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

class FunctionServiceImplTest {

    @Test
    void invokeReturnsHandlerResponse() throws Exception {
        FunctionServiceImpl service = new FunctionServiceImpl(
            Map.of("f1", (ctx, payload) -> "ok:" + payload)
        );

        try {
            RecordingObserver<InvokeResponse> observer = new RecordingObserver<>();
            service.invoke(
                InvokeRequest.newBuilder()
                    .setFunctionId("f1")
                    .setPayload(ByteString.copyFromUtf8("hi"))
                    .build(),
                observer
            );

            observer.await();
            assertTrue(observer.errors.isEmpty(), "expected no error");
            assertEquals(1, observer.values.size());
            assertEquals("ok:hi", observer.values.get(0).getPayload().toStringUtf8());
        } finally {
            service.shutdown();
        }
    }

    @Test
    void invokeReturnsNotFoundWhenMissingFunction() throws Exception {
        FunctionServiceImpl service = new FunctionServiceImpl(Map.of());
        try {
            RecordingObserver<InvokeResponse> observer = new RecordingObserver<>();
            service.invoke(
                InvokeRequest.newBuilder()
                    .setFunctionId("missing")
                    .setPayload(ByteString.copyFromUtf8("hi"))
                    .build(),
                observer
            );

            observer.await();
            assertEquals(1, observer.errors.size());
            Status status = Status.fromThrowable(observer.errors.get(0));
            assertEquals(Status.Code.NOT_FOUND, status.getCode());
        } finally {
            service.shutdown();
        }
    }

    @Test
    void startJobAndStreamJobCompletes() throws Exception {
        FunctionServiceImpl service = new FunctionServiceImpl(
            Map.of("f1", (ctx, payload) -> "done:" + payload)
        );

        try {
            RecordingObserver<StartJobResponse> startObserver = new RecordingObserver<>();
            service.startJob(
                InvokeRequest.newBuilder()
                    .setFunctionId("f1")
                    .setPayload(ByteString.copyFromUtf8("hi"))
                    .build(),
                startObserver
            );
            startObserver.await();
            assertTrue(startObserver.errors.isEmpty(), "expected no start error");
            String jobId = startObserver.values.get(0).getJobId();
            assertNotNull(jobId);

            RecordingObserver<JobEvent> streamObserver = new RecordingObserver<>();
            service.streamJob(
                JobStreamRequest.newBuilder().setJobId(jobId).build(),
                streamObserver
            );
            streamObserver.await();

            List<JobEvent> events = streamObserver.values;
            assertTrue(events.size() >= 2, "expected at least started+completed");
            assertEquals("started", events.get(0).getType());
            assertEquals("completed", events.get(events.size() - 1).getType());
        } finally {
            service.shutdown();
        }
    }

    @Test
    void cancelJobEmitsCancelled() throws Exception {
        FunctionServiceImpl service = new FunctionServiceImpl(
            Map.of("f1", (ctx, payload) -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    throw ex;
                }
                return "late";
            })
        );

        try {
            RecordingObserver<StartJobResponse> startObserver = new RecordingObserver<>();
            service.startJob(
                InvokeRequest.newBuilder()
                    .setFunctionId("f1")
                    .setPayload(ByteString.copyFromUtf8("hi"))
                    .build(),
                startObserver
            );
            startObserver.await();
            String jobId = startObserver.values.get(0).getJobId();

            RecordingObserver<StartJobResponse> cancelObserver = new RecordingObserver<>();
            service.cancelJob(
                CancelJobRequest.newBuilder().setJobId(jobId).build(),
                cancelObserver
            );
            cancelObserver.await();
            assertTrue(cancelObserver.errors.isEmpty(), "expected no cancel error");

            RecordingObserver<JobEvent> streamObserver = new RecordingObserver<>();
            service.streamJob(
                JobStreamRequest.newBuilder().setJobId(jobId).build(),
                streamObserver
            );
            streamObserver.await();

            boolean sawCancelled = streamObserver.values.stream().anyMatch(e -> e.getType().equals("cancelled"));
            assertTrue(sawCancelled, "expected cancelled event");
        } finally {
            service.shutdown();
        }
    }

    private static class RecordingObserver<T> implements StreamObserver<T> {
        final List<T> values = new CopyOnWriteArrayList<>();
        final List<Throwable> errors = new CopyOnWriteArrayList<>();
        private final CountDownLatch done = new CountDownLatch(1);

        @Override
        public void onNext(T value) {
            values.add(value);
        }

        @Override
        public void onError(Throwable t) {
            errors.add(t);
            done.countDown();
        }

        @Override
        public void onCompleted() {
            done.countDown();
        }

        void await() throws InterruptedException {
            if (!done.await(5, TimeUnit.SECONDS)) {
                throw new AssertionError("timed out waiting for observer completion");
            }
        }
    }
}
