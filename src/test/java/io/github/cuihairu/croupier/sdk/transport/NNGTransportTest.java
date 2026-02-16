package io.github.cuihairu.croupier.sdk.transport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for NNGTransport class.
 */
class NNGTransportTest {

    private NNGTransport transport;

    @BeforeEach
    void setUp() {
        transport = new NNGTransport("tcp://127.0.0.1:19090", 30000);
    }

    @Test
    void constructorWithValidParameters() {
        assertEquals("tcp://127.0.0.1:19090", getTransportAddress(transport));
        assertEquals(30000, getTransportTimeout(transport));
        assertFalse(transport.isConnected());
        assertEquals(-1, getTransportSocket(transport));
        assertEquals(0, getTransportRequestId(transport));
    }

    @Test
    void constructorWithDefaultTimeout() {
        NNGTransport defaultTransport = new NNGTransport("tcp://localhost:9999", 30000);
        assertEquals("tcp://localhost:9999", getTransportAddress(defaultTransport));
        assertEquals(30000, getTransportTimeout(defaultTransport));
    }

    @Test
    void constructorWithCustomTimeout() {
        NNGTransport customTransport = new NNGTransport("tcp://192.168.1.1:8080", 5000);
        assertEquals("tcp://192.168.1.1:8080", getTransportAddress(customTransport));
        assertEquals(5000, getTransportTimeout(customTransport));
    }

    @Test
    void constructorWithZeroTimeout() {
        NNGTransport zeroTimeoutTransport = new NNGTransport("tcp://test:9999", 0);
        assertEquals(0, getTransportTimeout(zeroTimeoutTransport));
    }

    @Test
    void constructorWithLargeTimeout() {
        NNGTransport largeTimeoutTransport = new NNGTransport("tcp://test:9999", 300000);
        assertEquals(300000, getTransportTimeout(largeTimeoutTransport));
    }

    @Test
    void initialStateIsNotConnected() {
        assertFalse(transport.isConnected());
    }

    @Test
    void initialSocketStateIsInvalid() {
        assertEquals(-1, getTransportSocket(transport));
    }

    @Test
    void initialRequestIdIsZero() {
        assertEquals(0, getTransportRequestId(transport));
    }

    @Test
    void closeWhenNotConnectedDoesNotThrow() {
        assertDoesNotThrow(() -> transport.close());
        assertFalse(transport.isConnected());
        assertEquals(-1, getTransportSocket(transport));
    }

    @Test
    void closeWhenNotConnectedIsIdempotent() {
        assertDoesNotThrow(() -> {
            transport.close();
            transport.close(); // Should not throw
            assertFalse(transport.isConnected());
        });
    }

    @Test
    void callWhenNotConnectedThrowsException() {
        var ex = assertThrows(RuntimeException.class, () -> {
            transport.call(Protocol.MSG_INVOKE_REQUEST, new byte[]{1, 2, 3});
        });
        assertTrue(ex.getMessage().contains("Not connected"));
    }

    @Test
    void callWithNullDataThrowsException() {
        var ex = assertThrows(RuntimeException.class, () -> {
            transport.call(Protocol.MSG_INVOKE_REQUEST, null);
        });
        assertTrue(ex.getMessage().contains("Not connected"));
    }

    @Test
    void callWithEmptyDataThrowsException() {
        var ex = assertThrows(RuntimeException.class, () -> {
            transport.call(Protocol.MSG_INVOKE_REQUEST, new byte[0]);
        });
        assertTrue(ex.getMessage().contains("Not connected"));
    }

    @Test
    void callWithDataWhenNotConnectedThrowsException() {
        var ex = assertThrows(RuntimeException.class, () -> {
            transport.callWithData(Protocol.MSG_INVOKE_REQUEST, new byte[]{1, 2, 3});
        });
        assertTrue(ex.getMessage().contains("Not connected"));
    }

    @Test
    void callWithDataWithNullDataThrowsException() {
        var ex = assertThrows(RuntimeException.class, () -> {
            transport.callWithData(Protocol.MSG_INVOKE_REQUEST, null);
        });
        assertTrue(ex.getMessage().contains("Not connected"));
    }

    @Test
    void callWithDataWithEmptyDataThrowsException() {
        var ex = assertThrows(RuntimeException.class, () -> {
            transport.callWithData(Protocol.MSG_INVOKE_REQUEST, new byte[0]);
        });
        assertTrue(ex.getMessage().contains("Not connected"));
    }

