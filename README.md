# Croupier Java SDK

[![Nightly Build](https://github.com/cuihairu/croupier-sdk-java/actions/workflows/nightly.yml/badge.svg)](https://github.com/cuihairu/croupier-sdk-java/actions/workflows/nightly.yml)
[![CI Build](https://github.com/cuihairu/croupier-sdk-java/actions/workflows/ci.yml/badge.svg)](https://github.com/cuihairu/croupier-sdk-java/actions/workflows/ci.yml)

Production-ready client for registering and executing Croupier “functions” (remote procedures) from JVM game services.

> 本 SDK 与 [Croupier 主项目](https://github.com/cuihairu/croupier) 使用同一套 proto/控制面协议，可直接接入其 Server / Agent。

## Overview

The SDK speaks the same gRPC/Protobuf contracts as the [Croupier platform](https://github.com/cuihairu/croupier).  
Key principles:

- **Single source of truth** – proto definitions live in the `proto/` submodule (shared with the main platform). Generated Java stubs are checked into `generated/` so consumers are not forced to run protoc.
- **Gradle-first pipeline** – the repo ships with a wrapper and protobuf plugin configuration; `./gradlew clean build` is all you need locally or in CI.
- **Ergonomic APIs** – descriptors, handlers, config builders, and async helpers wrap the raw gRPC channels.

## Highlights

- **Proto-aligned types**：`FunctionDescriptor`、`LocalFunctionDescriptor` 与官方 IDL 100% 对齐。
- **多租户 / 环境隔离**：内置 `gameId`、`env`、`serviceId` 维度。
- **函数注册到执行链路**：完成注册、心跳、执行、返回值、错误处理。
- **异步能力**：基于 `CompletableFuture`，便于与现有任务系统整合。
- **Provider manifest 上传**：控制面地址可用时，自动发布能力声明，便于 Dashboard 渲染。
- **Gradle + Buf 生态**：CI 自动拉取 proto、生成代码、执行测试并发布产物。

## Requirements

- JDK 11 或更高版本即可编译 & 运行（Gradle Wrapper 已锁定 8.x，默认编译到 `options.release = 11`）。
- Gradle Wrapper 已随仓库提供（无需全局安装）。

## Quick Start

### Install

Published coordinates (replace version tag accordingly):

```xml
<dependency>
    <groupId>com.croupier</groupId>
    <artifactId>croupier-sdk-java</artifactId>
    <version>0.1.0</version>
</dependency>
```

### Basic Usage

```java
import com.croupier.sdk.*;

public class GameServer {
    public static void main(String[] args) throws Exception {
        // Create client configuration
        ClientConfig config = new ClientConfig("my-game", "my-service");
        config.setAgentAddr("localhost:19090");
        config.setControlAddr("localhost:18080"); // Control plane for manifest upload
        config.setEnv("development");
        config.setInsecure(true); // For development
        config.setProviderLang("java");
        config.setProviderSdk("croupier-java-sdk");

        // Create client
        CroupierClient client = CroupierSDK.createClient(config);

        // Register a function
        FunctionDescriptor desc = CroupierSDK.functionDescriptor("player.ban", "1.0.0")
                .category("moderation")
                .risk("high")
                .entity("player")
                .operation("update")
                .build();

        FunctionHandler handler = (context, payload) -> {
            // Handle the function call
            return "{\"status\":\"success\"}";
        };

        client.registerFunction(desc, handler);

        // Start serving
        client.serve(); // Blocks until stopped
    }
}
```

### Async Usage

```java
// Connect and serve asynchronously
client.connect()
    .thenCompose(v -> client.serveAsync())
    .thenRun(() -> System.out.println("Service started"))
    .exceptionally(throwable -> {
        System.err.println("Failed: " + throwable.getMessage());
        return null;
    });
```

## Data Types

### FunctionDescriptor

Aligned with `control.proto`:

```java
FunctionDescriptor descriptor = CroupierSDK.functionDescriptor("player.ban", "1.0.0")
        .category("moderation")     // grouping category
        .risk("high")              // "low"|"medium"|"high"
        .entity("player")          // entity type
        .operation("update")       // "create"|"read"|"update"|"delete"
        .enabled(true)             // whether enabled
        .build();
```

### LocalFunctionDescriptor

Aligned with `agent/local/v1/local.proto`:

```java
LocalFunctionDescriptor localDesc = new LocalFunctionDescriptor("player.ban", "1.0.0");
```

### FunctionHandler

Functional interface for implementing game functions:

```java
FunctionHandler handler = (context, payload) -> {
    // context: execution context (JSON string)
    // payload: function payload (JSON string)
    // return: result (JSON string)
    return "{\"status\":\"success\"}";
};
```

## Configuration

### ClientConfig

```java
ClientConfig config = new ClientConfig();
config.setAgentAddr("localhost:19090");     // Agent address
config.setGameId("my-game");                // Game identifier
config.setEnv("development");               // Environment
config.setServiceId("my-service");          // Service identifier
config.setServiceVersion("1.0.0");         // Service version
config.setLocalListen(":0");                // Local server (auto-port)
config.setControlAddr("localhost:18080");   // Optional control-plane endpoint for manifests
config.setTimeoutSeconds(30);              // Connection timeout
config.setInsecure(true);                   // Use insecure gRPC
config.setProviderLang("java");             // Provider metadata
config.setProviderSdk("croupier-java-sdk");

// TLS settings (when not insecure)
config.setCaFile("/path/to/ca.pem");
config.setCertFile("/path/to/cert.pem");
config.setKeyFile("/path/to/key.pem");
```

## API Reference

### CroupierClient Interface

```java
public interface CroupierClient {
    // Function registration
    void registerFunction(FunctionDescriptor descriptor, FunctionHandler handler);

    // Connection management
    CompletableFuture<Void> connect();

    // Service operations
    void serve();                           // Blocks until stopped
    CompletableFuture<Void> serveAsync();  // Non-blocking

    // Lifecycle
    void stop();
    void close();

    // Status
    boolean isConnected();
    boolean isServing();
    String getLocalAddress();
}
```

### Factory Methods

```java
// Simple creation
CroupierClient client = CroupierSDK.createClient("game-id", "service-id");

// With agent address
CroupierClient client = CroupierSDK.createClient("game-id", "service-id", "localhost:19090");

// With full configuration
CroupierClient client = CroupierSDK.createClient(config);
```

## Proto & Build Pipeline

- `proto/`：Git submodule 指向 [`cuihairu/croupier-proto`](https://github.com/cuihairu/croupier-proto)，包含所有 `.proto`。
- `generated/`：已经提交的 `.java` gRPC Stubs，方便依赖方直接使用；当 proto 更新时由自动化流程重建并提交。
- `./gradlew`：内置 Gradle Wrapper + `com.google.protobuf` 插件，负责读取 `proto/` 并生成临时文件到 `build/generated/source`，然后与 `src/main/java` 合并编译。
- CI (`.github/workflows/ci.yml` & `nightly.yml`) 会在 JDK 11/17/21 上运行 `./gradlew --no-daemon clean build`，同时上传构建产物。

### 本地开发与测试

```bash
# 全量构建（编译 + 测试 + jar）
./gradlew --no-daemon clean build

# 仅运行单元测试
./gradlew --no-daemon test

# 查看生成的 gRPC 代码（可选）
ls build/generated/source/proto/main/java
```

> 提交 PR 前建议运行 `./gradlew --no-daemon clean build -x test`（如需跳测）以及 `./gradlew test` 分离定位失败案例。

## Architecture

```
Game Server → Java SDK → Agent → Croupier Server
```

The SDK implements a two-layer registration system:
1. **SDK → Agent**: Uses `LocalControlService` (from `local.proto`)
2. **Agent → Server**: Uses `ControlService` (from `control.proto`)

## Error Handling

The SDK provides comprehensive error handling:

```java
try {
    client.registerFunction(descriptor, handler);
    client.serve();
} catch (CroupierException e) {
    System.err.println("Croupier error: " + e.getMessage());
    e.printStackTrace();
} catch (Exception e) {
    System.err.println("Unexpected error: " + e.getMessage());
}
```

Async error handling:

```java
client.connect()
    .exceptionally(throwable -> {
        if (throwable.getCause() instanceof CroupierException) {
            System.err.println("Croupier error: " + throwable.getCause().getMessage());
        }
        return null;
    });
```

## Examples

`examples/` 提供可独立构建的 Demo（目前使用 Maven，保持与多数游戏项目一致）。

- `examples/basic`：最小化示例，展示函数注册与伪造执行。
- `examples/comprehensive`：更完整的配置示例，包括控制面回传、错误处理等。

运行综合示例：

```bash
cd examples/comprehensive
mvn compile exec:java -Dexec.mainClass="com.croupier.sdk.examples.ComprehensiveExample"
```

## Development

### Building

```bash
# Local development / CI
./gradlew --no-daemon clean build

# Run tests standalone
./gradlew --no-daemon test

# Package jar (already part of build, but can be explicit)
./gradlew --no-daemon jar
```

### Project Structure

```
proto/                    # 子模块：官方 API/SDK proto
generated/                # 已提交的 gRPC stubs（同步自 proto）
src/
├── main/java/com/croupier/sdk/
│   ├── CroupierSDK.java
│   ├── CroupierClient*.java
│   ├── descriptors / handlers / config
│   └── scripts/ProtoGenerator.java   # 兼容旧流水线的下载脚本
├── main/resources/
└── test/java/

examples/
├── basic/
└── comprehensive/
```

## Contributing

1. Ensure all types align with proto definitions
2. Add tests for new functionality
3. Update documentation for API changes
4. Test both local and CI build modes
5. Follow Java coding conventions

## License

See LICENSE file for details.
