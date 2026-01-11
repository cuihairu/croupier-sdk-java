package io.github.cuihairu.croupier.platform.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * PlatformService provides a unified interface for calling third-party platform APIs.
 * </pre>
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class PlatformServiceGrpc {

  private PlatformServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "croupier.platform.v1.PlatformService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.github.cuihairu.croupier.platform.v1.CallPlatformRequest,
      io.github.cuihairu.croupier.platform.v1.CallPlatformResponse> getCallPlatformMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CallPlatform",
      requestType = io.github.cuihairu.croupier.platform.v1.CallPlatformRequest.class,
      responseType = io.github.cuihairu.croupier.platform.v1.CallPlatformResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.cuihairu.croupier.platform.v1.CallPlatformRequest,
      io.github.cuihairu.croupier.platform.v1.CallPlatformResponse> getCallPlatformMethod() {
    io.grpc.MethodDescriptor<io.github.cuihairu.croupier.platform.v1.CallPlatformRequest, io.github.cuihairu.croupier.platform.v1.CallPlatformResponse> getCallPlatformMethod;
    if ((getCallPlatformMethod = PlatformServiceGrpc.getCallPlatformMethod) == null) {
      synchronized (PlatformServiceGrpc.class) {
        if ((getCallPlatformMethod = PlatformServiceGrpc.getCallPlatformMethod) == null) {
          PlatformServiceGrpc.getCallPlatformMethod = getCallPlatformMethod =
              io.grpc.MethodDescriptor.<io.github.cuihairu.croupier.platform.v1.CallPlatformRequest, io.github.cuihairu.croupier.platform.v1.CallPlatformResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CallPlatform"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.platform.v1.CallPlatformRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.platform.v1.CallPlatformResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PlatformServiceMethodDescriptorSupplier("CallPlatform"))
              .build();
        }
      }
    }
    return getCallPlatformMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.cuihairu.croupier.platform.v1.ListPlatformsRequest,
      io.github.cuihairu.croupier.platform.v1.ListPlatformsResponse> getListPlatformsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListPlatforms",
      requestType = io.github.cuihairu.croupier.platform.v1.ListPlatformsRequest.class,
      responseType = io.github.cuihairu.croupier.platform.v1.ListPlatformsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.cuihairu.croupier.platform.v1.ListPlatformsRequest,
      io.github.cuihairu.croupier.platform.v1.ListPlatformsResponse> getListPlatformsMethod() {
    io.grpc.MethodDescriptor<io.github.cuihairu.croupier.platform.v1.ListPlatformsRequest, io.github.cuihairu.croupier.platform.v1.ListPlatformsResponse> getListPlatformsMethod;
    if ((getListPlatformsMethod = PlatformServiceGrpc.getListPlatformsMethod) == null) {
      synchronized (PlatformServiceGrpc.class) {
        if ((getListPlatformsMethod = PlatformServiceGrpc.getListPlatformsMethod) == null) {
          PlatformServiceGrpc.getListPlatformsMethod = getListPlatformsMethod =
              io.grpc.MethodDescriptor.<io.github.cuihairu.croupier.platform.v1.ListPlatformsRequest, io.github.cuihairu.croupier.platform.v1.ListPlatformsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListPlatforms"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.platform.v1.ListPlatformsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.platform.v1.ListPlatformsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PlatformServiceMethodDescriptorSupplier("ListPlatforms"))
              .build();
        }
      }
    }
    return getListPlatformsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.cuihairu.croupier.platform.v1.ListPlatformMethodsRequest,
      io.github.cuihairu.croupier.platform.v1.ListPlatformMethodsResponse> getListPlatformMethodsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListPlatformMethods",
      requestType = io.github.cuihairu.croupier.platform.v1.ListPlatformMethodsRequest.class,
      responseType = io.github.cuihairu.croupier.platform.v1.ListPlatformMethodsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.cuihairu.croupier.platform.v1.ListPlatformMethodsRequest,
      io.github.cuihairu.croupier.platform.v1.ListPlatformMethodsResponse> getListPlatformMethodsMethod() {
    io.grpc.MethodDescriptor<io.github.cuihairu.croupier.platform.v1.ListPlatformMethodsRequest, io.github.cuihairu.croupier.platform.v1.ListPlatformMethodsResponse> getListPlatformMethodsMethod;
    if ((getListPlatformMethodsMethod = PlatformServiceGrpc.getListPlatformMethodsMethod) == null) {
      synchronized (PlatformServiceGrpc.class) {
        if ((getListPlatformMethodsMethod = PlatformServiceGrpc.getListPlatformMethodsMethod) == null) {
          PlatformServiceGrpc.getListPlatformMethodsMethod = getListPlatformMethodsMethod =
              io.grpc.MethodDescriptor.<io.github.cuihairu.croupier.platform.v1.ListPlatformMethodsRequest, io.github.cuihairu.croupier.platform.v1.ListPlatformMethodsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListPlatformMethods"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.platform.v1.ListPlatformMethodsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.platform.v1.ListPlatformMethodsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PlatformServiceMethodDescriptorSupplier("ListPlatformMethods"))
              .build();
        }
      }
    }
    return getListPlatformMethodsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      io.github.cuihairu.croupier.platform.v1.ReloadPlatformConfigResponse> getReloadPlatformConfigMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReloadPlatformConfig",
      requestType = com.google.protobuf.Empty.class,
      responseType = io.github.cuihairu.croupier.platform.v1.ReloadPlatformConfigResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      io.github.cuihairu.croupier.platform.v1.ReloadPlatformConfigResponse> getReloadPlatformConfigMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, io.github.cuihairu.croupier.platform.v1.ReloadPlatformConfigResponse> getReloadPlatformConfigMethod;
    if ((getReloadPlatformConfigMethod = PlatformServiceGrpc.getReloadPlatformConfigMethod) == null) {
      synchronized (PlatformServiceGrpc.class) {
        if ((getReloadPlatformConfigMethod = PlatformServiceGrpc.getReloadPlatformConfigMethod) == null) {
          PlatformServiceGrpc.getReloadPlatformConfigMethod = getReloadPlatformConfigMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, io.github.cuihairu.croupier.platform.v1.ReloadPlatformConfigResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReloadPlatformConfig"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.platform.v1.ReloadPlatformConfigResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PlatformServiceMethodDescriptorSupplier("ReloadPlatformConfig"))
              .build();
        }
      }
    }
    return getReloadPlatformConfigMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PlatformServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PlatformServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PlatformServiceStub>() {
        @java.lang.Override
        public PlatformServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PlatformServiceStub(channel, callOptions);
        }
      };
    return PlatformServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static PlatformServiceBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PlatformServiceBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PlatformServiceBlockingV2Stub>() {
        @java.lang.Override
        public PlatformServiceBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PlatformServiceBlockingV2Stub(channel, callOptions);
        }
      };
    return PlatformServiceBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PlatformServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PlatformServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PlatformServiceBlockingStub>() {
        @java.lang.Override
        public PlatformServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PlatformServiceBlockingStub(channel, callOptions);
        }
      };
    return PlatformServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PlatformServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PlatformServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PlatformServiceFutureStub>() {
        @java.lang.Override
        public PlatformServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PlatformServiceFutureStub(channel, callOptions);
        }
      };
    return PlatformServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * PlatformService provides a unified interface for calling third-party platform APIs.
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * CallPlatform invokes a method on a third-party platform.
     * The request and response are JSON encoded bytes.
     * </pre>
     */
    default void callPlatform(io.github.cuihairu.croupier.platform.v1.CallPlatformRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.platform.v1.CallPlatformResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCallPlatformMethod(), responseObserver);
    }

    /**
     * <pre>
     * ListPlatforms returns all available platforms.
     * </pre>
     */
    default void listPlatforms(io.github.cuihairu.croupier.platform.v1.ListPlatformsRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.platform.v1.ListPlatformsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListPlatformsMethod(), responseObserver);
    }

    /**
     * <pre>
     * ListPlatformMethods returns the methods supported by a platform.
     * </pre>
     */
    default void listPlatformMethods(io.github.cuihairu.croupier.platform.v1.ListPlatformMethodsRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.platform.v1.ListPlatformMethodsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListPlatformMethodsMethod(), responseObserver);
    }

    /**
     * <pre>
     * ReloadPlatformConfig reloads the platform configuration.
     * </pre>
     */
    default void reloadPlatformConfig(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.platform.v1.ReloadPlatformConfigResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReloadPlatformConfigMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service PlatformService.
   * <pre>
   * PlatformService provides a unified interface for calling third-party platform APIs.
   * </pre>
   */
  public static abstract class PlatformServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return PlatformServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service PlatformService.
   * <pre>
   * PlatformService provides a unified interface for calling third-party platform APIs.
   * </pre>
   */
  public static final class PlatformServiceStub
      extends io.grpc.stub.AbstractAsyncStub<PlatformServiceStub> {
    private PlatformServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PlatformServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PlatformServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * CallPlatform invokes a method on a third-party platform.
     * The request and response are JSON encoded bytes.
     * </pre>
     */
    public void callPlatform(io.github.cuihairu.croupier.platform.v1.CallPlatformRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.platform.v1.CallPlatformResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCallPlatformMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * ListPlatforms returns all available platforms.
     * </pre>
     */
    public void listPlatforms(io.github.cuihairu.croupier.platform.v1.ListPlatformsRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.platform.v1.ListPlatformsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListPlatformsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * ListPlatformMethods returns the methods supported by a platform.
     * </pre>
     */
    public void listPlatformMethods(io.github.cuihairu.croupier.platform.v1.ListPlatformMethodsRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.platform.v1.ListPlatformMethodsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListPlatformMethodsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * ReloadPlatformConfig reloads the platform configuration.
     * </pre>
     */
    public void reloadPlatformConfig(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.platform.v1.ReloadPlatformConfigResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReloadPlatformConfigMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service PlatformService.
   * <pre>
   * PlatformService provides a unified interface for calling third-party platform APIs.
   * </pre>
   */
  public static final class PlatformServiceBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<PlatformServiceBlockingV2Stub> {
    private PlatformServiceBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PlatformServiceBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PlatformServiceBlockingV2Stub(channel, callOptions);
    }

    /**
     * <pre>
     * CallPlatform invokes a method on a third-party platform.
     * The request and response are JSON encoded bytes.
     * </pre>
     */
    public io.github.cuihairu.croupier.platform.v1.CallPlatformResponse callPlatform(io.github.cuihairu.croupier.platform.v1.CallPlatformRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getCallPlatformMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ListPlatforms returns all available platforms.
     * </pre>
     */
    public io.github.cuihairu.croupier.platform.v1.ListPlatformsResponse listPlatforms(io.github.cuihairu.croupier.platform.v1.ListPlatformsRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getListPlatformsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ListPlatformMethods returns the methods supported by a platform.
     * </pre>
     */
    public io.github.cuihairu.croupier.platform.v1.ListPlatformMethodsResponse listPlatformMethods(io.github.cuihairu.croupier.platform.v1.ListPlatformMethodsRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getListPlatformMethodsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ReloadPlatformConfig reloads the platform configuration.
     * </pre>
     */
    public io.github.cuihairu.croupier.platform.v1.ReloadPlatformConfigResponse reloadPlatformConfig(com.google.protobuf.Empty request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getReloadPlatformConfigMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service PlatformService.
   * <pre>
   * PlatformService provides a unified interface for calling third-party platform APIs.
   * </pre>
   */
  public static final class PlatformServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<PlatformServiceBlockingStub> {
    private PlatformServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PlatformServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PlatformServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * CallPlatform invokes a method on a third-party platform.
     * The request and response are JSON encoded bytes.
     * </pre>
     */
    public io.github.cuihairu.croupier.platform.v1.CallPlatformResponse callPlatform(io.github.cuihairu.croupier.platform.v1.CallPlatformRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCallPlatformMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ListPlatforms returns all available platforms.
     * </pre>
     */
    public io.github.cuihairu.croupier.platform.v1.ListPlatformsResponse listPlatforms(io.github.cuihairu.croupier.platform.v1.ListPlatformsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListPlatformsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ListPlatformMethods returns the methods supported by a platform.
     * </pre>
     */
    public io.github.cuihairu.croupier.platform.v1.ListPlatformMethodsResponse listPlatformMethods(io.github.cuihairu.croupier.platform.v1.ListPlatformMethodsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListPlatformMethodsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ReloadPlatformConfig reloads the platform configuration.
     * </pre>
     */
    public io.github.cuihairu.croupier.platform.v1.ReloadPlatformConfigResponse reloadPlatformConfig(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReloadPlatformConfigMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service PlatformService.
   * <pre>
   * PlatformService provides a unified interface for calling third-party platform APIs.
   * </pre>
   */
  public static final class PlatformServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<PlatformServiceFutureStub> {
    private PlatformServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PlatformServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PlatformServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * CallPlatform invokes a method on a third-party platform.
     * The request and response are JSON encoded bytes.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.cuihairu.croupier.platform.v1.CallPlatformResponse> callPlatform(
        io.github.cuihairu.croupier.platform.v1.CallPlatformRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCallPlatformMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * ListPlatforms returns all available platforms.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.cuihairu.croupier.platform.v1.ListPlatformsResponse> listPlatforms(
        io.github.cuihairu.croupier.platform.v1.ListPlatformsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListPlatformsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * ListPlatformMethods returns the methods supported by a platform.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.cuihairu.croupier.platform.v1.ListPlatformMethodsResponse> listPlatformMethods(
        io.github.cuihairu.croupier.platform.v1.ListPlatformMethodsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListPlatformMethodsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * ReloadPlatformConfig reloads the platform configuration.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.cuihairu.croupier.platform.v1.ReloadPlatformConfigResponse> reloadPlatformConfig(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReloadPlatformConfigMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CALL_PLATFORM = 0;
  private static final int METHODID_LIST_PLATFORMS = 1;
  private static final int METHODID_LIST_PLATFORM_METHODS = 2;
  private static final int METHODID_RELOAD_PLATFORM_CONFIG = 3;

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
        case METHODID_CALL_PLATFORM:
          serviceImpl.callPlatform((io.github.cuihairu.croupier.platform.v1.CallPlatformRequest) request,
              (io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.platform.v1.CallPlatformResponse>) responseObserver);
          break;
        case METHODID_LIST_PLATFORMS:
          serviceImpl.listPlatforms((io.github.cuihairu.croupier.platform.v1.ListPlatformsRequest) request,
              (io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.platform.v1.ListPlatformsResponse>) responseObserver);
          break;
        case METHODID_LIST_PLATFORM_METHODS:
          serviceImpl.listPlatformMethods((io.github.cuihairu.croupier.platform.v1.ListPlatformMethodsRequest) request,
              (io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.platform.v1.ListPlatformMethodsResponse>) responseObserver);
          break;
        case METHODID_RELOAD_PLATFORM_CONFIG:
          serviceImpl.reloadPlatformConfig((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.platform.v1.ReloadPlatformConfigResponse>) responseObserver);
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
          getCallPlatformMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.cuihairu.croupier.platform.v1.CallPlatformRequest,
              io.github.cuihairu.croupier.platform.v1.CallPlatformResponse>(
                service, METHODID_CALL_PLATFORM)))
        .addMethod(
          getListPlatformsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.cuihairu.croupier.platform.v1.ListPlatformsRequest,
              io.github.cuihairu.croupier.platform.v1.ListPlatformsResponse>(
                service, METHODID_LIST_PLATFORMS)))
        .addMethod(
          getListPlatformMethodsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.cuihairu.croupier.platform.v1.ListPlatformMethodsRequest,
              io.github.cuihairu.croupier.platform.v1.ListPlatformMethodsResponse>(
                service, METHODID_LIST_PLATFORM_METHODS)))
        .addMethod(
          getReloadPlatformConfigMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.protobuf.Empty,
              io.github.cuihairu.croupier.platform.v1.ReloadPlatformConfigResponse>(
                service, METHODID_RELOAD_PLATFORM_CONFIG)))
        .build();
  }

  private static abstract class PlatformServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PlatformServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.github.cuihairu.croupier.platform.v1.Platform.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PlatformService");
    }
  }

  private static final class PlatformServiceFileDescriptorSupplier
      extends PlatformServiceBaseDescriptorSupplier {
    PlatformServiceFileDescriptorSupplier() {}
  }

  private static final class PlatformServiceMethodDescriptorSupplier
      extends PlatformServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    PlatformServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (PlatformServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PlatformServiceFileDescriptorSupplier())
              .addMethod(getCallPlatformMethod())
              .addMethod(getListPlatformsMethod())
              .addMethod(getListPlatformMethodsMethod())
              .addMethod(getReloadPlatformConfigMethod())
              .build();
        }
      }
    }
    return result;
  }
}