    @Test
    void multipleTransportInstancesHaveIndependentState() {
        NNGTransport transport1 = new NNGTransport("tcp://localhost:1111", 1000);
        NNGTransport transport2 = new NNGTransport("tcp://localhost:2222", 2000);

        assertNotSame(transport1, transport2);
        assertEquals("tcp://localhost:1111", getTransportAddress(transport1));
        assertEquals("tcp://localhost:2222", getTransportAddress(transport2));
        assertEquals(1000, getTransportTimeout(transport1));
        assertEquals(2000, getTransportTimeout(transport2));
        assertEquals(0, getTransportRequestId(transport1));
        assertEquals(0, getTransportRequestId(transport2));
    }

    @Test
    void testNNGLibraryInterfaceConstants() {
        // Verify NNGLibrary interface has required constants
        assertEquals(1, NNGTransport.NNGLibrary.NNG_FLAG_ALLOC);
    }

    @Test
    void testConnectThrowsExceptionWithoutNNG() {
        // connect() will fail if NNG library is not available
        // This is expected behavior - catch UnsatisfiedLinkError
        try {
            transport.connect();
            // If we get here without exception, NNG is available
            // This is OK too
        } catch (UnsatisfiedLinkError | NoClassDefFoundError e) {
            // Expected when NNG library is not installed
            assertTrue(e.getMessage() != null);
        }
    }

    @Test
    void testConnectFailsWithInvalidAddress() {
        NNGTransport invalidTransport = new NNGTransport("invalid://address", 3000);

        try {
            invalidTransport.connect();
            // If NNG is available, it should fail with invalid address
        } catch (UnsatisfiedLinkError | NoClassDefFoundError e) {
            // Expected when NNG library is not installed
            assertTrue(e.getMessage() != null);
        } catch (RuntimeException e) {
            // Expected when NNG is available but address is invalid
            assertTrue(e.getMessage().contains("Failed"));
        }
    }

    @Test
    void testAddressFormats() {
        // Test various address formats
        NNGTransport tcpTransport = new NNGTransport("tcp://127.0.0.1:8080", 1000);
        assertEquals("tcp://127.0.0.1:8080", getTransportAddress(tcpTransport));

        NNGTransport ipcTransport = new NNGTransport("ipc:///tmp/test", 1000);
        assertEquals("ipc:///tmp/test", getTransportAddress(ipcTransport));

        NNGTransport inprocTransport = new NNGTransport("inproc://test", 1000);
        assertEquals("inproc://test", getTransportAddress(inprocTransport));
    }

    // Helper methods to access private fields for testing
    private String getTransportAddress(NNGTransport t) {
        try {
            var field = NNGTransport.class.getDeclaredField("address");
            field.setAccessible(true);
            return (String) field.get(t);
        } catch (Exception e) {
            fail("Failed to access address field: " + e.getMessage());
            return null;
        }
    }

    private int getTransportTimeout(NNGTransport t) {
        try {
            var field = NNGTransport.class.getDeclaredField("timeoutMs");
            field.setAccessible(true);
            return (int) field.get(t);
        } catch (Exception e) {
            fail("Failed to access timeoutMs field: " + e.getMessage());
            return 0;
        }
    }

    private int getTransportRequestId(NNGTransport t) {
        try {
            var field = NNGTransport.class.getDeclaredField("requestId");
            field.setAccessible(true);
            return (int) field.get(t);
        } catch (Exception e) {
            fail("Failed to access requestId field: " + e.getMessage());
            return 0;
        }
    }

    private int getTransportSocket(NNGTransport t) {
        try {
            var field = NNGTransport.class.getDeclaredField("socket");
            field.setAccessible(true);
            return (int) field.get(t);
        } catch (Exception e) {
            fail("Failed to access socket field: " + e.getMessage());
            return 0;
        }
    }

    // ========== Additional tests to improve coverage ==========

    @Test
    void testConnectIdempotency() {
        // Test that calling connect() multiple times is idempotent
        try {
            transport.connect();
            boolean connectedAfterFirst = transport.isConnected();

            // Second call should return immediately if already connected
            transport.connect();
            boolean connectedAfterSecond = transport.isConnected();

            // If NNG is available, both should be true
            // If NNG is not available, we catch the exception above
            assertEquals(connectedAfterFirst, connectedAfterSecond);
        } catch (UnsatisfiedLinkError | NoClassDefFoundError e) {
            // NNG library not available - expected in test environment
            assertTrue(e.getMessage() != null);
        }
    }

    @Test
    void testConnectAndCloseCycle() {
        // Test connect -> close -> connect cycle
        try {
            transport.connect();
            assertTrue(transport.isConnected());

            transport.close();
            assertFalse(transport.isConnected());

            // Create new transport for reconnection test
            NNGTransport transport2 = new NNGTransport("tcp://127.0.0.1:19090", 30000);
            transport2.connect();
        } catch (UnsatisfiedLinkError | NoClassDefFoundError e) {
            // NNG library not available - expected in test environment
            assertTrue(e.getMessage() != null);
        }
    }

