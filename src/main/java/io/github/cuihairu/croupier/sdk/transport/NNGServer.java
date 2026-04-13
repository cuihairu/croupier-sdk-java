package io.github.cuihairu.croupier.sdk.transport;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * NNG REP server for local function hosting.
 */
public final class NNGServer implements RequestServer {
    private static final Logger LOG = LoggerFactory.getLogger(NNGServer.class);

    private final String address;
    private final int timeoutMs;
    private volatile RequestHandler handler;
    private volatile boolean listening;
    private volatile boolean stopRequested;
    private volatile int socket = -1;
    private Thread listenThread;

    public NNGServer(String address, int timeoutMs) {
        this.address = address;
        this.timeoutMs = timeoutMs;
    }

    @Override
    public synchronized void setHandler(RequestHandler handler) {
        this.handler = handler;
    }

    @Override
    public synchronized void listen() {
        if (listening) {
            return;
        }

        try {
            NNGTransport.NNGLibrary nng = NNGTransport.NNGLibrary.INSTANCE;
            IntByReference socketRef = new IntByReference();
            int result = nng.nng_rep0_open(socketRef);
            if (result != 0) {
                throw new RuntimeException("Failed to open REP socket: error " + result);
            }

            socket = socketRef.getValue();
            result = nng.nng_setopt_ms(socket, "recv-timeout", timeoutMs);
            if (result != 0) {
                nng.nng_close(socket);
                socket = -1;
                throw new RuntimeException("Failed to set recv timeout: error " + result);
            }

            result = nng.nng_setopt_ms(socket, "send-timeout", timeoutMs);
            if (result != 0) {
                nng.nng_close(socket);
                socket = -1;
                throw new RuntimeException("Failed to set send timeout: error " + result);
            }

            result = nng.nng_listen(socket, address, null, 0);
            if (result != 0) {
                nng.nng_close(socket);
                socket = -1;
                throw new RuntimeException("Failed to listen on " + address + ": error " + result);
            }

            stopRequested = false;
            listening = true;
            listenThread = new Thread(this::listenLoop, "croupier-java-nng-server");
            listenThread.setDaemon(true);
            listenThread.start();
            LOG.info("NNG server listening on {}", address);
        } catch (UnsatisfiedLinkError e) {
            throw new RuntimeException(
                "NNG native library not found. Please install NNG: https://github.com/nanomsg/nng",
                e
            );
        }
    }

    @Override
    public boolean isListening() {
        return listening;
    }

    @Override
    public synchronized void close() {
        stopRequested = true;
        listening = false;

        if (socket != -1) {
            try {
                NNGTransport.NNGLibrary.INSTANCE.nng_close(socket);
            } catch (Exception e) {
                LOG.warn("Error closing NNG server socket: {}", e.getMessage());
            } finally {
                socket = -1;
            }
        }

        if (listenThread != null) {
            try {
                listenThread.join(1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                listenThread = null;
            }
        }
    }

    private void listenLoop() {
        NNGTransport.NNGLibrary nng = NNGTransport.NNGLibrary.INSTANCE;

        while (!stopRequested && socket != -1) {
            PointerByReference recvBufRef = new PointerByReference();
            IntByReference sizeRef = new IntByReference();

            int recvResult = nng.nng_recv(socket, recvBufRef, sizeRef, NNGTransport.NNGLibrary.NNG_FLAG_ALLOC);
            if (recvResult != 0) {
                if (!stopRequested) {
                    LOG.debug("NNG server receive returned error {}", recvResult);
                }
                continue;
            }

            Pointer recvBuf = recvBufRef.getValue();
            int recvSize = sizeRef.getValue();
            byte[] response = new byte[0];

            try {
                byte[] request = recvBuf.getByteArray(0, recvSize);
                Protocol.ParsedMessage parsed = Protocol.parseMessage(request);
                RequestHandler currentHandler = handler;
                byte[] responseBody = currentHandler != null
                    ? currentHandler.handle(parsed.msgId, parsed.reqId, parsed.body)
                    : new byte[0];
                response = Protocol.newMessage(
                    Protocol.getResponseMsgID(parsed.msgId),
                    parsed.reqId,
                    responseBody
                );
            } catch (Exception e) {
                LOG.warn("Failed to process NNG request: {}", e.getMessage());
            } finally {
                nng.nng_free(recvBuf, recvSize);
            }

            try {
                ByteBuffer directBuffer = ByteBuffer.allocateDirect(response.length);
                directBuffer.put(response);
                directBuffer.flip();
                Pointer sendBuf = Native.getDirectBufferPointer(directBuffer);
                int sendResult = nng.nng_send(socket, sendBuf, response.length, 0);
                if (sendResult != 0 && !stopRequested) {
                    throw new IOException("Send failed: error " + sendResult);
                }
            } catch (Exception e) {
                if (!stopRequested) {
                    LOG.warn("Failed to send NNG response: {}", e.getMessage());
                }
            }
        }
    }
}
