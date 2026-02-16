package io.github.cuihairu.croupier.sdk.invoker;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import static io.github.cuihairu.croupier.sdk.invoker.InvokerException.ErrorCode;

/**
 * Implementation of the Invoker interface with job management support.
 *
 * <p>This implementation provides a framework for job management with reactive
 * streaming support. Actual transport layer integration is pending.</p>
 */
public class InvokerImpl implements Invoker {

    private static final Logger logger = LoggerFactory.getLogger(InvokerImpl.class);

    private final InvokerConfig config;
    private final Map<String, Map<String, Object>> schemas;
    private final Map<String, JobState> activeJobs;
    private volatile boolean connected;

    public InvokerImpl(InvokerConfig config) {
        this.config = config;
        this.schemas = new ConcurrentHashMap<>();
        this.activeJobs = new ConcurrentHashMap<>();
        this.connected = false;
    }

    @Override
    public void connect() throws InvokerException {
        if (connected) {
            return;
        }

        try {
            logger.info("Connecting to server/agent at: {}", config.getAddress());

            // TODO: Implement transport connection (NNG, etc.)
            connected = true;

            logger.info("Connected to: {}", config.getAddress());
        } catch (Exception e) {
            logger.error("Connection failed", e);
            throw new InvokerException(ErrorCode.CONNECTION_FAILED, "Connection failed: " + e.getMessage(), e);
        }
    }

    @Override
    public String invoke(String functionId, String payload) throws InvokerException {
        return invoke(functionId, payload, InvokeOptions.create());
    }

    @Override
    public String invoke(String functionId, String payload, InvokeOptions options) throws InvokerException {
        if (!connected) {
            connect();
        }

        try {
            logger.debug("Invoking function: {} with payload: {}", functionId, payload);

            // TODO: Implement via transport layer
            // For now, return a mock response
            return "{\"status\":\"ok\",\"message\":\"Invoke not fully implemented - transport layer needed\",\"functionId\":\"" + functionId + "\"}";

        } catch (Exception e) {
            if (e instanceof InvokerException) {
                throw (InvokerException) e;
            }
            throw new InvokerException(ErrorCode.INTERNAL, "Invoke failed: " + e.getMessage(), e);
        }
    }

    @Override
    public String startJob(String functionId, String payload) throws InvokerException {
        return startJob(functionId, payload, InvokeOptions.create());
    }

    @Override
    public String startJob(String functionId, String payload, InvokeOptions options) throws InvokerException {
        if (!connected) {
            connect();
        }

        // Generate unique job ID
        String jobId = generateJobId(functionId);

        try {
            logger.info("Starting job: {} for function: {} with jobId: {}",
                functionId, payload, jobId);

            // Create job state
            JobState jobState = new JobState(jobId, functionId, payload, options);
            activeJobs.put(jobId, jobState);

            // TODO: Send START_JOB request via transport layer
            // For now, simulate job start
            jobState.setStatus(JobStatus.STARTED);
            publishJobEvent(jobId, JobEventInfo.builder()
                .type("started")
                .jobId(jobId)
                .message("Job started")
                .done(false)
                .build());

            logger.info("Job started: {}", jobId);
            return jobId;

        } catch (Exception e) {
            activeJobs.remove(jobId);
            if (e instanceof InvokerException) {
                throw (InvokerException) e;
            }
            throw new InvokerException(ErrorCode.INTERNAL, "StartJob failed: " + e.getMessage(), e);
        }
    }

    @Override
    public Publisher<JobEventInfo> streamJob(String jobId) {
        logger.info("Streaming events for job: {}", jobId);

        // Return a reactive publisher for job events
        return new JobEventPublisher(jobId);
    }

