/**
 * NNG Transport Layer for Croupier Java SDK.
 *
 * <p>Implements the NNG (nanomsg-next-gen) based transport for communication
 * with Croupier Agent using REQ/REP pattern.</p>
 *
 * <p>This implementation uses JNA to call the native NNG library.</p>
 *
 * <p><b>Prerequisites:</b> The NNG native library must be installed on the system.
 * See: https://github.com/nanomsg/nng</p>
 */
package io.github.cuihairu.croupier.sdk.transport;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * NNG-based transport client using REQ/REP pattern.
 */
public class NNGTransport {
    private static final Logger LOG = LoggerFactory.getLogger(NNGTransport.class);

    /**
     * JNA interface to NNG native library.
     */
    public interface NNGLibrary extends Library {
        NNGLibrary INSTANCE = Native.load("nng", NNGLibrary.class);

        // Socket functions
        int nng_req0_open(IntByReference socket);
        int nng_close(int socket);

        // Dial functions
        int nng_dial(int socket, String url, PointerByReference dialer, int flags);

        // Send/receive functions
        int nng_send(int socket, Pointer data, int size, int flags);
        int nng_recv(int socket, PointerByReference buf, IntByReference size, int flags);

        // Memory functions
        void nng_free(Pointer data, int size);

        // Options
        int nng_setopt_int(int socket, String option, int value);
        int nng_setopt_ms(int socket, String option, int milliseconds);

        // Flags
        int NNG_FLAG_ALLOC = 1;
        int NNG_FLAG_NONBLOCK = 2;
    }

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

        try {
            NNGLibrary nng = NNGLibrary.INSTANCE;

            // Create REQ socket
            IntByReference socketRef = new IntByReference();
            int result = nng.nng_req0_open(socketRef);
            if (result != 0) {
                throw new RuntimeException("Failed to open socket: error " + result);
            }
            this.socket = socketRef.getValue();

            // Set receive timeout
            result = nng.nng_setopt_ms(this.socket, "recv-timeout", timeoutMs);
            if (result != 0) {
                nng.nng_close(this.socket);
                this.socket = -1;
                throw new RuntimeException("Failed to set timeout: error " + result);
            }

            // Dial to server
            result = nng.nng_dial(this.socket, address, null, 0);
            if (result != 0) {
                nng.nng_close(this.socket);
                this.socket = -1;
                throw new RuntimeException("Failed to dial: error " + result);
            }

            connected = true;
            LOG.info("Connected to: {}", address);
        } catch (UnsatisfiedLinkError e) {
            throw new RuntimeException(
                "NNG native library not found. Please install NNG: https://github.com/nanomsg/nng",
                e
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect: " + e.getMessage(), e);
        }
    }

    /**
     * Close the connection.
     */
    public synchronized void close() {
        if (!connected) {
            return;
        }

        try {
            if (socket != -1) {
                NNGLibrary.INSTANCE.nng_close(socket);
                socket = -1;
            }
        } catch (Exception e) {
            LOG.warn("Error closing socket: {}", e.getMessage());
        } finally {
            connected = false;
        }

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
     * @return Pair of [responseMsgType, responseDataLength]
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

        try {
            NNGLibrary nng = NNGLibrary.INSTANCE;

            // Send request
            Pointer sendBuf = Native.getDirectBufferPointer(
                ByteBuffer.allocateDirect(message.length)
                    .put(message)
                    .flip()
            );

            int sendResult = nng.nng_send(socket, sendBuf, message.length, 0);
            if (sendResult != 0) {
                throw new IOException("Send failed: error " + sendResult);
            }

            // Receive response
            PointerByReference recvBufRef = new PointerByReference();
            IntByReference sizeRef = new IntByReference();

            int recvResult = nng.nng_recv(socket, recvBufRef, sizeRef, NNGLibrary.NNG_FLAG_ALLOC);
            if (recvResult != 0) {
                throw new IOException("Receive failed: error " + recvResult);
            }

            Pointer recvBuf = recvBufRef.getValue();
            int recvSize = sizeRef.getValue();

            try {
                // Copy response data
                byte[] responseData = recvBuf.getByteArray(0, recvSize);

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

            } finally {
                // Free receive buffer
                nng.nng_free(recvBuf, recvSize);
            }

        } catch (IOException e) {
            throw new RuntimeException("NNG call failed: " + e.getMessage(), e);
        }
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

        try {
            NNGLibrary nng = NNGLibrary.INSTANCE;

            // Send request
            Pointer sendBuf = Native.getDirectBufferPointer(
                ByteBuffer.allocateDirect(message.length)
                    .put(message)
                    .flip()
            );

            int sendResult = nng.nng_send(socket, sendBuf, message.length, 0);
            if (sendResult != 0) {
                throw new IOException("Send failed: error " + sendResult);
            }

            // Receive response
            PointerByReference recvBufRef = new PointerByReference();
            IntByReference sizeRef = new IntByReference();

            int recvResult = nng.nng_recv(socket, recvBufRef, sizeRef, NNGLibrary.NNG_FLAG_ALLOC);
            if (recvResult != 0) {
                throw new IOException("Receive failed: error " + recvResult);
            }

            Pointer recvBuf = recvBufRef.getValue();
            int recvSize = sizeRef.getValue();

            try {
                byte[] responseData = recvBuf.getByteArray(0, recvSize);
                Protocol.ParsedMessage parsed = Protocol.parseMessage(responseData);

                int expectedRespType = Protocol.getResponseMsgID(msgType);
                if (parsed.msgId != expectedRespType) {
                    throw new RuntimeException(String.format(
                        "Unexpected response type: expected 0x%06X, got 0x%06X",
                        expectedRespType, parsed.msgId));
                }

                return parsed.body;

            } finally {
                nng.nng_free(recvBuf, recvSize);
            }

        } catch (IOException e) {
            throw new RuntimeException("NNG call failed: " + e.getMessage(), e);
        }
    }
}