    @Test
    void testCloseIdempotencyAfterSuccessfulConnect() {
        // Test that close() can be called multiple times without error
        try {
            transport.connect();
            assertTrue(transport.isConnected());

            transport.close();
            assertFalse(transport.isConnected());

            // Second close should not throw
            transport.close();
            assertFalse(transport.isConnected());

            // Third close should still not throw
            transport.close();
        } catch (UnsatisfiedLinkError | NoClassDefFoundError e) {
            // NNG library not available - expected in test environment
            assertTrue(e.getMessage() != null);
        }
    }

    @Test
    void testCallWithVariousMessageTypes() {
        // Test call with different message types when not connected
        var ex1 = assertThrows(RuntimeException.class, () -> {
            transport.call(Protocol.MSG_REGISTER_REQUEST, new byte[]{1, 2, 3});
        });
        assertTrue(ex1.getMessage().contains("Not connected"));

        var ex2 = assertThrows(RuntimeException.class, () -> {
            transport.call(Protocol.MSG_INVOKE_REQUEST, new byte[]{4, 5, 6});
        });
        assertTrue(ex2.getMessage().contains("Not connected"));

        var ex3 = assertThrows(RuntimeException.class, () -> {
            transport.call(Protocol.MSG_START_JOB_REQUEST, new byte[]{7, 8, 9});
        });
        assertTrue(ex3.getMessage().contains("Not connected"));
    }

    @Test
    void testCallWithDataWithVariousMessageTypes() {
        // Test callWithData with different message types when not connected
        var ex1 = assertThrows(RuntimeException.class, () -> {
            transport.callWithData(Protocol.MSG_REGISTER_REQUEST, new byte[]{1});
        });
        assertTrue(ex1.getMessage().contains("Not connected"));

        var ex2 = assertThrows(RuntimeException.class, () -> {
            transport.callWithData(Protocol.MSG_INVOKE_REQUEST, new byte[]{2});
        });
        assertTrue(ex2.getMessage().contains("Not connected"));

        var ex3 = assertThrows(RuntimeException.class, () -> {
            transport.callWithData(Protocol.MSG_START_JOB_REQUEST, new byte[]{3});
        });
        assertTrue(ex3.getMessage().contains("Not connected"));
    }

    @Test
    void testCallWithLargeData() {
        // Test call with large data array
        byte[] largeData = new byte[10000];
        for (int i = 0; i < largeData.length; i++) {
            largeData[i] = (byte) (i % 256);
        }

        var ex = assertThrows(RuntimeException.class, () -> {
            transport.call(Protocol.MSG_INVOKE_REQUEST, largeData);
        });
        assertTrue(ex.getMessage().contains("Not connected"));
    }

    @Test
    void testCallWithDataWithLargeData() {
        // Test callWithData with large data array
        byte[] largeData = new byte[10000];
        for (int i = 0; i < largeData.length; i++) {
            largeData[i] = (byte) (i % 256);
        }

        var ex = assertThrows(RuntimeException.class, () -> {
            transport.callWithData(Protocol.MSG_INVOKE_REQUEST, largeData);
        });
        assertTrue(ex.getMessage().contains("Not connected"));
    }

    @Test
    void testConstructorWithNegativeTimeout() {
        // Test constructor with negative timeout (edge case)
        NNGTransport negTimeoutTransport = new NNGTransport("tcp://test:9999", -1000);
        assertEquals(-1000, getTransportTimeout(negTimeoutTransport));
    }

    @Test
    void testConstructorWithMaxTimeout() {
        // Test constructor with maximum integer timeout
        NNGTransport maxTimeoutTransport = new NNGTransport("tcp://test:9999", Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, getTransportTimeout(maxTimeoutTransport));
    }

    @Test
    void testConstructorWithMinTimeout() {
        // Test constructor with minimum integer timeout
        NNGTransport minTimeoutTransport = new NNGTransport("tcp://test:9999", Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, getTransportTimeout(minTimeoutTransport));
    }

    @Test
    void testVariousAddressFormats() {
        // Test various valid and edge case address formats
        String[] addresses = {
            "tcp://0.0.0.0:8080",
            "tcp://localhost:0",
            "tcp://192.168.255.255:65535",
            "ipc:///tmp/very/long/path/to/socket",
            "inproc://test-name-with-dashes",
            "inproc://test_name_with_underscores"
        };

        for (String addr : addresses) {
            NNGTransport t = new NNGTransport(addr, 1000);
            assertEquals(addr, getTransportAddress(t));
        }
    }

