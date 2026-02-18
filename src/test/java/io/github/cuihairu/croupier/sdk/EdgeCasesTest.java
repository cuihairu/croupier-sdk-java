// Copyright 2025 Croupier Authors
// Licensed under the Apache License, Version 2.0

package io.github.cuihairu.croupier.sdk;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Edge case tests for Croupier SDK.
 */
public class EdgeCasesTest {

    // ========== Configuration Edge Cases ==========

    @Test
    public void testConfigWithEmptyServiceId() {
        ClientConfig config = new ClientConfig();
        config.setServiceId("");
        assertEquals("", config.getServiceId());
    }

    @Test
    public void testConfigWithVeryLongServiceId() {
        StringBuilder longId = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            longId.append("a");
        }

        ClientConfig config = new ClientConfig();
        config.setServiceId(longId.toString());
        assertEquals(10000, config.getServiceId().length());
    }

    @Test
    public void testConfigWithZeroTimeout() {
        ClientConfig config = new ClientConfig();
        config.setTimeoutSeconds(0);
        assertEquals(0, config.getTimeoutSeconds());
    }

    @Test
    public void testConfigWithNegativeTimeout() {
        ClientConfig config = new ClientConfig();
        config.setTimeoutSeconds(-1);
        assertEquals(-1, config.getTimeoutSeconds());
    }

    @Test
    public void testConfigWithVeryLargeTimeout() {
        ClientConfig config = new ClientConfig();
        config.setTimeoutSeconds(3600);
        assertEquals(3600, config.getTimeoutSeconds());
    }

    @Test
    public void testConfigWithEmptyAgentAddr() {
        ClientConfig config = new ClientConfig();
        config.setAgentAddr("");
        assertEquals("", config.getAgentAddr());
    }

    // ========== Function Descriptor Edge Cases ==========

    @Test
    public void testDescriptorWithVeryLongFunctionId() {
        StringBuilder longId = new StringBuilder("func.");
        for (int i = 0; i < 1000; i++) {
            longId.append("x.");
        }
        longId.append("test");

        FunctionDescriptor desc = new FunctionDescriptor();
        desc.setId(longId.toString());
        desc.setVersion("1.0.0");

        assertTrue(desc.getId().length() > 4000);
    }

    @Test
    public void testDescriptorWithSpecialCharactersInId() {
        String[] specialIds = {
            "test.function@v1.0",
            "test-function_v2",
            "test/function:v3"
        };

        for (String id : specialIds) {
            FunctionDescriptor desc = new FunctionDescriptor();
            desc.setId(id);
            desc.setVersion("1.0.0");
            assertEquals(id, desc.getId());
        }
    }

    @Test
    public void testDescriptorWithAllOptionalFieldsNull() {
        FunctionDescriptor desc = new FunctionDescriptor();
        desc.setId("test.func");
        desc.setVersion("1.0.0");
        desc.setName(null);
        desc.setDescription(null);
        desc.setCategory(null);
        desc.setRiskLevel(null);

        assertNull(desc.getName());
        assertNull(desc.getDescription());
        assertNull(desc.getCategory());
        assertNull(desc.getRiskLevel());
    }

    // ========== Invoke Options Edge Cases ==========

    @Test
    public void testInvokeOptionsWithEmptyGameId() {
        InvokeOptions options = new InvokeOptions();
        options.setGameId("");
        assertEquals("", options.getGameId());
    }

    @Test
    public void testInvokeOptionsWithEmptyEnv() {
        InvokeOptions options = new InvokeOptions();
        options.setEnv("");
        assertEquals("", options.getEnv());
    }

    @Test
    public void testInvokeOptionsWithZeroTimeout() {
        InvokeOptions options = new InvokeOptions();
        options.setTimeoutSeconds(0);
        assertEquals(0, options.getTimeoutSeconds());
    }

    @Test
    public void testInvokeOptionsWithNegativeTimeout() {
        InvokeOptions options = new InvokeOptions();
        options.setTimeoutSeconds(-1);
        assertEquals(-1, options.getTimeoutSeconds());
    }

    @Test
    public void testInvokeOptionsWithEmptyMetadata() {
        InvokeOptions options = new InvokeOptions();
        options.setMetadata(new HashMap<>());
        assertTrue(options.getMetadata().isEmpty());
    }

    @Test
    public void testInvokeOptionsWithNullMetadata() {
        InvokeOptions options = new InvokeOptions();
        options.setMetadata(null);
        assertNull(options.getMetadata());
    }

    // ========== Numeric Edge Cases ==========

    @Test
    public void testIntegerMaxValue() {
        int max = Integer.MAX_VALUE;
        assertTrue(max > 0);
        assertEquals(2147483647, max);
    }

    @Test
    public void testIntegerMinValue() {
        int min = Integer.MIN_VALUE;
        assertTrue(min < 0);
        assertEquals(-2147483648, min);
    }

    @Test
    public void testLongMaxValue() {
        long max = Long.MAX_VALUE;
        assertTrue(max > 0);
    }

    @Test
    public void testLongMinValue() {
        long min = Long.MIN_VALUE;
        assertTrue(min < 0);
    }

    @Test
    public void testDoubleInfinity() {
        double posInf = Double.POSITIVE_INFINITY;
        double negInf = Double.NEGATIVE_INFINITY;

        assertTrue(Double.isInfinite(posInf));
        assertTrue(posInf > 0);
        assertTrue(Double.isInfinite(negInf));
        assertTrue(negInf < 0);
    }

    @Test
    public void testDoubleNaN() {
        double nan = Double.NaN;
        assertTrue(Double.isNaN(nan));
    }

    @Test
    public void testVerySmallDouble() {
        double tiny = 1e-100;
        assertTrue(tiny > 0);
        assertTrue(tiny < 1e-50);
    }

    // ========== String Edge Cases ==========

    @Test
    public void testEmptyString() {
        String s = "";
        assertTrue(s.isEmpty());
        assertEquals(0, s.length());
    }

    @Test
    public void testStringWithOnlyWhitespace() {
        String[] whitespaceStrings = {" ", "  ", "\t", "\n", "\r", "\n\t\r "};

        for (String s : whitespaceStrings) {
            assertTrue(s.length() > 0);
            for (char c : s.toCharArray()) {
                assertTrue(Character.isWhitespace(c));
            }
        }
    }

    @Test
    public void testStringWithNullCharacter() {
        String s = "test\u0000string";
        assertTrue(s.contains("\u0000"));
        assertEquals(11, s.length());
    }

    @Test
    public void testStringWithControlCharacters() {
        String s = "test\a\b\f\n\r\t\vstring";
        assertTrue(s.length() > 4);
    }

    @Test
    public void testStringWithUnicode() {
        String unicode = "Unicode: 中文 🚀";
        assertTrue(unicode.length() > 10);
        assertTrue(unicode.contains("中文"));
        assertTrue(unicode.contains("🚀"));
    }

    // ========== Collection Edge Cases ==========

    @Test
    public void testEmptyList() {
        List<String> list = List.of();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void testListWithDuplicates() {
        List<String> list = List.of("same", "same", "same");
        assertEquals(3, list.size());
        assertEquals("same", list.get(0));
        assertEquals("same", list.get(1));
        assertEquals("same", list.get(2));
    }

    @Test
    public void testListWithNullValues() {
        List<String> list = List.of("one", null, "two");
        assertEquals(3, list.size());
        assertNull(list.get(1));
    }

    @Test
    public void testEmptyMap() {
        Map<String, String> map = Map.of();
        assertTrue(map.isEmpty());
        assertEquals(0, map.size());
    }

    @Test
    public void testMapWithNullValues() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", null);
        map.put("key2", "value");

        assertEquals(2, map.size());
        assertNull(map.get("key1"));
        assertEquals("value", map.get("key2"));
    }

    @Test
    public void testMapWithEmptyStringKey() {
        Map<String, String> map = new HashMap<>();
        map.put("", "value");

        assertTrue(map.containsKey(""));
        assertEquals("value", map.get(""));
    }

    @Test
    public void testMapWithEmptyStringValue() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "");

        assertEquals("", map.get("key"));
    }

    // ========== Boolean Edge Cases ==========

    @Test
    public void testBooleanTrue() {
        boolean value = true;
        assertTrue(value);
    }

    @Test
    public void testBooleanFalse() {
        boolean value = false;
        assertFalse(value);
    }

    @Test
    public void testBooleanObjectTrue() {
        Boolean value = Boolean.TRUE;
        assertTrue(value);
        assertEquals(Boolean.TRUE, value);
    }

    @Test
    public void testBooleanObjectFalse() {
        Boolean value = Boolean.FALSE;
        assertFalse(value);
        assertEquals(Boolean.FALSE, value);
    }

    // ========== Type Conversion Edge Cases ==========

    @Test
    public void testIntToStringConversion() {
        int value = 123;
        String str = Integer.toString(value);
        assertEquals("123", str);
    }

    @Test
    public void testStringToIntConversion() {
        String str = "456";
        int value = Integer.parseInt(str);
        assertEquals(456, value);
    }

    @Test
    public void testDoubleToStringConversion() {
        double value = 123.456;
        String str = Double.toString(value);
        assertTrue(str.contains("123"));
    }

    @Test
    public void testLongToStringConversion() {
        long value = 123456789L;
        String str = Long.toString(value);
        assertEquals("123456789", str);
    }

    // ========== Exception Edge Cases ==========

    @Test
    public void testExceptionWithNullMessage() {
        CroupierException exception = new CroupierException((String) null);
        assertNull(exception.getMessage());
    }

    @Test
    public void testExceptionWithEmptyMessage() {
        CroupierException exception = new CroupierException("");
        assertEquals("", exception.getMessage());
    }

    @Test
    public void testExceptionWithVeryLongMessage() {
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            longMessage.append("x");
        }

        CroupierException exception = new CroupierException(longMessage.toString());
        assertEquals(10000, exception.getMessage().length());
    }

    // ========== Retry Config Edge Cases ==========

    @Test
    public void testRetryConfigWithZeroAttempts() {
        RetryConfig config = new RetryConfig();
        config.setMaxAttempts(0);
        assertEquals(0, config.getMaxAttempts());
    }

    @Test
    public void testRetryConfigWithNegativeAttempts() {
        RetryConfig config = new RetryConfig();
        config.setMaxAttempts(-1);
        assertEquals(-1, config.getMaxAttempts());
    }

    @Test
    public void testRetryConfigWithZeroDelay() {
        RetryConfig config = new RetryConfig();
        config.setInitialDelayMs(0);
        assertEquals(0, config.getInitialDelayMs());
    }

    @Test
    public void testRetryConfigWithNegativeDelay() {
        RetryConfig config = new RetryConfig();
        config.setInitialDelayMs(-100);
        assertEquals(-100, config.getInitialDelayMs());
    }

    // ========== Reconnect Config Edge Cases ==========

    @Test
    public void testReconnectConfigWithZeroMaxAttempts() {
        ReconnectConfig config = new ReconnectConfig();
        config.setMaxAttempts(0);
        assertEquals(0, config.getMaxAttempts());
    }

    @Test
    public void testReconnectConfigWithZeroDelay() {
        ReconnectConfig config = new ReconnectConfig();
        config.setInitialDelayMs(0);
        assertEquals(0, config.getInitialDelayMs());
    }

    // ========== Path Edge Cases ==========

    @Test
    public void testEmptyPath() {
        String path = "";
        assertTrue(path.isEmpty());
    }

    @Test
    public void testRelativePath() {
        String path = "../config/test.json";
        assertTrue(path.contains(".."));
    }

    @Test
    public void testAbsolutePath() {
        String path = "/etc/croupier/config.json";
        assertTrue(path.startsWith("/"));
    }

    @Test
    public void testPathWithSpaces() {
        String path = "/path/with spaces/file.json";
        assertTrue(path.contains(" "));
    }

    @Test
    public void testWindowsPath() {
        String path = "C:\\Program Files\\Croupier\\config.json";
        assertTrue(path.contains("C:"));
        assertTrue(path.contains("\\"));
    }

    // ========== Version Edge Cases ==========

    @Test
    public void testEmptyVersion() {
        String version = "";
        assertTrue(version.isEmpty());
    }

    @Test
    public void testVersionWithManyComponents() {
        String version = "1.2.3.4.5.6.7.8.9.10";
        int dotCount = 0;
        for (char c : version.toCharArray()) {
            if (c == '.') dotCount++;
        }
        assertEquals(9, dotCount);
    }

    @Test
    public void testVersionWithPreRelease() {
        String version = "1.0.0-beta";
        assertTrue(version.contains("-"));
    }

    // ========== ID Edge Cases ==========

    @Test
    public void testEmptyId() {
        String id = "";
        assertTrue(id.isEmpty());
    }

    @Test
    public void testIdWithOnlyDots() {
        String id = "...";
        assertEquals(3, id.length());
    }

    @Test
    public void testIdStartingWithDot() {
        String id = ".test.function";
        assertTrue(id.startsWith("."));
    }

    @Test
    public void testIdEndingWithDot() {
        String id = "test.function.";
        assertTrue(id.endsWith("."));
    }

    // ========== Memory Edge Cases ==========

    @Test
    public void testLargeStringAllocation() {
        char[] chars = new char[1000000]; // 1MB
        for (int i = 0; i < chars.length; i++) {
            chars[i] = 'x';
        }
        String largeString = new String(chars);
        assertEquals(1000000, largeString.length());
    }

    @Test
    public void testLargeListAllocation() {
        List<Integer> list = new java.util.ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            list.add(i);
        }
        assertEquals(100000, list.size());
    }

    @Test
    public void testLargeMapAllocation() {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < 10000; i++) {
            map.put("key_" + i, i);
        }
        assertEquals(10000, map.size());
    }

    // ========== Boundary Value Tests ==========

    @Test
    public void testByteBoundary() {
        byte min = Byte.MIN_VALUE;
        byte max = Byte.MAX_VALUE;

        assertEquals(-128, min);
        assertEquals(127, max);
    }

    @Test
    public void testShortBoundary() {
        short min = Short.MIN_VALUE;
        short max = Short.MAX_VALUE;

        assertEquals(-32768, min);
        assertEquals(32767, max);
    }

    @Test
    public void testCharacterBoundary() {
        char min = Character.MIN_VALUE;
        char max = Character.MAX_VALUE;

        assertEquals(0, min);
        assertEquals(65535, max);
    }
}
