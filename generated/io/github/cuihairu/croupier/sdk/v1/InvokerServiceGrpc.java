package io.github.cuihairu.croupier.sdk.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * SDK Invoker Service - Main interface for invoking functions
 * This is what SDK developers will use to call game functions
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.71.0)",
    comments = "Source: croupier/sdk/v1/invoker.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class InvokerServiceGrpc {

  private InvokerServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "croupier.sdk.v1.InvokerService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.github.cuihairu.croupier.sdk.v1.InvokeRequest,
      io.github.cuihairu.croupier.sdk.v1.InvokeResponse> getInvokeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Invoke",
      requestType = io.github.cuihairu.croupier.sdk.v1.InvokeRequest.class,
      responseType = io.github.cuihairu.croupier.sdk.v1.InvokeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.cuihairu.croupier.sdk.v1.InvokeRequest,
      io.github.cuihairu.croupier.sdk.v1.InvokeResponse> getInvokeMethod() {
    io.grpc.MethodDescriptor<io.github.cuihairu.croupier.sdk.v1.InvokeRequest, io.github.cuihairu.croupier.sdk.v1.InvokeResponse> getInvokeMethod;
    if ((getInvokeMethod = InvokerServiceGrpc.getInvokeMethod) == null) {
      synchronized (InvokerServiceGrpc.class) {
        if ((getInvokeMethod = InvokerServiceGrpc.getInvokeMethod) == null) {
          InvokerServiceGrpc.getInvokeMethod = getInvokeMethod =
              io.grpc.MethodDescriptor.<io.github.cuihairu.croupier.sdk.v1.InvokeRequest, io.github.cuihairu.croupier.sdk.v1.InvokeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Invoke"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.sdk.v1.InvokeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.sdk.v1.InvokeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new InvokerServiceMethodDescriptorSupplier("Invoke"))
              .build();
        }
      }
    }
    return getInvokeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.cuihairu.croupier.sdk.v1.InvokeRequest,
      io.github.cuihairu.croupier.sdk.v1.StartJobResponse> getStartJobMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StartJob",
      requestType = io.github.cuihairu.croupier.sdk.v1.InvokeRequest.class,
      responseType = io.github.cuihairu.croupier.sdk.v1.StartJobResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.cuihairu.croupier.sdk.v1.InvokeRequest,
      io.github.cuihairu.croupier.sdk.v1.StartJobResponse> getStartJobMethod() {
    io.grpc.MethodDescriptor<io.github.cuihairu.croupier.sdk.v1.InvokeRequest, io.github.cuihairu.croupier.sdk.v1.StartJobResponse> getStartJobMethod;
    if ((getStartJobMethod = InvokerServiceGrpc.getStartJobMethod) == null) {
      synchronized (InvokerServiceGrpc.class) {
        if ((getStartJobMethod = InvokerServiceGrpc.getStartJobMethod) == null) {
          InvokerServiceGrpc.getStartJobMethod = getStartJobMethod =
              io.grpc.MethodDescriptor.<io.github.cuihairu.croupier.sdk.v1.InvokeRequest, io.github.cuihairu.croupier.sdk.v1.StartJobResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StartJob"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.sdk.v1.InvokeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.sdk.v1.StartJobResponse.getDefaultInstance()))
              .setSchemaDescriptor(new InvokerServiceMethodDescriptorSupplier("StartJob"))
              .build();
        }
      }
    }
    return getStartJobMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.cuihairu.croupier.sdk.v1.JobStreamRequest,
      io.github.cuihairu.croupier.sdk.v1.JobEvent> getStreamJobMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StreamJob",
      requestType = io.github.cuihairu.croupier.sdk.v1.JobStreamRequest.class,
      responseType = io.github.cuihairu.croupier.sdk.v1.JobEvent.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<io.github.cuihairu.croupier.sdk.v1.JobStreamRequest,
      io.github.cuihairu.croupier.sdk.v1.JobEvent> getStreamJobMethod() {
    io.grpc.MethodDescriptor<io.github.cuihairu.croupier.sdk.v1.JobStreamRequest, io.github.cuihairu.croupier.sdk.v1.JobEvent> getStreamJobMethod;
    if ((getStreamJobMethod = InvokerServiceGrpc.getStreamJobMethod) == null) {
      synchronized (InvokerServiceGrpc.class) {
        if ((getStreamJobMethod = InvokerServiceGrpc.getStreamJobMethod) == null) {
          InvokerServiceGrpc.getStreamJobMethod = getStreamJobMethod =
              io.grpc.MethodDescriptor.<io.github.cuihairu.croupier.sdk.v1.JobStreamRequest, io.github.cuihairu.croupier.sdk.v1.JobEvent>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StreamJob"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.sdk.v1.JobStreamRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.sdk.v1.JobEvent.getDefaultInstance()))
              .setSchemaDescriptor(new InvokerServiceMethodDescriptorSupplier("StreamJob"))
              .build();
        }
      }
    }
    return getStreamJobMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.cuihairu.croupier.sdk.v1.CancelJobRequest,
      io.github.cuihairu.croupier.sdk.v1.StartJobResponse> getCancelJobMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CancelJob",
      requestType = io.github.cuihairu.croupier.sdk.v1.CancelJobRequest.class,
      responseType = io.github.cuihairu.croupier.sdk.v1.StartJobResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.cuihairu.croupier.sdk.v1.CancelJobRequest,
      io.github.cuihairu.croupier.sdk.v1.StartJobResponse> getCancelJobMethod() {
    io.grpc.MethodDescriptor<io.github.cuihairu.croupier.sdk.v1.CancelJobRequest, io.github.cuihairu.croupier.sdk.v1.StartJobResponse> getCancelJobMethod;
    if ((getCancelJobMethod = InvokerServiceGrpc.getCancelJobMethod) == null) {
      synchronized (InvokerServiceGrpc.class) {
        if ((getCancelJobMethod = InvokerServiceGrpc.getCancelJobMethod) == null) {
          InvokerServiceGrpc.getCancelJobMethod = getCancelJobMethod =
              io.grpc.MethodDescriptor.<io.github.cuihairu.croupier.sdk.v1.CancelJobRequest, io.github.cuihairu.croupier.sdk.v1.StartJobResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CancelJob"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.sdk.v1.CancelJobRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.sdk.v1.StartJobResponse.getDefaultInstance()))
              .setSchemaDescriptor(new InvokerServiceMethodDescriptorSupplier("CancelJob"))
              .build();
        }
      }
    }
    return getCancelJobMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static InvokerServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InvokerServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InvokerServiceStub>() {
        @java.lang.Override
        public InvokerServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InvokerServiceStub(channel, callOptions);
        }
      };
    return InvokerServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static InvokerServiceBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InvokerServiceBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InvokerServiceBlockingV2Stub>() {
        @java.lang.Override
        public InvokerServiceBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InvokerServiceBlockingV2Stub(channel, callOptions);
        }
      };
    return InvokerServiceBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static InvokerServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InvokerServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InvokerServiceBlockingStub>() {
        @java.lang.Override
        public InvokerServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InvokerServiceBlockingStub(channel, callOptions);
        }
      };
    return InvokerServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static InvokerServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InvokerServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InvokerServiceFutureStub>() {
        @java.lang.Override
        public InvokerServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InvokerServiceFutureStub(channel, callOptions);
        }
      };
    return InvokerServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * SDK Invoker Service - Main interface for invoking functions
   * This is what SDK developers will use to call game functions
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * Synchronous function invocation
     * </pre>
     */
    default void invoke(io.github.cuihairu.croupier.sdk.v1.InvokeRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.sdk.v1.InvokeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getInvokeMethod(), responseObserver);
    }

    /**
     * <pre>
     * Asynchronous job starting
     * </pre>
     */
    default void startJob(io.github.cuihairu.croupier.sdk.v1.InvokeRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.sdk.v1.StartJobResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStartJobMethod(), responseObserver);
    }

    /**
     * <pre>
     * Stream job events for async operations
     * </pre>
     */
    default void streamJob(io.github.cuihairu.croupier.sdk.v1.JobStreamRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.sdk.v1.JobEvent> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStreamJobMethod(), responseObserver);
    }

    /**
     * <pre>
     * Cancel a running job
     * </pre>
     */
    default void cancelJob(io.github.cuihairu.croupier.sdk.v1.CancelJobRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.sdk.v1.StartJobResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCancelJobMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service InvokerService.
   * <pre>
   * SDK Invoker Service - Main interface for invoking functions
   * This is what SDK developers will use to call game functions
   * </pre>
   */
  public static abstract class InvokerServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return InvokerServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service InvokerService.
   * <pre>
   * SDK Invoker Service - Main interface for invoking functions
   * This is what SDK developers will use to call game functions
   * </pre>
   */
  public static final class InvokerServiceStub
      extends io.grpc.stub.AbstractAsyncStub<InvokerServiceStub> {
    private InvokerServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InvokerServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InvokerServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Synchronous function invocation
     * </pre>
     */
    public void invoke(io.github.cuihairu.croupier.sdk.v1.InvokeRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.sdk.v1.InvokeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getInvokeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Asynchronous job starting
     * </pre>
     */
    public void startJob(io.github.cuihairu.croupier.sdk.v1.InvokeRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.sdk.v1.StartJobResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getStartJobMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Stream job events for async operations
     * </pre>
     */
    public void streamJob(io.github.cuihairu.croupier.sdk.v1.JobStreamRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.sdk.v1.JobEvent> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getStreamJobMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Cancel a running job
     * </pre>
     */
    public void cancelJob(io.github.cuihairu.croupier.sdk.v1.CancelJobRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.sdk.v1.StartJobResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCancelJobMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service InvokerService.
   * <pre>
   * SDK Invoker Service - Main interface for invoking functions
   * This is what SDK developers will use to call game functions
   * </pre>
   */
  public static final class InvokerServiceBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<InvokerServiceBlockingV2Stub> {
    private InvokerServiceBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InvokerServiceBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InvokerServiceBlockingV2Stub(channel, callOptions);
    }

    /**
     * <pre>
     * Synchronous function invocation
     * </pre>
     */
    public io.github.cuihairu.croupier.sdk.v1.InvokeResponse invoke(io.github.cuihairu.croupier.sdk.v1.InvokeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getInvokeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Asynchronous job starting
     * </pre>
     */
    public io.github.cuihairu.croupier.sdk.v1.StartJobResponse startJob(io.github.cuihairu.croupier.sdk.v1.InvokeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getStartJobMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Stream job events for async operations
     * </pre>
     */
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/10918")
    public io.grpc.stub.BlockingClientCall<?, io.github.cuihairu.croupier.sdk.v1.JobEvent>
        streamJob(io.github.cuihairu.croupier.sdk.v1.JobStreamRequest request) {
      return io.grpc.stub.ClientCalls.blockingV2ServerStreamingCall(
          getChannel(), getStreamJobMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Cancel a running job
     * </pre>
     */
    public io.github.cuihairu.croupier.sdk.v1.StartJobResponse cancelJob(io.github.cuihairu.croupier.sdk.v1.CancelJobRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCancelJobMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service InvokerService.
   * <pre>
   * SDK Invoker Service - Main interface for invoking functions
   * This is what SDK developers will use to call game functions
   * </pre>
   */
  public static final class InvokerServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<InvokerServiceBlockingStub> {
    private InvokerServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InvokerServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InvokerServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Synchronous function invocation
     * </pre>
     */
    public io.github.cuihairu.croupier.sdk.v1.InvokeResponse invoke(io.github.cuihairu.croupier.sdk.v1.InvokeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getInvokeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Asynchronous job starting
     * </pre>
     */
    public io.github.cuihairu.croupier.sdk.v1.StartJobResponse startJob(io.github.cuihairu.croupier.sdk.v1.InvokeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getStartJobMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Stream job events for async operations
     * </pre>
     */
    public java.util.Iterator<io.github.cuihairu.croupier.sdk.v1.JobEvent> streamJob(
        io.github.cuihairu.croupier.sdk.v1.JobStreamRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getStreamJobMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Cancel a running job
     * </pre>
     */
    public io.github.cuihairu.croupier.sdk.v1.StartJobResponse cancelJob(io.github.cuihairu.croupier.sdk.v1.CancelJobRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCancelJobMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service InvokerService.
   * <pre>
   * SDK Invoker Service - Main interface for invoking functions
   * This is what SDK developers will use to call game functions
   * </pre>
   */
  public static final class InvokerServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<InvokerServiceFutureStub> {
    private InvokerServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InvokerServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InvokerServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Synchronous function invocation
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.cuihairu.croupier.sdk.v1.InvokeResponse> invoke(
        io.github.cuihairu.croupier.sdk.v1.InvokeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getInvokeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Asynchronous job starting
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.cuihairu.croupier.sdk.v1.StartJobResponse> startJob(
        io.github.cuihairu.croupier.sdk.v1.InvokeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getStartJobMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Cancel a running job
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.cuihairu.croupier.sdk.v1.StartJobResponse> cancelJob(
        io.github.cuihairu.croupier.sdk.v1.CancelJobRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCancelJobMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_INVOKE = 0;
  private static final int METHODID_START_JOB = 1;
  private static final int METHODID_STREAM_JOB = 2;
  private static final int METHODID_CANCEL_JOB = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_INVOKE:
          serviceImpl.invoke((io.github.cuihairu.croupier.sdk.v1.InvokeRequest) request,
              (io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.sdk.v1.InvokeResponse>) responseObserver);
          break;
        case METHODID_START_JOB:
          serviceImpl.startJob((io.github.cuihairu.croupier.sdk.v1.InvokeRequest) request,
              (io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.sdk.v1.StartJobResponse>) responseObserver);
          break;
        case METHODID_STREAM_JOB:
          serviceImpl.streamJob((io.github.cuihairu.croupier.sdk.v1.JobStreamRequest) request,
              (io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.sdk.v1.JobEvent>) responseObserver);
          break;
        case METHODID_CANCEL_JOB:
          serviceImpl.cancelJob((io.github.cuihairu.croupier.sdk.v1.CancelJobRequest) request,
              (io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.sdk.v1.StartJobResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getInvokeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.cuihairu.croupier.sdk.v1.InvokeRequest,
              io.github.cuihairu.croupier.sdk.v1.InvokeResponse>(
                service, METHODID_INVOKE)))
        .addMethod(
          getStartJobMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.cuihairu.croupier.sdk.v1.InvokeRequest,
              io.github.cuihairu.croupier.sdk.v1.StartJobResponse>(
                service, METHODID_START_JOB)))
        .addMethod(
          getStreamJobMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              io.github.cuihairu.croupier.sdk.v1.JobStreamRequest,
              io.github.cuihairu.croupier.sdk.v1.JobEvent>(
                service, METHODID_STREAM_JOB)))
        .addMethod(
          getCancelJobMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.cuihairu.croupier.sdk.v1.CancelJobRequest,
              io.github.cuihairu.croupier.sdk.v1.StartJobResponse>(
                service, METHODID_CANCEL_JOB)))
        .build();
  }

  private static abstract class InvokerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    InvokerServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.github.cuihairu.croupier.sdk.v1.Invoker.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("InvokerService");
    }
  }

  private static final class InvokerServiceFileDescriptorSupplier
      extends InvokerServiceBaseDescriptorSupplier {
    InvokerServiceFileDescriptorSupplier() {}
  }

  private static final class InvokerServiceMethodDescriptorSupplier
      extends InvokerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    InvokerServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (InvokerServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new InvokerServiceFileDescriptorSupplier())
              .addMethod(getInvokeMethod())
              .addMethod(getStartJobMethod())
              .addMethod(getStreamJobMethod())
              .addMethod(getCancelJobMethod())
              .build();
        }
      }
    }
    return result;
  }
}
