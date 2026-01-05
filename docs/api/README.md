# API 参考

## ClientConfig

```java
ClientConfig config = ClientConfig.builder()
    .agentAddr("localhost:19090")
    .gameId("my-game")
    .env("development")
    .insecure(true)
    .build();
```

## CroupierClient

```java
CroupierClient client = new CroupierClient(config);
client.connect();
client.serve();
client.close();
```

## FunctionDescriptor

```java
FunctionDescriptor desc = FunctionDescriptor.builder()
    .id("function.id")
    .version("0.1.0")
    .name("Function Name")
    .description("Description")
    .build();
```

## FunctionHandler

```java
FunctionHandler handler = (context, payload) -> {
    // 处理逻辑
    return "{\"success\":true}";
};
```