    @Override
    public void cancelJob(String jobId) throws InvokerException {
        JobState jobState = activeJobs.get(jobId);

        if (jobState == null) {
            throw new InvokerException(ErrorCode.NOT_FOUND,
                "Job not found: " + jobId);
        }

        if (jobState.isDone()) {
            throw new InvokerException(ErrorCode.FAILED_PRECONDITION,
                "Job already finished: " + jobId + " (status: " + jobState.getStatus() + ")");
        }

        try {
            logger.info("Cancelling job: {}", jobId);

            // TODO: Send CANCEL_JOB request via transport layer
            // For now, simulate cancellation
            jobState.setStatus(JobStatus.CANCELLED);
            publishJobEvent(jobId, JobEventInfo.builder()
                .type("cancelled")
                .jobId(jobId)
                .message("Job cancelled")
                .done(true)
                .build());

            // Remove from active jobs after a delay
            activeJobs.remove(jobId);

            logger.info("Job cancelled: {}", jobId);

        } catch (Exception e) {
            if (e instanceof InvokerException) {
                throw (InvokerException) e;
            }
            throw new InvokerException(ErrorCode.INTERNAL, "CancelJob failed: " + e.getMessage(), e);
        }
    }

    @Override
    public void setSchema(String functionId, Map<String, Object> schema) {
        schemas.put(functionId, schema);
        logger.debug("Set schema for function: {}", functionId);
    }

