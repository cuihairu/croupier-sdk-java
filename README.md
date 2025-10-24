# Croupier SDK (Java)

Work in progress. This module will provide a Java SDK for Croupier to:
- Register local handlers (FunctionService) and interact with Agent
- Call functions via FunctionService (Core/Agent) with retries/metadata

Build & Test
```
./gradlew build        # if wrapper available
# or: gradle build      # using system Gradle
```

Links
- Main project: https://github.com/cuihairu/croupier
- Go SDK (reference): https://github.com/cuihairu/croupier-sdk-go

Status
- Skeleton only; API shape will follow the Go SDK first, then evolve with IDL-generated stubs.
