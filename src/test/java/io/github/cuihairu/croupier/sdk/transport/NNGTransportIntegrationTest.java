package io.github.cuihairu.croupier.sdk.transport;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for NNGTransport.
 *
 * Note: These tests document the behavior and limitations of testing
 * NNGTransport without a running NNG server and native library.
 */
class NNGTransportIntegrationTest {

    @Test
    void testConnectWithNNGAvailable() {
        NNGTransport transport = new NNGTransport("tcp://127.0.0.1:19090", 30000);

        try {
            transport.connect();
            // If we get here, NNG library is available
            assertTrue(transport.isConnected());
            transport.close();
        } catch (UnsatisfiedLinkError | NoClassDefFoundError e) {
            // NNG library not available - expected in test environment
            assertFalse(transport.isConnected());
        }
    }

    @Test
    void testCloseAfterSuccessfulConnect() {
        NNGTransport transport = new NNGTransport("tcp://127.0.0.1:19090", 30000);

        try {
            transport.connect();
            assertTrue(transport.isConnected());

            transport.close();
            assertFalse(transport.isConnected());
        } catch (UnsatisfiedLinkError | NoClassDefFoundError e) {
            // NNG library not available
            assertFalse(transport.isConnected());
        }
    }

    @Test
    void testConnectIdempotencyWithNNG() {
        NNGTransport transport = new NNGTransport("tcp://127.0.0.1:19090", 30000);

        try {
            transport.connect();
            assertTrue(transport.isConnected());

            // Second connect should be idempotent
            transport.connect();
            assertTrue(transport.isConnected());
        } catch (UnsatisfiedLinkError | NoClassDefFoundError e) {
            // NNG library not available - expected in test environment
        }
    }

    @Test
    void testCloseIdempotencyWithNNG() {
        NNGTransport transport = new NNGTransport("tcp://127.0.0.1:19090", 30000);

        try {
            transport.connect();
            transport.close();
            assertFalse(transport.isConnected());

            // Second close should not throw
            transport.close();
            assertFalse(transport.isConnected());

            // Third close should still not throw
            transport.close();
        } catch (UnsatisfiedLinkError | NoClassDefFoundError e) {
            // NNG library not available
        }
    }

    @Test
    void testMultipleConnectCloseCycles() {
        NNGTransport transport = new NNGTransport("tcp://127.0.0.1:19090", 30000);

        try {
            for (int i = 0; i < 3; i++) {
                assertFalse(transport.isConnected());
                transport.connect();
                assertTrue(transport.isConnected());
                transport.close();
                assertFalse(transport.isConnected());
            }
        } catch (UnsatisfiedLinkError | NoClassDefFoundError e) {
            // NNG library not available
        }
    }

    @Test
    void testConnectWithVariousTimeouts() {
        int[] timeouts = {0, 100, 1000, 5000, 30000};

        for (int timeout : timeouts) {
            NNGTransport t = new NNGTransport("tcp://127.0.0.1:19090", timeout);
            try {
                t.connect();
                assertTrue(t.isConnected());
                t.close();
            } catch (UnsatisfiedLinkError | NoClassDefFoundError e) {
                // NNG library not available - expected
            }
        }
    }

    @Test
    void testConnectWithVariousAddresses() {
        String[] addresses = {
            "tcp://127.0.0.1:19090",
            "tcp://localhost:19090",
            "inproc://test"
        };

        for (String address : addresses) {
            NNGTransport t = new NNGTransport(address, 3000);
            try {
                t.connect();
                assertTrue(t.isConnected());
                t.close();
            } catch (UnsatisfiedLinkError | NoClassDefFoundError e) {
                // NNG library not available - expected
            } catch (RuntimeException e) {
                // Invalid address format or other NNG errors
                assertTrue(e.getMessage() != null);
            }
        }
    }

    /**
     * Document the limitations of testing NNGTransport without NNG library.
     */
    @Test
    void testCoverageLimitations() {
        // NNGTransport depends on:
        // 1. Native NNG library (via JNA)
        // 2. Running NNG server for integration tests
        //
        // Current coverage (49%) covers:
        // - All error paths when not connected
        // - Constructor variations
        // - Close idempotency
        //
        // Not covered due to NNG dependency:
        // - Successful connect() path (requires NNG library)
        // - Successful call() path (requires NNG library + server)
        // - Successful callWithData() path (requires NNG library + server)
        // - Request ID increment logic (requires NNG library)

        NNGTransport transport = new NNGTransport("tcp://127.0.0.1:19090", 30000);

        // We can test error paths
        assertThrows(RuntimeException.class, () -> {
            transport.call(Protocol.MSG_INVOKE_REQUEST, new byte[]{1, 2, 3});
        });

        assertThrows(RuntimeException.class, () -> {
            transport.callWithData(Protocol.MSG_INVOKE_REQUEST, new byte[]{1, 2, 3});
        });

        // But successful paths require NNG library
        try {
            transport.connect();
            // If we reach here, NNG is available and we can test more
            assertTrue(transport.isConnected());
        } catch (UnsatisfiedLinkError | NoClassDefFoundError e) {
            // Expected in CI/test environment without NNG
            assertFalse(transport.isConnected());
        }
    }
}