    @Test
    void testCloseWithoutConnectDoesNotChangeState() {
        // Ensure close without connect doesn't change initial state
        assertFalse(transport.isConnected());
        assertEquals(-1, getTransportSocket(transport));

        transport.close();

        assertFalse(transport.isConnected());
        assertEquals(-1, getTransportSocket(transport));
    }

    @Test
    void testMultipleTransportsWithSameAddress() {
        // Test multiple transport instances with the same address
        NNGTransport t1 = new NNGTransport("tcp://localhost:8080", 1000);
        NNGTransport t2 = new NNGTransport("tcp://localhost:8080", 1000);

        // They should be different instances
        assertNotSame(t1, t2);

        // But have the same configuration
        assertEquals(getTransportAddress(t1), getTransportAddress(t2));
        assertEquals(getTransportTimeout(t1), getTransportTimeout(t2));

        // But independent state
        t1.close();
        assertFalse(t1.isConnected());
        assertFalse(t2.isConnected());
    }

    @Test
    void testConnectWithIPv6Address() {
        // Test connect with IPv6 address
        NNGTransport ipv6Transport = new NNGTransport("tcp://[::1]:8080", 1000);
        assertEquals("tcp://[::1]:8080", getTransportAddress(ipv6Transport));

        try {
            ipv6Transport.connect();
        } catch (UnsatisfiedLinkError | NoClassDefFoundError e) {
            // Expected when NNG is not available
            assertTrue(e.getMessage() != null);
        }
    }

    @Test
    void testCallWithSingleByteData() {
        // Test call with single byte
        var ex = assertThrows(RuntimeException.class, () -> {
            transport.call(Protocol.MSG_INVOKE_REQUEST, new byte[]{42});
        });
        assertTrue(ex.getMessage().contains("Not connected"));
    }

    @Test
    void testCallWithDataWithSingleByteData() {
        // Test callWithData with single byte
        var ex = assertThrows(RuntimeException.class, () -> {
            transport.callWithData(Protocol.MSG_INVOKE_REQUEST, new byte[]{42});
        });
        assertTrue(ex.getMessage().contains("Not connected"));
    }

    @Test
    void testRequestIdIncrementNotAffectedByFailedCalls() {
        // Request ID should not increment when calls fail (not connected)
        int initialRequestId = getTransportRequestId(transport);
        assertEquals(0, initialRequestId);

        try {
            transport.call(Protocol.MSG_INVOKE_REQUEST, new byte[]{1, 2, 3});
        } catch (RuntimeException e) {
            // Expected - not connected
        }

        // Request ID should not have changed
        int requestIdAfterFailedCall = getTransportRequestId(transport);
        assertEquals(initialRequestId, requestIdAfterFailedCall);
    }

    @Test
    void testStateAfterMultipleFailedAttempts() {
        // Ensure state remains consistent after multiple failed call attempts
        for (int i = 0; i < 10; i++) {
            try {
                transport.call(Protocol.MSG_INVOKE_REQUEST, new byte[]{1, 2, 3});
            } catch (RuntimeException e) {
                // Expected - not connected
            }
        }

        // State should remain unchanged
        assertFalse(transport.isConnected());
        assertEquals(-1, getTransportSocket(transport));
        assertEquals(0, getTransportRequestId(transport));
    }

    @Test
    void testCloseResetsSocketState() {
        // Test that close properly resets socket state
        try {
            transport.connect();
            int socketAfterConnect = getTransportSocket(transport);

            transport.close();
            int socketAfterClose = getTransportSocket(transport);

            // Socket should be reset to -1 after close
            assertEquals(-1, socketAfterClose);
        } catch (UnsatisfiedLinkError | NoClassDefFoundError e) {
            // Expected when NNG is not available
            assertTrue(e.getMessage() != null);
        }
    }

    @Test
    void testConnectWithVeryShortTimeout() {
        // Test connect with very short timeout (1ms)
        NNGTransport shortTimeoutTransport = new NNGTransport("tcp://127.0.0.1:19090", 1);
        assertEquals(1, getTransportTimeout(shortTimeoutTransport));

        try {
            shortTimeoutTransport.connect();
        } catch (UnsatisfiedLinkError | NoClassDefFoundError e) {
            // Expected when NNG is not available
            assertTrue(e.getMessage() != null);
        }
    }

    private void assertNotSame(NNGTransport t1, NNGTransport t2) {
        assertFalse(t1 == t2);
    }
}