    @Override
    public void close() throws InvokerException {
        // Cancel all active jobs
        for (String jobId : activeJobs.keySet()) {
            try {
                cancelJob(jobId);
            } catch (Exception e) {
                logger.warn("Failed to cancel job during close: {}", jobId, e);
            }
        }

        connected = false;
        schemas.clear();
        activeJobs.clear();
        logger.info("Invoker closed");
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    /**
     * Gets the number of active jobs.
     *
     * @return the count of active jobs
     */
    public int getActiveJobCount() {
        return activeJobs.size();
    }

    /**
     * Checks if a job exists.
     *
     * @param jobId the job ID to check
     * @return true if the job exists, false otherwise
     */
    public boolean hasJob(String jobId) {
        return activeJobs.containsKey(jobId);
    }

    /**
     * Gets the status of a job.
     *
     * @param jobId the job ID
     * @return the job status, or null if not found
     */
    public JobStatus getJobStatus(String jobId) {
        JobState state = activeJobs.get(jobId);
        return state != null ? state.getStatus() : null;
    }

    // Private helper methods

    private String generateJobId(String functionId) {
        // Generate a unique job ID
        // Format: {functionId}-{timestamp}-{random}
        long timestamp = System.currentTimeMillis();
        String random = UUID.randomUUID().toString().substring(0, 8);
        return functionId + "-" + timestamp + "-" + random;
    }

    private void publishJobEvent(String jobId, JobEventInfo event) {
        JobState state = activeJobs.get(jobId);
        if (state != null) {
            state.publishEvent(event);
        }
    }

    /**
     * Simulates job progress updates (for testing/future implementation).
     *
     * @param jobId the job ID to update
     * @param progress the progress percentage (0-100)
     * @param message the progress message
     */
    public void simulateJobProgress(String jobId, int progress, String message) {
        JobState state = activeJobs.get(jobId);
        if (state != null && !state.isDone()) {
            state.setStatus(JobStatus.PROGRESS);
            publishJobEvent(jobId, JobEventInfo.builder()
                .type("progress")
                .jobId(jobId)
                .progress(progress)
                .message(message)
                .done(false)
                .build());

            // If job is complete, mark as done
            if (progress >= 100) {
                state.setStatus(JobStatus.COMPLETED);
                publishJobEvent(jobId, JobEventInfo.builder()
                    .type("completed")
                    .jobId(jobId)
                    .message("Job completed")
                    .progress(100)
                    .done(true)
                    .build());
            }
        }
    }

    /**
     * Simulates job error (for testing/future implementation).
     *
     * @param jobId the job ID that failed
     * @param error the error message
     */
    public void simulateJobError(String jobId, String error) {
        JobState state = activeJobs.get(jobId);
        if (state != null) {
            state.setStatus(JobStatus.ERROR);
            publishJobEvent(jobId, JobEventInfo.builder()
                .type("error")
                .jobId(jobId)
                .error(error)
                .message("Job failed: " + error)
                .done(true)
                .build());
        }
    }

    // Inner classes

    /**
     * Job status enumeration.
     */
    public enum JobStatus {
        STARTED,
        PROGRESS,
        COMPLETED,
        ERROR,
        CANCELLED
    }

    /**
     * Internal state for tracking active jobs.
     */
    private static class JobState {
        private final String jobId;
        private final String functionId;
        private final String payload;
        private final InvokeOptions options;
        private final CopyOnWriteArrayList<JobEventSubscription> subscriptions;
        private volatile JobStatus status;
        private final long startTime;

        JobState(String jobId, String functionId, String payload, InvokeOptions options) {
            this.jobId = jobId;
            this.functionId = functionId;
            this.payload = payload;
            this.options = options;
            this.subscriptions = new CopyOnWriteArrayList<>();
            this.status = JobStatus.STARTED;
            this.startTime = System.currentTimeMillis();
        }

        String getJobId() {
            return jobId;
        }

        String getFunctionId() {
            return functionId;
        }

        String getPayload() {
            return payload;
        }

        InvokeOptions getOptions() {
            return options;
        }

        JobStatus getStatus() {
            return status;
        }

        void setStatus(JobStatus status) {
            this.status = status;
        }

        boolean isDone() {
            return status == JobStatus.COMPLETED ||
                   status == JobStatus.ERROR ||
                   status == JobStatus.CANCELLED;
        }

        void publishEvent(JobEventInfo event) {
            for (JobEventSubscription subscription : subscriptions) {
                subscription.emit(event);
            }
        }

        void addSubscription(JobEventSubscription subscription) {
            subscriptions.add(subscription);
        }

        void removeSubscription(JobEventSubscription subscription) {
            subscriptions.remove(subscription);
        }
    }

    /**
     * Reactive publisher for job events.
     */
    private class JobEventPublisher implements Publisher<JobEventInfo> {
        private final String jobId;

        JobEventPublisher(String jobId) {
            this.jobId = jobId;
        }

        @Override
        public void subscribe(Subscriber<? super JobEventInfo> subscriber) {
            JobState state = activeJobs.get(jobId);

            if (state == null) {
                subscriber.onError(new InvokerException(ErrorCode.NOT_FOUND,
                    "Job not found: " + jobId));
                return;
            }

            try {
                subscriber.onSubscribe(new JobEventSubscription(state, subscriber));
            } catch (Exception e) {
                subscriber.onError(e);
            }
        }
    }

    /**
     * Subscription for job event streams.
     */
    private static class JobEventSubscription implements org.reactivestreams.Subscription {
        private final JobState jobState;
        private final Subscriber<? super JobEventInfo> subscriber;
        private volatile boolean cancelled = false;
        private volatile long requested = 0;

        JobEventSubscription(JobState state, Subscriber<? super JobEventInfo> subscriber) {
            this.jobState = state;
            this.subscriber = subscriber;
            this.jobState.addSubscription(this);
        }

        void emit(JobEventInfo event) {
            if (!cancelled && requested > 0) {
                try {
                    subscriber.onNext(event);
                    if (requested != Long.MAX_VALUE) {
                        requested--;
                    }

                    // If job is done, complete the stream
                    if (event.isDone()) {
                        subscriber.onComplete();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }

        @Override
        public void request(long n) {
            if (n <= 0) {
                subscriber.onError(new IllegalArgumentException("Number requested must be positive"));
                return;
            }
            requested = (requested == Long.MAX_VALUE) ? Long.MAX_VALUE : requested + n;
        }

        @Override
        public void cancel() {
            cancelled = true;
            jobState.removeSubscription(this);
        }
    }
}
