package com.cuihairu.croupier.sdk;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CroupierClientTest {
    @Test
    public void testConstruct() {
        CroupierClient cli = new CroupierClient("127.0.0.1:19090");
        assertEquals("127.0.0.1:19090", cli.getAgentAddr());
    }
}

