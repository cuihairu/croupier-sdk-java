package com.cuihairu.croupier.sdk;

/**
 * Croupier Java SDK skeleton.
 * This is a placeholder client; networking and gRPC bindings will be added later.
 */
public class CroupierClient {
    private final String agentAddr;

    public CroupierClient(String agentAddr) {
        this.agentAddr = agentAddr;
    }

    public void connect() {
        // TODO: connect to Agent via gRPC; register functions
        // skeleton: no-op
    }

    public String getAgentAddr() { return agentAddr; }
}

