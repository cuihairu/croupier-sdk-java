package com.croupier.sdk;

import com.croupier.function.v1.CancelJobRequest;
import com.croupier.function.v1.InvokeRequest;
import com.croupier.function.v1.InvokeResponse;
import com.croupier.function.v1.JobEvent;
import com.croupier.function.v1.JobStreamRequest;
import com.croupier.function.v1.StartJobResponse;
import com.croupier.function.v1.FunctionServiceGrpc;
import com.google.protobuf.ByteString;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Local FunctionService implementation that forwards gRPC calls to registered handlers.
 */
public class FunctionServiceImpl extends FunctionServiceGrpc.FunctionServiceImplBase {
    private static final Logger logger = LoggerFactory.getLogger(FunctionServiceImpl.class);

    private final Map<String, FunctionHandler> handlers;
    private final ConcurrentHashMap<String, JobState> jobs = new ConcurrentHashMap<>();
    private final ExecutorService jobExecutor = Executors.newCachedThreadPool(r -> {
        Thread t = new Thread(r, "croupier-function-job");
        t.setDaemon(true);
        return t;
    });
    private final AtomicLong jobSeq = new AtomicLong();

    FunctionServiceImpl(Map<String, FunctionHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void invoke(InvokeRequest request, StreamObserver<InvokeResponse> responseObserver) {
        try {
            FunctionHandler handler = lookupHandler(request, responseObserver);
            if (handler == null) {
                return;
            }
            String payload = request.getPayload().toStringUtf8();
            String context = request.getMetadataMap().toString();
            String result = handler.handle(context, payload);
            ByteString body = result == null ? ByteString.EMPTY : ByteString.copyFrom(result, StandardCharsets.UTF_8);

            responseObserver.onNext(InvokeResponse.newBuilder().setPayload(body).build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription("handler failed").withCause(e).asRuntimeException());
        }
    }

    @Override
    public void startJob(InvokeRequest request, StreamObserver<StartJobResponse> responseObserver) {
        try {
            FunctionHandler handler = lookupHandler(request, responseObserver);
            if (handler == null) {
                return;
            }
            String payload = request.getPayload().toStringUtf8();
            String context = request.getMetadataMap().toString();

            String jobId = String.format("%s-%d", request.getFunctionId(), jobSeq.incrementAndGet());
            JobState state = new JobState();
            jobs.put(jobId, state);
            state.events.offer(jobEvent("started", "job started", null));

            state.future = jobExecutor.submit(() -> runJob(jobId, handler, context, payload, state));

            responseObserver.onNext(StartJobResponse.newBuilder().setJobId(jobId).build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription("start job failed").withCause(e).asRuntimeException());
        }
    }

    @Override
    public void streamJob(JobStreamRequest request, StreamObserver<JobEvent> responseObserver) {
        String jobId = request.getJobId();
        if (jobId == null || jobId.isEmpty()) {
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("job_id is required").asRuntimeException());
            return;
        }
        JobState state = jobs.get(jobId);
        if (state == null) {
            responseObserver.onError(Status.NOT_FOUND.withDescription("job not found").asRuntimeException());
            return;
        }
        try {
            while (true) {
                JobEvent event = state.events.poll(500, TimeUnit.MILLISECONDS);
                if (event != null) {
                    responseObserver.onNext(event);
                    if (isTerminal(event)) {
                        jobs.remove(jobId);
                        break;
                    }
                }
                if (state.completed.get() && state.events.isEmpty()) {
                    jobs.remove(jobId);
                    break;
                }
            }
            responseObserver.onCompleted();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            responseObserver.onError(Status.CANCELLED.withDescription("stream interrupted").asRuntimeException());
        }
    }

    @Override
    public void cancelJob(CancelJobRequest request, StreamObserver<StartJobResponse> responseObserver) {
        String jobId = request.getJobId();
        if (jobId == null || jobId.isEmpty()) {
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("job_id is required").asRuntimeException());
            return;
        }
        JobState state = jobs.get(jobId);
        if (state == null) {
            responseObserver.onError(Status.NOT_FOUND.withDescription("job not found").asRuntimeException());
            return;
        }
        state.cancelled.set(true);
        if (state.future != null) {
            state.future.cancel(true);
        }
        state.events.offer(jobEvent("cancelled", "job cancelled", null));
        state.completed.set(true);

        responseObserver.onNext(StartJobResponse.newBuilder().setJobId(jobId).build());
        responseObserver.onCompleted();
    }

    void shutdown() {
        jobs.forEach((id, state) -> {
            if (state.future != null) {
                state.future.cancel(true);
            }
            state.events.clear();
        });
        jobs.clear();
        jobExecutor.shutdownNow();
    }

    private FunctionHandler lookupHandler(InvokeRequest request, StreamObserver<?> observer) {
        if (request == null || request.getFunctionId().isEmpty()) {
            observer.onError(Status.INVALID_ARGUMENT.withDescription("function_id is required").asRuntimeException());
            return null;
        }
        FunctionHandler handler = handlers.get(request.getFunctionId());
        if (handler == null) {
            observer.onError(Status.NOT_FOUND.withDescription("function not registered").asRuntimeException());
            return null;
        }
        return handler;
    }

    private void runJob(
        String jobId,
        FunctionHandler handler,
        String context,
        String payload,
        JobState state
    ) {
        try {
            String result = handler.handle(context, payload);
            ByteString body = result == null ? ByteString.EMPTY : ByteString.copyFrom(result, StandardCharsets.UTF_8);
            state.events.offer(jobEvent("completed", "job completed", body));
        } catch (Exception e) {
            if (state.cancelled.get()) {
                state.events.offer(jobEvent("cancelled", "job cancelled", null));
            } else {
                state.events.offer(jobEvent("error", e.getMessage(), null));
            }
        } finally {
            state.completed.set(true);
        }
    }

    private JobEvent jobEvent(String type, String message, ByteString payload) {
        JobEvent.Builder builder = JobEvent.newBuilder().setType(type);
        if (message != null) {
            builder.setMessage(message);
        }
        if (payload != null) {
            builder.setPayload(payload);
        }
        return builder.build();
    }

    private boolean isTerminal(JobEvent event) {
        if (event == null) {
            return false;
        }
        String type = event.getType().toLowerCase();
        return type.equals("completed") || type.equals("error") || type.equals("cancelled");
    }

    private static class JobState {
        final BlockingQueue<JobEvent> events = new LinkedBlockingQueue<>();
        final AtomicBoolean completed = new AtomicBoolean(false);
        final AtomicBoolean cancelled = new AtomicBoolean(false);
        Future<?> future;
    }
}
