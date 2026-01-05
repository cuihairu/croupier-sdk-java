# 入门指南

欢迎使用 Croupier Java SDK！

## 系统要求

- Java 17+
- Maven 或 Gradle

## 安装

### Maven

```xml
<dependency>
    <groupId>com.github.cuihairu</groupId>
    <artifactId>croupier-sdk-java</artifactId>
    <version>0.1.0</version>
</dependency>
```

### Gradle

```groovy
implementation 'com.github.cuihairu:croupier-sdk-java:0.1.0'
```

## 快速开始

```java
import com.github.cuihairu.croupier.sdk.*;

public class Example {
    public static void main(String[] args) {
        ClientConfig config = ClientConfig.builder()
            .gameId("demo-game")
            .env("development")
            .agentAddr("localhost:19090")
            .insecure(true)
            .build();

        CroupierClient client = new CroupierClient(config);

        FunctionDescriptor desc = FunctionDescriptor.builder()
            .id("hello.world")
            .version("0.1.0")
            .build();

        client.registerFunction(desc, (ctx, payload) -> {
            return "{\"message\":\"Hello from Java!\"}";
        });

        client.connect();
        client.serve();
    }
}
```
