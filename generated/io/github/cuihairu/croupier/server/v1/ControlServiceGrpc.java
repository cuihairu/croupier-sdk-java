package io.github.cuihairu.croupier.server.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Server Control Service - Internal interface for agent registration and management
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.69.0)",
    comments = "Source: croupier/server/v1/server_control.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ControlServiceGrpc {

  private ControlServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "croupier.server.v1.ControlService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      io.github.cuihairu.croupier.server.v1.ListFunctionsSummaryResponse> getListFunctionsSummaryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListFunctionsSummary",
      requestType = com.google.protobuf.Empty.class,
      responseType = io.github.cuihairu.croupier.server.v1.ListFunctionsSummaryResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      io.github.cuihairu.croupier.server.v1.ListFunctionsSummaryResponse> getListFunctionsSummaryMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, io.github.cuihairu.croupier.server.v1.ListFunctionsSummaryResponse> getListFunctionsSummaryMethod;
    if ((getListFunctionsSummaryMethod = ControlServiceGrpc.getListFunctionsSummaryMethod) == null) {
      synchronized (ControlServiceGrpc.class) {
        if ((getListFunctionsSummaryMethod = ControlServiceGrpc.getListFunctionsSummaryMethod) == null) {
          ControlServiceGrpc.getListFunctionsSummaryMethod = getListFunctionsSummaryMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, io.github.cuihairu.croupier.server.v1.ListFunctionsSummaryResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListFunctionsSummary"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.server.v1.ListFunctionsSummaryResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ControlServiceMethodDescriptorSupplier("ListFunctionsSummary"))
              .build();
        }
      }
    }
    return getListFunctionsSummaryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.cuihairu.croupier.server.v1.RegisterRequest,
      io.github.cuihairu.croupier.server.v1.RegisterResponse> getRegisterMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Register",
      requestType = io.github.cuihairu.croupier.server.v1.RegisterRequest.class,
      responseType = io.github.cuihairu.croupier.server.v1.RegisterResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.cuihairu.croupier.server.v1.RegisterRequest,
      io.github.cuihairu.croupier.server.v1.RegisterResponse> getRegisterMethod() {
    io.grpc.MethodDescriptor<io.github.cuihairu.croupier.server.v1.RegisterRequest, io.github.cuihairu.croupier.server.v1.RegisterResponse> getRegisterMethod;
    if ((getRegisterMethod = ControlServiceGrpc.getRegisterMethod) == null) {
      synchronized (ControlServiceGrpc.class) {
        if ((getRegisterMethod = ControlServiceGrpc.getRegisterMethod) == null) {
          ControlServiceGrpc.getRegisterMethod = getRegisterMethod =
              io.grpc.MethodDescriptor.<io.github.cuihairu.croupier.server.v1.RegisterRequest, io.github.cuihairu.croupier.server.v1.RegisterResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Register"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.server.v1.RegisterRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.server.v1.RegisterResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ControlServiceMethodDescriptorSupplier("Register"))
              .build();
        }
      }
    }
    return getRegisterMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.cuihairu.croupier.server.v1.HeartbeatRequest,
      io.github.cuihairu.croupier.server.v1.HeartbeatResponse> getHeartbeatMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Heartbeat",
      requestType = io.github.cuihairu.croupier.server.v1.HeartbeatRequest.class,
      responseType = io.github.cuihairu.croupier.server.v1.HeartbeatResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.cuihairu.croupier.server.v1.HeartbeatRequest,
      io.github.cuihairu.croupier.server.v1.HeartbeatResponse> getHeartbeatMethod() {
    io.grpc.MethodDescriptor<io.github.cuihairu.croupier.server.v1.HeartbeatRequest, io.github.cuihairu.croupier.server.v1.HeartbeatResponse> getHeartbeatMethod;
    if ((getHeartbeatMethod = ControlServiceGrpc.getHeartbeatMethod) == null) {
      synchronized (ControlServiceGrpc.class) {
        if ((getHeartbeatMethod = ControlServiceGrpc.getHeartbeatMethod) == null) {
          ControlServiceGrpc.getHeartbeatMethod = getHeartbeatMethod =
              io.grpc.MethodDescriptor.<io.github.cuihairu.croupier.server.v1.HeartbeatRequest, io.github.cuihairu.croupier.server.v1.HeartbeatResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Heartbeat"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.server.v1.HeartbeatRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.server.v1.HeartbeatResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ControlServiceMethodDescriptorSupplier("Heartbeat"))
              .build();
        }
      }
    }
    return getHeartbeatMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.cuihairu.croupier.server.v1.RegisterCapabilitiesRequest,
      io.github.cuihairu.croupier.server.v1.RegisterCapabilitiesResponse> getRegisterCapabilitiesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RegisterCapabilities",
      requestType = io.github.cuihairu.croupier.server.v1.RegisterCapabilitiesRequest.class,
      responseType = io.github.cuihairu.croupier.server.v1.RegisterCapabilitiesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.cuihairu.croupier.server.v1.RegisterCapabilitiesRequest,
      io.github.cuihairu.croupier.server.v1.RegisterCapabilitiesResponse> getRegisterCapabilitiesMethod() {
    io.grpc.MethodDescriptor<io.github.cuihairu.croupier.server.v1.RegisterCapabilitiesRequest, io.github.cuihairu.croupier.server.v1.RegisterCapabilitiesResponse> getRegisterCapabilitiesMethod;
    if ((getRegisterCapabilitiesMethod = ControlServiceGrpc.getRegisterCapabilitiesMethod) == null) {
      synchronized (ControlServiceGrpc.class) {
        if ((getRegisterCapabilitiesMethod = ControlServiceGrpc.getRegisterCapabilitiesMethod) == null) {
          ControlServiceGrpc.getRegisterCapabilitiesMethod = getRegisterCapabilitiesMethod =
              io.grpc.MethodDescriptor.<io.github.cuihairu.croupier.server.v1.RegisterCapabilitiesRequest, io.github.cuihairu.croupier.server.v1.RegisterCapabilitiesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RegisterCapabilities"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.server.v1.RegisterCapabilitiesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.server.v1.RegisterCapabilitiesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ControlServiceMethodDescriptorSupplier("RegisterCapabilities"))
              .build();
        }
      }
    }
    return getRegisterCapabilitiesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.cuihairu.croupier.server.v1.GetAgentSystemInfoRequest,
      io.github.cuihairu.croupier.ops.v1.SystemInfo> getGetAgentSystemInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAgentSystemInfo",
      requestType = io.github.cuihairu.croupier.server.v1.GetAgentSystemInfoRequest.class,
      responseType = io.github.cuihairu.croupier.ops.v1.SystemInfo.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.cuihairu.croupier.server.v1.GetAgentSystemInfoRequest,
      io.github.cuihairu.croupier.ops.v1.SystemInfo> getGetAgentSystemInfoMethod() {
    io.grpc.MethodDescriptor<io.github.cuihairu.croupier.server.v1.GetAgentSystemInfoRequest, io.github.cuihairu.croupier.ops.v1.SystemInfo> getGetAgentSystemInfoMethod;
    if ((getGetAgentSystemInfoMethod = ControlServiceGrpc.getGetAgentSystemInfoMethod) == null) {
      synchronized (ControlServiceGrpc.class) {
        if ((getGetAgentSystemInfoMethod = ControlServiceGrpc.getGetAgentSystemInfoMethod) == null) {
          ControlServiceGrpc.getGetAgentSystemInfoMethod = getGetAgentSystemInfoMethod =
              io.grpc.MethodDescriptor.<io.github.cuihairu.croupier.server.v1.GetAgentSystemInfoRequest, io.github.cuihairu.croupier.ops.v1.SystemInfo>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAgentSystemInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.server.v1.GetAgentSystemInfoRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.ops.v1.SystemInfo.getDefaultInstance()))
              .setSchemaDescriptor(new ControlServiceMethodDescriptorSupplier("GetAgentSystemInfo"))
              .build();
        }
      }
    }
    return getGetAgentSystemInfoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.cuihairu.croupier.server.v1.ListAgentProcessesRequest,
      io.github.cuihairu.croupier.ops.v1.ListProcessesResponse> getListAgentProcessesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListAgentProcesses",
      requestType = io.github.cuihairu.croupier.server.v1.ListAgentProcessesRequest.class,
      responseType = io.github.cuihairu.croupier.ops.v1.ListProcessesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.cuihairu.croupier.server.v1.ListAgentProcessesRequest,
      io.github.cuihairu.croupier.ops.v1.ListProcessesResponse> getListAgentProcessesMethod() {
    io.grpc.MethodDescriptor<io.github.cuihairu.croupier.server.v1.ListAgentProcessesRequest, io.github.cuihairu.croupier.ops.v1.ListProcessesResponse> getListAgentProcessesMethod;
    if ((getListAgentProcessesMethod = ControlServiceGrpc.getListAgentProcessesMethod) == null) {
      synchronized (ControlServiceGrpc.class) {
        if ((getListAgentProcessesMethod = ControlServiceGrpc.getListAgentProcessesMethod) == null) {
          ControlServiceGrpc.getListAgentProcessesMethod = getListAgentProcessesMethod =
              io.grpc.MethodDescriptor.<io.github.cuihairu.croupier.server.v1.ListAgentProcessesRequest, io.github.cuihairu.croupier.ops.v1.ListProcessesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListAgentProcesses"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.server.v1.ListAgentProcessesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.ops.v1.ListProcessesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ControlServiceMethodDescriptorSupplier("ListAgentProcesses"))
              .build();
        }
      }
    }
    return getListAgentProcessesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.cuihairu.croupier.server.v1.QueryMetricsRequest,
      io.github.cuihairu.croupier.server.v1.QueryMetricsResponse> getQueryMetricsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QueryMetrics",
      requestType = io.github.cuihairu.croupier.server.v1.QueryMetricsRequest.class,
      responseType = io.github.cuihairu.croupier.server.v1.QueryMetricsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.cuihairu.croupier.server.v1.QueryMetricsRequest,
      io.github.cuihairu.croupier.server.v1.QueryMetricsResponse> getQueryMetricsMethod() {
    io.grpc.MethodDescriptor<io.github.cuihairu.croupier.server.v1.QueryMetricsRequest, io.github.cuihairu.croupier.server.v1.QueryMetricsResponse> getQueryMetricsMethod;
    if ((getQueryMetricsMethod = ControlServiceGrpc.getQueryMetricsMethod) == null) {
      synchronized (ControlServiceGrpc.class) {
        if ((getQueryMetricsMethod = ControlServiceGrpc.getQueryMetricsMethod) == null) {
          ControlServiceGrpc.getQueryMetricsMethod = getQueryMetricsMethod =
              io.grpc.MethodDescriptor.<io.github.cuihairu.croupier.server.v1.QueryMetricsRequest, io.github.cuihairu.croupier.server.v1.QueryMetricsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "QueryMetrics"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.server.v1.QueryMetricsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.server.v1.QueryMetricsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ControlServiceMethodDescriptorSupplier("QueryMetrics"))
              .build();
        }
      }
    }
    return getQueryMetricsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ControlServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ControlServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ControlServiceStub>() {
        @java.lang.Override
        public ControlServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ControlServiceStub(channel, callOptions);
        }
      };
    return ControlServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ControlServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ControlServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ControlServiceBlockingStub>() {
        @java.lang.Override
        public ControlServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ControlServiceBlockingStub(channel, callOptions);
        }
      };
    return ControlServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ControlServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ControlServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ControlServiceFutureStub>() {
        @java.lang.Override
        public ControlServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ControlServiceFutureStub(channel, callOptions);
        }
      };
    return ControlServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Server Control Service - Internal interface for agent registration and management
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * Summarized function catalog with UI/RBAC metadata (for dashboard)
     * </pre>
     */
    default void listFunctionsSummary(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.server.v1.ListFunctionsSummaryResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListFunctionsSummaryMethod(), responseObserver);
    }

    /**
     * <pre>
     * Agent registration with server
     * </pre>
     */
    default void register(io.github.cuihairu.croupier.server.v1.RegisterRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.server.v1.RegisterResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterMethod(), responseObserver);
    }

    /**
     * <pre>
     * Agent heartbeat
     * </pre>
     */
    default void heartbeat(io.github.cuihairu.croupier.server.v1.HeartbeatRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.server.v1.HeartbeatResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHeartbeatMethod(), responseObserver);
    }

    /**
     * <pre>
     * Provider capabilities registration
     * </pre>
     */
    default void registerCapabilities(io.github.cuihairu.croupier.server.v1.RegisterCapabilitiesRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.server.v1.RegisterCapabilitiesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterCapabilitiesMethod(), responseObserver);
    }

    /**
     * <pre>
     * GetAgentSystemInfo retrieves system info for a specific agent.
     * </pre>
     */
    default void getAgentSystemInfo(io.github.cuihairu.croupier.server.v1.GetAgentSystemInfoRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.ops.v1.SystemInfo> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAgentSystemInfoMethod(), responseObserver);
    }

    /**
     * <pre>
     * ListAgentProcesses lists processes on a specific agent.
     * </pre>
     */
    default void listAgentProcesses(io.github.cuihairu.croupier.server.v1.ListAgentProcessesRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.ops.v1.ListProcessesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListAgentProcessesMethod(), responseObserver);
    }

    /**
     * <pre>
     * QueryMetrics queries stored metrics from agents.
     * </pre>
     */
    default void queryMetrics(io.github.cuihairu.croupier.server.v1.QueryMetricsRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.server.v1.QueryMetricsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getQueryMetricsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ControlService.
   * <pre>
   * Server Control Service - Internal interface for agent registration and management
   * </pre>
   */
  public static abstract class ControlServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ControlServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ControlService.
   * <pre>
   * Server Control Service - Internal interface for agent registration and management
   * </pre>
   */
  public static final class ControlServiceStub
      extends io.grpc.stub.AbstractAsyncStub<ControlServiceStub> {
    private ControlServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ControlServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ControlServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Summarized function catalog with UI/RBAC metadata (for dashboard)
     * </pre>
     */
    public void listFunctionsSummary(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.server.v1.ListFunctionsSummaryResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListFunctionsSummaryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Agent registration with server
     * </pre>
     */
    public void register(io.github.cuihairu.croupier.server.v1.RegisterRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.server.v1.RegisterResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Agent heartbeat
     * </pre>
     */
    public void heartbeat(io.github.cuihairu.croupier.server.v1.HeartbeatRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.server.v1.HeartbeatResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHeartbeatMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Provider capabilities registration
     * </pre>
     */
    public void registerCapabilities(io.github.cuihairu.croupier.server.v1.RegisterCapabilitiesRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.server.v1.RegisterCapabilitiesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterCapabilitiesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * GetAgentSystemInfo retrieves system info for a specific agent.
     * </pre>
     */
    public void getAgentSystemInfo(io.github.cuihairu.croupier.server.v1.GetAgentSystemInfoRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.ops.v1.SystemInfo> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAgentSystemInfoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * ListAgentProcesses lists processes on a specific agent.
     * </pre>
     */
    public void listAgentProcesses(io.github.cuihairu.croupier.server.v1.ListAgentProcessesRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.ops.v1.ListProcessesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListAgentProcessesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * QueryMetrics queries stored metrics from agents.
     * </pre>
     */
    public void queryMetrics(io.github.cuihairu.croupier.server.v1.QueryMetricsRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.server.v1.QueryMetricsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getQueryMetricsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ControlService.
   * <pre>
   * Server Control Service - Internal interface for agent registration and management
   * </pre>
   */
  public static final class ControlServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ControlServiceBlockingStub> {
    private ControlServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ControlServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ControlServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Summarized function catalog with UI/RBAC metadata (for dashboard)
     * </pre>
     */
    public io.github.cuihairu.croupier.server.v1.ListFunctionsSummaryResponse listFunctionsSummary(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListFunctionsSummaryMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Agent registration with server
     * </pre>
     */
    public io.github.cuihairu.croupier.server.v1.RegisterResponse register(io.github.cuihairu.croupier.server.v1.RegisterRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Agent heartbeat
     * </pre>
     */
    public io.github.cuihairu.croupier.server.v1.HeartbeatResponse heartbeat(io.github.cuihairu.croupier.server.v1.HeartbeatRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHeartbeatMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Provider capabilities registration
     * </pre>
     */
    public io.github.cuihairu.croupier.server.v1.RegisterCapabilitiesResponse registerCapabilities(io.github.cuihairu.croupier.server.v1.RegisterCapabilitiesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterCapabilitiesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * GetAgentSystemInfo retrieves system info for a specific agent.
     * </pre>
     */
    public io.github.cuihairu.croupier.ops.v1.SystemInfo getAgentSystemInfo(io.github.cuihairu.croupier.server.v1.GetAgentSystemInfoRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAgentSystemInfoMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ListAgentProcesses lists processes on a specific agent.
     * </pre>
     */
    public io.github.cuihairu.croupier.ops.v1.ListProcessesResponse listAgentProcesses(io.github.cuihairu.croupier.server.v1.ListAgentProcessesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListAgentProcessesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * QueryMetrics queries stored metrics from agents.
     * </pre>
     */
    public io.github.cuihairu.croupier.server.v1.QueryMetricsResponse queryMetrics(io.github.cuihairu.croupier.server.v1.QueryMetricsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQueryMetricsMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ControlService.
   * <pre>
   * Server Control Service - Internal interface for agent registration and management
   * </pre>
   */
  public static final class ControlServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<ControlServiceFutureStub> {
    private ControlServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ControlServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ControlServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Summarized function catalog with UI/RBAC metadata (for dashboard)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.cuihairu.croupier.server.v1.ListFunctionsSummaryResponse> listFunctionsSummary(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListFunctionsSummaryMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Agent registration with server
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.cuihairu.croupier.server.v1.RegisterResponse> register(
        io.github.cuihairu.croupier.server.v1.RegisterRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Agent heartbeat
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.cuihairu.croupier.server.v1.HeartbeatResponse> heartbeat(
        io.github.cuihairu.croupier.server.v1.HeartbeatRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHeartbeatMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Provider capabilities registration
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.cuihairu.croupier.server.v1.RegisterCapabilitiesResponse> registerCapabilities(
        io.github.cuihairu.croupier.server.v1.RegisterCapabilitiesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterCapabilitiesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * GetAgentSystemInfo retrieves system info for a specific agent.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.cuihairu.croupier.ops.v1.SystemInfo> getAgentSystemInfo(
        io.github.cuihairu.croupier.server.v1.GetAgentSystemInfoRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAgentSystemInfoMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * ListAgentProcesses lists processes on a specific agent.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.cuihairu.croupier.ops.v1.ListProcessesResponse> listAgentProcesses(
        io.github.cuihairu.croupier.server.v1.ListAgentProcessesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListAgentProcessesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * QueryMetrics queries stored metrics from agents.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.cuihairu.croupier.server.v1.QueryMetricsResponse> queryMetrics(
        io.github.cuihairu.croupier.server.v1.QueryMetricsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getQueryMetricsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LIST_FUNCTIONS_SUMMARY = 0;
  private static final int METHODID_REGISTER = 1;
  private static final int METHODID_HEARTBEAT = 2;
  private static final int METHODID_REGISTER_CAPABILITIES = 3;
  private static final int METHODID_GET_AGENT_SYSTEM_INFO = 4;
  private static final int METHODID_LIST_AGENT_PROCESSES = 5;
  private static final int METHODID_QUERY_METRICS = 6;

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
        case METHODID_LIST_FUNCTIONS_SUMMARY:
          serviceImpl.listFunctionsSummary((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.server.v1.ListFunctionsSummaryResponse>) responseObserver);
          break;
        case METHODID_REGISTER:
          serviceImpl.register((io.github.cuihairu.croupier.server.v1.RegisterRequest) request,
              (io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.server.v1.RegisterResponse>) responseObserver);
          break;
        case METHODID_HEARTBEAT:
          serviceImpl.heartbeat((io.github.cuihairu.croupier.server.v1.HeartbeatRequest) request,
              (io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.server.v1.HeartbeatResponse>) responseObserver);
          break;
        case METHODID_REGISTER_CAPABILITIES:
          serviceImpl.registerCapabilities((io.github.cuihairu.croupier.server.v1.RegisterCapabilitiesRequest) request,
              (io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.server.v1.RegisterCapabilitiesResponse>) responseObserver);
          break;
        case METHODID_GET_AGENT_SYSTEM_INFO:
          serviceImpl.getAgentSystemInfo((io.github.cuihairu.croupier.server.v1.GetAgentSystemInfoRequest) request,
              (io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.ops.v1.SystemInfo>) responseObserver);
          break;
        case METHODID_LIST_AGENT_PROCESSES:
          serviceImpl.listAgentProcesses((io.github.cuihairu.croupier.server.v1.ListAgentProcessesRequest) request,
              (io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.ops.v1.ListProcessesResponse>) responseObserver);
          break;
        case METHODID_QUERY_METRICS:
          serviceImpl.queryMetrics((io.github.cuihairu.croupier.server.v1.QueryMetricsRequest) request,
              (io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.server.v1.QueryMetricsResponse>) responseObserver);
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
          getListFunctionsSummaryMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.protobuf.Empty,
              io.github.cuihairu.croupier.server.v1.ListFunctionsSummaryResponse>(
                service, METHODID_LIST_FUNCTIONS_SUMMARY)))
        .addMethod(
          getRegisterMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.cuihairu.croupier.server.v1.RegisterRequest,
              io.github.cuihairu.croupier.server.v1.RegisterResponse>(
                service, METHODID_REGISTER)))
        .addMethod(
          getHeartbeatMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.cuihairu.croupier.server.v1.HeartbeatRequest,
              io.github.cuihairu.croupier.server.v1.HeartbeatResponse>(
                service, METHODID_HEARTBEAT)))
        .addMethod(
          getRegisterCapabilitiesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.cuihairu.croupier.server.v1.RegisterCapabilitiesRequest,
              io.github.cuihairu.croupier.server.v1.RegisterCapabilitiesResponse>(
                service, METHODID_REGISTER_CAPABILITIES)))
        .addMethod(
          getGetAgentSystemInfoMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.cuihairu.croupier.server.v1.GetAgentSystemInfoRequest,
              io.github.cuihairu.croupier.ops.v1.SystemInfo>(
                service, METHODID_GET_AGENT_SYSTEM_INFO)))
        .addMethod(
          getListAgentProcessesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.cuihairu.croupier.server.v1.ListAgentProcessesRequest,
              io.github.cuihairu.croupier.ops.v1.ListProcessesResponse>(
                service, METHODID_LIST_AGENT_PROCESSES)))
        .addMethod(
          getQueryMetricsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.cuihairu.croupier.server.v1.QueryMetricsRequest,
              io.github.cuihairu.croupier.server.v1.QueryMetricsResponse>(
                service, METHODID_QUERY_METRICS)))
        .build();
  }

  private static abstract class ControlServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ControlServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.github.cuihairu.croupier.server.v1.ServerControl.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ControlService");
    }
  }

  private static final class ControlServiceFileDescriptorSupplier
      extends ControlServiceBaseDescriptorSupplier {
    ControlServiceFileDescriptorSupplier() {}
  }

  private static final class ControlServiceMethodDescriptorSupplier
      extends ControlServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ControlServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (ControlServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ControlServiceFileDescriptorSupplier())
              .addMethod(getListFunctionsSummaryMethod())
              .addMethod(getRegisterMethod())
              .addMethod(getHeartbeatMethod())
              .addMethod(getRegisterCapabilitiesMethod())
              .addMethod(getGetAgentSystemInfoMethod())
              .addMethod(getListAgentProcessesMethod())
              .addMethod(getQueryMetricsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
