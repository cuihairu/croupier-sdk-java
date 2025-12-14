package io.github.cuihairu.croupier.sdk;

/**
 * Croupier Java SDK Client
 *
 * Package structure explanation:
 * - Original domain: cuihairu.github.io
 * - Reversed domain: io.github.cuihairu
 * - Project name: croupier
 * - Module name: sdk
 * - Full package: io.github.cuihairu.croupier.sdk
 *
 * Maven GroupID: croupier.cuihairu.github.io
 * Note: Maven often puts project name first for easier sorting
 */
public class CroupierClient {
    private final String gameId;
    private final String serviceId;

    public CroupierClient(String gameId, String serviceId) {
        this.gameId = gameId;
        this.serviceId = serviceId;
    }

    public void connect() {
        // Implementation
        System.out.println("Connecting to Croupier server...");
    }
}