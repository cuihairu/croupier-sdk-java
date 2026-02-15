/**
 * NNG Transport Layer for Croupier Java SDK.
 *
 * <p>Implements the NNG (nanomsg-next-gen) based transport for communication
 * with Croupier Agent using REQ/REP pattern.
 */
package io.github.cuihairu.croupier.sdk.transport;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * NNG-based transport client using REQ/REP pattern.
 */
public class NNGTransport {
    private static final Logger LOG = LoggerFactory.getLogger(NNGTransport.class);

    private final String address;
    private final int timeoutMs;
    private int socket;
    private boolean connected;
    private int requestId;

    /**
     * Initialize NNG transport.
     *
     * @param address   NNG address (e.g., "tcp://127.0.0.1:19090")
     * @param timeoutMs Request timeout in milliseconds
     */
    public NNGTransport(String address, int timeoutMs) {
        this.address = address;
        this.timeoutMs = timeoutMs;
        this.socket = -1;
        this.connected = false;
        this.requestId = 0;
    }

    /**
     * Connect to the NNG server (Agent).
     */
    public synchronized void connect() {
        if (connected) {
            return;
        }

        LOG.info("Connecting to NNG server at: {}", address);

        // Create REQ socket
        int[] socketPtr = new int[1];
        int rv = NNGLibrary.INSTANCE.nng_req0_open(socketPtr);
        if (rv != 0) {
            throw new RuntimeException("Failed to create socket: " + NNGLibrary.INSTANCE.nng_strerror(rv));
        }
        socket = socketPtr[0];

        // Set timeouts
        NNGLibrary.INSTANCE.nng_socket_set_ms(socket, "nng:recv-timeout", timeoutMs);
        NNGLibrary.INSTANCE.nng_socket_set_ms(socket, "nng:send-timeout", timeoutMs);

        // Dial to server
        rv = NNGLibrary.INSTANCE.nng_dial(socket, address, null, 0);
        if (rv != 0) {
            NNGLibrary.INSTANCE.nng_close(socket);
            throw new RuntimeException("Failed to dial: " + NNGLibrary.INSTANCE.nng_strerror(rv));
        }

        connected = true;
        LOG.info("Connected to: {}", address);
    }

    /**
     * Close the connection.
     */
    public synchronized void close() {
        if (!connected) {
            return;
        }

        NNGLibrary.INSTANCE.nng_close(socket);
        socket = -1;
        connected = false;

        LOG.info("NNG transport closed");
    }

    /**
     * Check if connected.
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Send a request and wait for response.
     *
     * @param msgType Protocol message type (e.g., MSG_INVOKE_REQUEST)
     * @param data    Protobuf serialized request body
     * @return Pair of [responseMsgType, responseData]
     */
    public synchronized int[] call(int msgType, byte[] data) {
        if (!connected) {
            throw new RuntimeException("Not connected");
        }

        // Generate request ID
        requestId = (requestId + 1) & 0xFFFFFFFF;

        // Build message with protocol header
        byte[] message = Protocol.newMessage(msgType, requestId, data);

        LOG.debug("Sending message type=0x{}, reqId={}", Integer.toHexString(msgType), requestId);

        // Send request
        int rv = NNGLibrary.INSTANCE.nng_send(socket, message, message.length, 0);
        if (rv != 0) {
            throw new RuntimeException("Send failed: " + NNGLibrary.INSTANCE.nng_strerror(rv));
        }

        // Receive response
        PointerByReference responsePtr = new PointerByReference();
        IntByReference responseSize = new IntByReference();
        rv = NNGLibrary.INSTANCE.nng_recv(socket, responsePtr, responseSize, NNGLibrary.NNG_FLAG_ALLOC);
        if (rv != 0) {
            throw new RuntimeException("Receive failed: " + NNGLibrary.INSTANCE.nng_strerror(rv));
        }

        // Copy response data
        byte[] responseData = responsePtr.getValue().getByteArray(0, responseSize.getValue());
        NNGLibrary.INSTANCE.nng_free(responsePtr.getValue(), responseSize.getValue());

        // Parse response
        Protocol.ParsedMessage parsed = Protocol.parseMessage(responseData);

        LOG.debug("Received response type=0x{}, reqId={}", Integer.toHexString(parsed.msgId), parsed.reqId);

        // Verify request ID matches
        if (parsed.reqId != requestId) {
            LOG.warn("Request ID mismatch: expected {}, got {}", requestId, parsed.reqId);
        }

        // Verify response type
        int expectedRespType = Protocol.getResponseMsgID(msgType);
        if (parsed.msgId != expectedRespType) {
            throw new RuntimeException(String.format(
                "Unexpected response type: expected 0x%06X, got 0x%06X",
                expectedRespType, parsed.msgId));
        }

        return new int[]{parsed.msgId, parsed.body.length};
    }

    /**
     * Call and return full response data.
     */
    public synchronized byte[] callWithData(int msgType, byte[] data) {
        if (!connected) {
            throw new RuntimeException("Not connected");
        }

        requestId = (requestId + 1) & 0xFFFFFFFF;
        byte[] message = Protocol.newMessage(msgType, requestId, data);

        int rv = NNGLibrary.INSTANCE.nng_send(socket, message, message.length, 0);
        if (rv != 0) {
            throw new RuntimeException("Send failed: " + NNGLibrary.INSTANCE.nng_strerror(rv));
        }

        PointerByReference responsePtr = new PointerByReference();
        IntByReference responseSize = new IntByReference();
        rv = NNGLibrary.INSTANCE.nng_recv(socket, responsePtr, responseSize, NNGLibrary.NNG_FLAG_ALLOC);
        if (rv != 0) {
            throw new RuntimeException("Receive failed: " + NNGLibrary.INSTANCE.nng_strerror(rv));
        }

        byte[] responseData = responsePtr.getValue().getByteArray(0, responseSize.getValue());
        NNGLibrary.INSTANCE.nng_free(responsePtr.getValue(), responseSize.getValue());

        Protocol.ParsedMessage parsed = Protocol.parseMessage(responseData);

        int expectedRespType = Protocol.getResponseMsgID(msgType);
        if (parsed.msgId != expectedRespType) {
            throw new RuntimeException(String.format(
                "Unexpected response type: expected 0x%06X, got 0x%06X",
                expectedRespType, parsed.msgId));
        }

        return parsed.body;
    }

    /**
     * JNA interface to NNG library.
     */
    public interface NNGLibrary extends Library {
        NNGLibrary INSTANCE = Native.load("nng", NNGLibrary.class);

        int NNG_FLAG_ALLOC = 1;

        int nng_req0_open(int[] socket);
        int nng_rep0_open(int[] socket);
        int nng_dial(int socket, String url, PointerByReference dialer, int flags);
        int nng_listen(int socket, String url, PointerByReference listener, int flags);
        int nng_close(int socket);
        int nng_send(int socket, byte[] data, int size, int flags);
        int nng_recv(int socket, PointerByReference data, IntByReference size, int flags);
        void nng_free(Pointer ptr, int size);
        String nng_strerror(int error);
        int nng_socket_set_ms(int socket, String option, int value);
    }
}
