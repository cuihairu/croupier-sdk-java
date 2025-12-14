package croupier.cuihairu.github.io.sdk;

/**
 * Croupier Java SDK Client
 *
 * This is an example showing the correct package structure.
 * The package name follows the reverse domain naming convention:
 * - io: top-level domain
 * - github.io: second-level domain
 * - cuihairu: username
 * - croupier: project name
 * - sdk: module name
 */
public class CroupierClient {
    private String gameId;
    private String serviceId;

    public CroupierClient(String gameId, String serviceId) {
        this.gameId = gameId;
        this.serviceId = serviceId;
    }

    public void connect() {
        // Implementation here
    }
}