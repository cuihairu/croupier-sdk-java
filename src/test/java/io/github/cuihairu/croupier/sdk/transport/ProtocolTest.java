/**
 * Unit tests for Protocol class.
 */
package io.github.cuihairu.croupier.sdk.transport;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProtocolTest {

    @Test
    void testNewMessage() {
        int msgType = Protocol.MSG_INVOKE_REQUEST;
        int reqId = 12345;
        byte[] body = "test payload".getBytes();

        byte[] message = Protocol.newMessage(msgType, reqId, body);

        assertEquals(Protocol.HEADER_SIZE + body.length, message.length);
        assertEquals(Protocol.VERSION_1, message[0]);
    }

    @Test
    void testParseMessage() {
        int msgType = Protocol.MSG_REGISTER_LOCAL_REQUEST;
        int reqId = 999;
        byte[] body = "hello world".getBytes();

        byte[] message = Protocol.newMessage(msgType, reqId, body);
        Protocol.ParsedMessage parsed = Protocol.parseMessage(message);

        assertEquals(Protocol.VERSION_1, parsed.version);
        assertEquals(msgType, parsed.msgId);
        assertEquals(reqId, parsed.reqId);
        assertArrayEquals(body, parsed.body);
    }

    @Test
    void testGetResponseMsgID() {
        assertEquals(Protocol.MSG_INVOKE_RESPONSE, Protocol.getResponseMsgID(Protocol.MSG_INVOKE_REQUEST));
        assertEquals(Protocol.MSG_REGISTER_LOCAL_RESPONSE, Protocol.getResponseMsgID(Protocol.MSG_REGISTER_LOCAL_REQUEST));
    }

    @Test
    void testIsRequest() {
        assertTrue(Protocol.isRequest(Protocol.MSG_INVOKE_REQUEST));
        assertTrue(Protocol.isRequest(Protocol.MSG_REGISTER_LOCAL_REQUEST));
        assertFalse(Protocol.isRequest(Protocol.MSG_INVOKE_RESPONSE));
        assertFalse(Protocol.isRequest(Protocol.MSG_REGISTER_LOCAL_RESPONSE));
    }

    @Test
    void testIsResponse() {
        assertTrue(Protocol.isResponse(Protocol.MSG_INVOKE_RESPONSE));
        assertTrue(Protocol.isResponse(Protocol.MSG_REGISTER_LOCAL_RESPONSE));
        assertFalse(Protocol.isResponse(Protocol.MSG_INVOKE_REQUEST));
        assertFalse(Protocol.isResponse(Protocol.MSG_REGISTER_LOCAL_REQUEST));
    }

    @Test
    void testMsgIdString() {
        assertEquals("InvokeRequest", Protocol.msgIdString(Protocol.MSG_INVOKE_REQUEST));
        assertEquals("InvokeResponse", Protocol.msgIdString(Protocol.MSG_INVOKE_RESPONSE));
        assertEquals("RegisterLocalRequest", Protocol.msgIdString(Protocol.MSG_REGISTER_LOCAL_REQUEST));
        assertTrue(Protocol.msgIdString(0xFFFFFF).startsWith("Unknown"));
    }

    @Test
    void testPutAndGetMsgID() {
        int msgId = 0x030101;
        byte[] buf = new byte[3];
        Protocol.putMsgID(buf, 0, msgId);
        int decoded = Protocol.getMsgID(buf, 0);
        assertEquals(msgId, decoded);
    }
}
