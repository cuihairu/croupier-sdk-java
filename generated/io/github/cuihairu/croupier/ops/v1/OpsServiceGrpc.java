package io.github.cuihairu.croupier.ops.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * OpsService provides server operations and monitoring capabilities.
 * WARNING: This service can execute privileged operations. Enable with caution.
 * See operations documentation for security implications.
 * </pre>
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class OpsServiceGrpc {

  private OpsServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "croupier.ops.v1.OpsService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.github.cuihairu.croupier.ops.v1.MetricsReport,
      com.google.protobuf.Empty> getReportMetricsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReportMetrics",
      requestType = io.github.cuihairu.croupier.ops.v1.MetricsReport.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.cuihairu.croupier.ops.v1.MetricsReport,
      com.google.protobuf.Empty> getReportMetricsMethod() {
    io.grpc.MethodDescriptor<io.github.cuihairu.croupier.ops.v1.MetricsReport, com.google.protobuf.Empty> getReportMetricsMethod;
    if ((getReportMetricsMethod = OpsServiceGrpc.getReportMetricsMethod) == null) {
      synchronized (OpsServiceGrpc.class) {
        if ((getReportMetricsMethod = OpsServiceGrpc.getReportMetricsMethod) == null) {
          OpsServiceGrpc.getReportMetricsMethod = getReportMetricsMethod =
              io.grpc.MethodDescriptor.<io.github.cuihairu.croupier.ops.v1.MetricsReport, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReportMetrics"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.ops.v1.MetricsReport.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new OpsServiceMethodDescriptorSupplier("ReportMetrics"))
              .build();
        }
      }
    }
    return getReportMetricsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.cuihairu.croupier.ops.v1.MetricsReport,
      com.google.protobuf.Empty> getStreamMetricsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StreamMetrics",
      requestType = io.github.cuihairu.croupier.ops.v1.MetricsReport.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<io.github.cuihairu.croupier.ops.v1.MetricsReport,
      com.google.protobuf.Empty> getStreamMetricsMethod() {
    io.grpc.MethodDescriptor<io.github.cuihairu.croupier.ops.v1.MetricsReport, com.google.protobuf.Empty> getStreamMetricsMethod;
    if ((getStreamMetricsMethod = OpsServiceGrpc.getStreamMetricsMethod) == null) {
      synchronized (OpsServiceGrpc.class) {
        if ((getStreamMetricsMethod = OpsServiceGrpc.getStreamMetricsMethod) == null) {
          OpsServiceGrpc.getStreamMetricsMethod = getStreamMetricsMethod =
              io.grpc.MethodDescriptor.<io.github.cuihairu.croupier.ops.v1.MetricsReport, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StreamMetrics"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.ops.v1.MetricsReport.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new OpsServiceMethodDescriptorSupplier("StreamMetrics"))
              .build();
        }
      }
    }
    return getStreamMetricsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      io.github.cuihairu.croupier.ops.v1.SystemInfo> getGetSystemInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetSystemInfo",
      requestType = com.google.protobuf.Empty.class,
      responseType = io.github.cuihairu.croupier.ops.v1.SystemInfo.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      io.github.cuihairu.croupier.ops.v1.SystemInfo> getGetSystemInfoMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, io.github.cuihairu.croupier.ops.v1.SystemInfo> getGetSystemInfoMethod;
    if ((getGetSystemInfoMethod = OpsServiceGrpc.getGetSystemInfoMethod) == null) {
      synchronized (OpsServiceGrpc.class) {
        if ((getGetSystemInfoMethod = OpsServiceGrpc.getGetSystemInfoMethod) == null) {
          OpsServiceGrpc.getGetSystemInfoMethod = getGetSystemInfoMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, io.github.cuihairu.croupier.ops.v1.SystemInfo>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetSystemInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.ops.v1.SystemInfo.getDefaultInstance()))
              .setSchemaDescriptor(new OpsServiceMethodDescriptorSupplier("GetSystemInfo"))
              .build();
        }
      }
    }
    return getGetSystemInfoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.cuihairu.croupier.ops.v1.RestartProcessRequest,
      io.github.cuihairu.croupier.ops.v1.RestartProcessResponse> getRestartProcessMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RestartProcess",
      requestType = io.github.cuihairu.croupier.ops.v1.RestartProcessRequest.class,
      responseType = io.github.cuihairu.croupier.ops.v1.RestartProcessResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.cuihairu.croupier.ops.v1.RestartProcessRequest,
      io.github.cuihairu.croupier.ops.v1.RestartProcessResponse> getRestartProcessMethod() {
    io.grpc.MethodDescriptor<io.github.cuihairu.croupier.ops.v1.RestartProcessRequest, io.github.cuihairu.croupier.ops.v1.RestartProcessResponse> getRestartProcessMethod;
    if ((getRestartProcessMethod = OpsServiceGrpc.getRestartProcessMethod) == null) {
      synchronized (OpsServiceGrpc.class) {
        if ((getRestartProcessMethod = OpsServiceGrpc.getRestartProcessMethod) == null) {
          OpsServiceGrpc.getRestartProcessMethod = getRestartProcessMethod =
              io.grpc.MethodDescriptor.<io.github.cuihairu.croupier.ops.v1.RestartProcessRequest, io.github.cuihairu.croupier.ops.v1.RestartProcessResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RestartProcess"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.ops.v1.RestartProcessRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.ops.v1.RestartProcessResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OpsServiceMethodDescriptorSupplier("RestartProcess"))
              .build();
        }
      }
    }
    return getRestartProcessMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.cuihairu.croupier.ops.v1.StopProcessRequest,
      io.github.cuihairu.croupier.ops.v1.StopProcessResponse> getStopProcessMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StopProcess",
      requestType = io.github.cuihairu.croupier.ops.v1.StopProcessRequest.class,
      responseType = io.github.cuihairu.croupier.ops.v1.StopProcessResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.cuihairu.croupier.ops.v1.StopProcessRequest,
      io.github.cuihairu.croupier.ops.v1.StopProcessResponse> getStopProcessMethod() {
    io.grpc.MethodDescriptor<io.github.cuihairu.croupier.ops.v1.StopProcessRequest, io.github.cuihairu.croupier.ops.v1.StopProcessResponse> getStopProcessMethod;
    if ((getStopProcessMethod = OpsServiceGrpc.getStopProcessMethod) == null) {
      synchronized (OpsServiceGrpc.class) {
        if ((getStopProcessMethod = OpsServiceGrpc.getStopProcessMethod) == null) {
          OpsServiceGrpc.getStopProcessMethod = getStopProcessMethod =
              io.grpc.MethodDescriptor.<io.github.cuihairu.croupier.ops.v1.StopProcessRequest, io.github.cuihairu.croupier.ops.v1.StopProcessResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StopProcess"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.ops.v1.StopProcessRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.ops.v1.StopProcessResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OpsServiceMethodDescriptorSupplier("StopProcess"))
              .build();
        }
      }
    }
    return getStopProcessMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.cuihairu.croupier.ops.v1.StartProcessRequest,
      io.github.cuihairu.croupier.ops.v1.StartProcessResponse> getStartProcessMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StartProcess",
      requestType = io.github.cuihairu.croupier.ops.v1.StartProcessRequest.class,
      responseType = io.github.cuihairu.croupier.ops.v1.StartProcessResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.cuihairu.croupier.ops.v1.StartProcessRequest,
      io.github.cuihairu.croupier.ops.v1.StartProcessResponse> getStartProcessMethod() {
    io.grpc.MethodDescriptor<io.github.cuihairu.croupier.ops.v1.StartProcessRequest, io.github.cuihairu.croupier.ops.v1.StartProcessResponse> getStartProcessMethod;
    if ((getStartProcessMethod = OpsServiceGrpc.getStartProcessMethod) == null) {
      synchronized (OpsServiceGrpc.class) {
        if ((getStartProcessMethod = OpsServiceGrpc.getStartProcessMethod) == null) {
          OpsServiceGrpc.getStartProcessMethod = getStartProcessMethod =
              io.grpc.MethodDescriptor.<io.github.cuihairu.croupier.ops.v1.StartProcessRequest, io.github.cuihairu.croupier.ops.v1.StartProcessResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StartProcess"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.ops.v1.StartProcessRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.ops.v1.StartProcessResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OpsServiceMethodDescriptorSupplier("StartProcess"))
              .build();
        }
      }
    }
    return getStartProcessMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      io.github.cuihairu.croupier.ops.v1.ListProcessesResponse> getListProcessesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListProcesses",
      requestType = com.google.protobuf.Empty.class,
      responseType = io.github.cuihairu.croupier.ops.v1.ListProcessesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      io.github.cuihairu.croupier.ops.v1.ListProcessesResponse> getListProcessesMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, io.github.cuihairu.croupier.ops.v1.ListProcessesResponse> getListProcessesMethod;
    if ((getListProcessesMethod = OpsServiceGrpc.getListProcessesMethod) == null) {
      synchronized (OpsServiceGrpc.class) {
        if ((getListProcessesMethod = OpsServiceGrpc.getListProcessesMethod) == null) {
          OpsServiceGrpc.getListProcessesMethod = getListProcessesMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, io.github.cuihairu.croupier.ops.v1.ListProcessesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListProcesses"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.ops.v1.ListProcessesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OpsServiceMethodDescriptorSupplier("ListProcesses"))
              .build();
        }
      }
    }
    return getListProcessesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.cuihairu.croupier.ops.v1.ExecuteCommandRequest,
      io.github.cuihairu.croupier.ops.v1.ExecuteCommandResponse> getExecuteCommandMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ExecuteCommand",
      requestType = io.github.cuihairu.croupier.ops.v1.ExecuteCommandRequest.class,
      responseType = io.github.cuihairu.croupier.ops.v1.ExecuteCommandResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.cuihairu.croupier.ops.v1.ExecuteCommandRequest,
      io.github.cuihairu.croupier.ops.v1.ExecuteCommandResponse> getExecuteCommandMethod() {
    io.grpc.MethodDescriptor<io.github.cuihairu.croupier.ops.v1.ExecuteCommandRequest, io.github.cuihairu.croupier.ops.v1.ExecuteCommandResponse> getExecuteCommandMethod;
    if ((getExecuteCommandMethod = OpsServiceGrpc.getExecuteCommandMethod) == null) {
      synchronized (OpsServiceGrpc.class) {
        if ((getExecuteCommandMethod = OpsServiceGrpc.getExecuteCommandMethod) == null) {
          OpsServiceGrpc.getExecuteCommandMethod = getExecuteCommandMethod =
              io.grpc.MethodDescriptor.<io.github.cuihairu.croupier.ops.v1.ExecuteCommandRequest, io.github.cuihairu.croupier.ops.v1.ExecuteCommandResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ExecuteCommand"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.ops.v1.ExecuteCommandRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.cuihairu.croupier.ops.v1.ExecuteCommandResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OpsServiceMethodDescriptorSupplier("ExecuteCommand"))
              .build();
        }
      }
    }
    return getExecuteCommandMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static OpsServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OpsServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OpsServiceStub>() {
        @java.lang.Override
        public OpsServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OpsServiceStub(channel, callOptions);
        }
      };
    return OpsServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static OpsServiceBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OpsServiceBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OpsServiceBlockingV2Stub>() {
        @java.lang.Override
        public OpsServiceBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OpsServiceBlockingV2Stub(channel, callOptions);
        }
      };
    return OpsServiceBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static OpsServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OpsServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OpsServiceBlockingStub>() {
        @java.lang.Override
        public OpsServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OpsServiceBlockingStub(channel, callOptions);
        }
      };
    return OpsServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static OpsServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OpsServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OpsServiceFutureStub>() {
        @java.lang.Override
        public OpsServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OpsServiceFutureStub(channel, callOptions);
        }
      };
    return OpsServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * OpsService provides server operations and monitoring capabilities.
   * WARNING: This service can execute privileged operations. Enable with caution.
   * See operations documentation for security implications.
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * ReportMetrics reports system metrics from agent to server.
     * </pre>
     */
    default void reportMetrics(io.github.cuihairu.croupier.ops.v1.MetricsReport request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReportMetricsMethod(), responseObserver);
    }

    /**
     * <pre>
     * StreamMetrics establishes a streaming connection for continuous metrics reporting.
     * </pre>
     */
    default io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.ops.v1.MetricsReport> streamMetrics(
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getStreamMetricsMethod(), responseObserver);
    }

    /**
     * <pre>
     * GetSystemInfo returns detailed system information.
     * </pre>
     */
    default void getSystemInfo(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.ops.v1.SystemInfo> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetSystemInfoMethod(), responseObserver);
    }

    /**
     * <pre>
     * RestartProcess restarts a managed process.
     * Requires ops.allow_restart = true in agent config.
     * </pre>
     */
    default void restartProcess(io.github.cuihairu.croupier.ops.v1.RestartProcessRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.ops.v1.RestartProcessResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRestartProcessMethod(), responseObserver);
    }

    /**
     * <pre>
     * StopProcess stops a managed process.
     * Requires ops.allow_restart = true in agent config.
     * </pre>
     */
    default void stopProcess(io.github.cuihairu.croupier.ops.v1.StopProcessRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.ops.v1.StopProcessResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStopProcessMethod(), responseObserver);
    }

    /**
     * <pre>
     * StartProcess starts a managed process.
     * Requires ops.allow_restart = true in agent config.
     * </pre>
     */
    default void startProcess(io.github.cuihairu.croupier.ops.v1.StartProcessRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.ops.v1.StartProcessResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStartProcessMethod(), responseObserver);
    }

    /**
     * <pre>
     * ListProcesses lists all managed processes.
     * </pre>
     */
    default void listProcesses(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.ops.v1.ListProcessesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListProcessesMethod(), responseObserver);
    }

    /**
     * <pre>
     * ExecuteCommand executes a shell command.
     * Requires ops.allow_exec = true in agent config.
     * WARNING: This is a high-risk operation. Use with extreme caution.
     * </pre>
     */
    default void executeCommand(io.github.cuihairu.croupier.ops.v1.ExecuteCommandRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.ops.v1.ExecuteCommandResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getExecuteCommandMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service OpsService.
   * <pre>
   * OpsService provides server operations and monitoring capabilities.
   * WARNING: This service can execute privileged operations. Enable with caution.
   * See operations documentation for security implications.
   * </pre>
   */
  public static abstract class OpsServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return OpsServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service OpsService.
   * <pre>
   * OpsService provides server operations and monitoring capabilities.
   * WARNING: This service can execute privileged operations. Enable with caution.
   * See operations documentation for security implications.
   * </pre>
   */
  public static final class OpsServiceStub
      extends io.grpc.stub.AbstractAsyncStub<OpsServiceStub> {
    private OpsServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OpsServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OpsServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * ReportMetrics reports system metrics from agent to server.
     * </pre>
     */
    public void reportMetrics(io.github.cuihairu.croupier.ops.v1.MetricsReport request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReportMetricsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * StreamMetrics establishes a streaming connection for continuous metrics reporting.
     * </pre>
     */
    public io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.ops.v1.MetricsReport> streamMetrics(
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getStreamMetricsMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * GetSystemInfo returns detailed system information.
     * </pre>
     */
    public void getSystemInfo(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.ops.v1.SystemInfo> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetSystemInfoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * RestartProcess restarts a managed process.
     * Requires ops.allow_restart = true in agent config.
     * </pre>
     */
    public void restartProcess(io.github.cuihairu.croupier.ops.v1.RestartProcessRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.ops.v1.RestartProcessResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRestartProcessMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * StopProcess stops a managed process.
     * Requires ops.allow_restart = true in agent config.
     * </pre>
     */
    public void stopProcess(io.github.cuihairu.croupier.ops.v1.StopProcessRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.ops.v1.StopProcessResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getStopProcessMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * StartProcess starts a managed process.
     * Requires ops.allow_restart = true in agent config.
     * </pre>
     */
    public void startProcess(io.github.cuihairu.croupier.ops.v1.StartProcessRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.ops.v1.StartProcessResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getStartProcessMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * ListProcesses lists all managed processes.
     * </pre>
     */
    public void listProcesses(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.ops.v1.ListProcessesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListProcessesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * ExecuteCommand executes a shell command.
     * Requires ops.allow_exec = true in agent config.
     * WARNING: This is a high-risk operation. Use with extreme caution.
     * </pre>
     */
    public void executeCommand(io.github.cuihairu.croupier.ops.v1.ExecuteCommandRequest request,
        io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.ops.v1.ExecuteCommandResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getExecuteCommandMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service OpsService.
   * <pre>
   * OpsService provides server operations and monitoring capabilities.
   * WARNING: This service can execute privileged operations. Enable with caution.
   * See operations documentation for security implications.
   * </pre>
   */
  public static final class OpsServiceBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<OpsServiceBlockingV2Stub> {
    private OpsServiceBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OpsServiceBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OpsServiceBlockingV2Stub(channel, callOptions);
    }

    /**
     * <pre>
     * ReportMetrics reports system metrics from agent to server.
     * </pre>
     */
    public com.google.protobuf.Empty reportMetrics(io.github.cuihairu.croupier.ops.v1.MetricsReport request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getReportMetricsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * StreamMetrics establishes a streaming connection for continuous metrics reporting.
     * </pre>
     */
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/10918")
    public io.grpc.stub.BlockingClientCall<io.github.cuihairu.croupier.ops.v1.MetricsReport, com.google.protobuf.Empty>
        streamMetrics() {
      return io.grpc.stub.ClientCalls.blockingClientStreamingCall(
          getChannel(), getStreamMetricsMethod(), getCallOptions());
    }

    /**
     * <pre>
     * GetSystemInfo returns detailed system information.
     * </pre>
     */
    public io.github.cuihairu.croupier.ops.v1.SystemInfo getSystemInfo(com.google.protobuf.Empty request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getGetSystemInfoMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * RestartProcess restarts a managed process.
     * Requires ops.allow_restart = true in agent config.
     * </pre>
     */
    public io.github.cuihairu.croupier.ops.v1.RestartProcessResponse restartProcess(io.github.cuihairu.croupier.ops.v1.RestartProcessRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getRestartProcessMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * StopProcess stops a managed process.
     * Requires ops.allow_restart = true in agent config.
     * </pre>
     */
    public io.github.cuihairu.croupier.ops.v1.StopProcessResponse stopProcess(io.github.cuihairu.croupier.ops.v1.StopProcessRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getStopProcessMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * StartProcess starts a managed process.
     * Requires ops.allow_restart = true in agent config.
     * </pre>
     */
    public io.github.cuihairu.croupier.ops.v1.StartProcessResponse startProcess(io.github.cuihairu.croupier.ops.v1.StartProcessRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getStartProcessMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ListProcesses lists all managed processes.
     * </pre>
     */
    public io.github.cuihairu.croupier.ops.v1.ListProcessesResponse listProcesses(com.google.protobuf.Empty request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getListProcessesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ExecuteCommand executes a shell command.
     * Requires ops.allow_exec = true in agent config.
     * WARNING: This is a high-risk operation. Use with extreme caution.
     * </pre>
     */
    public io.github.cuihairu.croupier.ops.v1.ExecuteCommandResponse executeCommand(io.github.cuihairu.croupier.ops.v1.ExecuteCommandRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getExecuteCommandMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service OpsService.
   * <pre>
   * OpsService provides server operations and monitoring capabilities.
   * WARNING: This service can execute privileged operations. Enable with caution.
   * See operations documentation for security implications.
   * </pre>
   */
  public static final class OpsServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<OpsServiceBlockingStub> {
    private OpsServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OpsServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OpsServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * ReportMetrics reports system metrics from agent to server.
     * </pre>
     */
    public com.google.protobuf.Empty reportMetrics(io.github.cuihairu.croupier.ops.v1.MetricsReport request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReportMetricsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * GetSystemInfo returns detailed system information.
     * </pre>
     */
    public io.github.cuihairu.croupier.ops.v1.SystemInfo getSystemInfo(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetSystemInfoMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * RestartProcess restarts a managed process.
     * Requires ops.allow_restart = true in agent config.
     * </pre>
     */
    public io.github.cuihairu.croupier.ops.v1.RestartProcessResponse restartProcess(io.github.cuihairu.croupier.ops.v1.RestartProcessRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRestartProcessMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * StopProcess stops a managed process.
     * Requires ops.allow_restart = true in agent config.
     * </pre>
     */
    public io.github.cuihairu.croupier.ops.v1.StopProcessResponse stopProcess(io.github.cuihairu.croupier.ops.v1.StopProcessRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getStopProcessMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * StartProcess starts a managed process.
     * Requires ops.allow_restart = true in agent config.
     * </pre>
     */
    public io.github.cuihairu.croupier.ops.v1.StartProcessResponse startProcess(io.github.cuihairu.croupier.ops.v1.StartProcessRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getStartProcessMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ListProcesses lists all managed processes.
     * </pre>
     */
    public io.github.cuihairu.croupier.ops.v1.ListProcessesResponse listProcesses(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListProcessesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ExecuteCommand executes a shell command.
     * Requires ops.allow_exec = true in agent config.
     * WARNING: This is a high-risk operation. Use with extreme caution.
     * </pre>
     */
    public io.github.cuihairu.croupier.ops.v1.ExecuteCommandResponse executeCommand(io.github.cuihairu.croupier.ops.v1.ExecuteCommandRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getExecuteCommandMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service OpsService.
   * <pre>
   * OpsService provides server operations and monitoring capabilities.
   * WARNING: This service can execute privileged operations. Enable with caution.
   * See operations documentation for security implications.
   * </pre>
   */
  public static final class OpsServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<OpsServiceFutureStub> {
    private OpsServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OpsServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OpsServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * ReportMetrics reports system metrics from agent to server.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> reportMetrics(
        io.github.cuihairu.croupier.ops.v1.MetricsReport request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReportMetricsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * GetSystemInfo returns detailed system information.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.cuihairu.croupier.ops.v1.SystemInfo> getSystemInfo(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetSystemInfoMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * RestartProcess restarts a managed process.
     * Requires ops.allow_restart = true in agent config.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.cuihairu.croupier.ops.v1.RestartProcessResponse> restartProcess(
        io.github.cuihairu.croupier.ops.v1.RestartProcessRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRestartProcessMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * StopProcess stops a managed process.
     * Requires ops.allow_restart = true in agent config.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.cuihairu.croupier.ops.v1.StopProcessResponse> stopProcess(
        io.github.cuihairu.croupier.ops.v1.StopProcessRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getStopProcessMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * StartProcess starts a managed process.
     * Requires ops.allow_restart = true in agent config.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.cuihairu.croupier.ops.v1.StartProcessResponse> startProcess(
        io.github.cuihairu.croupier.ops.v1.StartProcessRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getStartProcessMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * ListProcesses lists all managed processes.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.cuihairu.croupier.ops.v1.ListProcessesResponse> listProcesses(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListProcessesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * ExecuteCommand executes a shell command.
     * Requires ops.allow_exec = true in agent config.
     * WARNING: This is a high-risk operation. Use with extreme caution.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.cuihairu.croupier.ops.v1.ExecuteCommandResponse> executeCommand(
        io.github.cuihairu.croupier.ops.v1.ExecuteCommandRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getExecuteCommandMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REPORT_METRICS = 0;
  private static final int METHODID_GET_SYSTEM_INFO = 1;
  private static final int METHODID_RESTART_PROCESS = 2;
  private static final int METHODID_STOP_PROCESS = 3;
  private static final int METHODID_START_PROCESS = 4;
  private static final int METHODID_LIST_PROCESSES = 5;
  private static final int METHODID_EXECUTE_COMMAND = 6;
  private static final int METHODID_STREAM_METRICS = 7;

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
        case METHODID_REPORT_METRICS:
          serviceImpl.reportMetrics((io.github.cuihairu.croupier.ops.v1.MetricsReport) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_GET_SYSTEM_INFO:
          serviceImpl.getSystemInfo((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.ops.v1.SystemInfo>) responseObserver);
          break;
        case METHODID_RESTART_PROCESS:
          serviceImpl.restartProcess((io.github.cuihairu.croupier.ops.v1.RestartProcessRequest) request,
              (io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.ops.v1.RestartProcessResponse>) responseObserver);
          break;
        case METHODID_STOP_PROCESS:
          serviceImpl.stopProcess((io.github.cuihairu.croupier.ops.v1.StopProcessRequest) request,
              (io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.ops.v1.StopProcessResponse>) responseObserver);
          break;
        case METHODID_START_PROCESS:
          serviceImpl.startProcess((io.github.cuihairu.croupier.ops.v1.StartProcessRequest) request,
              (io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.ops.v1.StartProcessResponse>) responseObserver);
          break;
        case METHODID_LIST_PROCESSES:
          serviceImpl.listProcesses((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.ops.v1.ListProcessesResponse>) responseObserver);
          break;
        case METHODID_EXECUTE_COMMAND:
          serviceImpl.executeCommand((io.github.cuihairu.croupier.ops.v1.ExecuteCommandRequest) request,
              (io.grpc.stub.StreamObserver<io.github.cuihairu.croupier.ops.v1.ExecuteCommandResponse>) responseObserver);
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
        case METHODID_STREAM_METRICS:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.streamMetrics(
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getReportMetricsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.cuihairu.croupier.ops.v1.MetricsReport,
              com.google.protobuf.Empty>(
                service, METHODID_REPORT_METRICS)))
        .addMethod(
          getStreamMetricsMethod(),
          io.grpc.stub.ServerCalls.asyncClientStreamingCall(
            new MethodHandlers<
              io.github.cuihairu.croupier.ops.v1.MetricsReport,
              com.google.protobuf.Empty>(
                service, METHODID_STREAM_METRICS)))
        .addMethod(
          getGetSystemInfoMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.protobuf.Empty,
              io.github.cuihairu.croupier.ops.v1.SystemInfo>(
                service, METHODID_GET_SYSTEM_INFO)))
        .addMethod(
          getRestartProcessMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.cuihairu.croupier.ops.v1.RestartProcessRequest,
              io.github.cuihairu.croupier.ops.v1.RestartProcessResponse>(
                service, METHODID_RESTART_PROCESS)))
        .addMethod(
          getStopProcessMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.cuihairu.croupier.ops.v1.StopProcessRequest,
              io.github.cuihairu.croupier.ops.v1.StopProcessResponse>(
                service, METHODID_STOP_PROCESS)))
        .addMethod(
          getStartProcessMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.cuihairu.croupier.ops.v1.StartProcessRequest,
              io.github.cuihairu.croupier.ops.v1.StartProcessResponse>(
                service, METHODID_START_PROCESS)))
        .addMethod(
          getListProcessesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.protobuf.Empty,
              io.github.cuihairu.croupier.ops.v1.ListProcessesResponse>(
                service, METHODID_LIST_PROCESSES)))
        .addMethod(
          getExecuteCommandMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.cuihairu.croupier.ops.v1.ExecuteCommandRequest,
              io.github.cuihairu.croupier.ops.v1.ExecuteCommandResponse>(
                service, METHODID_EXECUTE_COMMAND)))
        .build();
  }

  private static abstract class OpsServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    OpsServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.github.cuihairu.croupier.ops.v1.Ops.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("OpsService");
    }
  }

  private static final class OpsServiceFileDescriptorSupplier
      extends OpsServiceBaseDescriptorSupplier {
    OpsServiceFileDescriptorSupplier() {}
  }

  private static final class OpsServiceMethodDescriptorSupplier
      extends OpsServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    OpsServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (OpsServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new OpsServiceFileDescriptorSupplier())
              .addMethod(getReportMetricsMethod())
              .addMethod(getStreamMetricsMethod())
              .addMethod(getGetSystemInfoMethod())
              .addMethod(getRestartProcessMethod())
              .addMethod(getStopProcessMethod())
              .addMethod(getStartProcessMethod())
              .addMethod(getListProcessesMethod())
              .addMethod(getExecuteCommandMethod())
              .build();
        }
      }
    }
    return result;
  }
}
