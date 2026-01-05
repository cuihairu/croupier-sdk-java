package io.github.cuihairu.croupier.sdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class FunctionDescriptorTest {

    @Test
    void constructorWithIdAndVersion() {
        FunctionDescriptor desc = new FunctionDescriptor("func1", "1.0.0");

        assertEquals("func1", desc.getId());
        assertEquals("1.0.0", desc.getVersion());
        assertNull(desc.getName());
        assertNull(desc.getDescription());
    }

    @Test
    void fullConstructor() {
        FunctionDescriptor desc = new FunctionDescriptor("func2", "2.0.0", "Test Function", "A test function");

        assertEquals("func2", desc.getId());
        assertEquals("2.0.0", desc.getVersion());
        assertEquals("Test Function", desc.getName());
        assertEquals("A test function", desc.getDescription());
    }

    @Test
    void settersWork() {
        FunctionDescriptor desc = new FunctionDescriptor("f", "1.0");

        desc.setId("new-f");
        assertEquals("new-f", desc.getId());

        desc.setVersion("2.0");
        assertEquals("2.0", desc.getVersion());

        desc.setName("New Name");
        assertEquals("New Name", desc.getName());

        desc.setDescription("New Description");
        assertEquals("New Description", desc.getDescription());
    }

    @Test
    void equalsReturnsTrueForSameObject() {
        FunctionDescriptor desc = new FunctionDescriptor("f", "1.0");
        assertTrue(desc.equals(desc));
    }

    @Test
    void equalsReturnsFalseForNull() {
        FunctionDescriptor desc = new FunctionDescriptor("f", "1.0");
        assertFalse(desc.equals(null));
    }

    @Test
    void equalsReturnsFalseForDifferentClass() {
        FunctionDescriptor desc = new FunctionDescriptor("f", "1.0");
        assertFalse(desc.equals("not a descriptor"));
    }

    @Test
    void hashCodeIsConsistent() {
        FunctionDescriptor desc1 = new FunctionDescriptor("f", "1.0");
        FunctionDescriptor desc2 = new FunctionDescriptor("f", "1.0");

        assertEquals(desc1.hashCode(), desc2.hashCode());
    }

    @Test
    void toStringContainsIdAndVersion() {
        FunctionDescriptor desc = new FunctionDescriptor("testFunc", "3.0.0");
        String str = desc.toString();

        assertTrue(str.contains("testFunc"));
        assertTrue(str.contains("3.0.0"));
    }

    @Test
    void builderCreatesValidDescriptor() {
        FunctionDescriptor desc = FunctionDescriptor.builder()
            .id("builder-func")
            .version("1.0.0")
            .name("Builder Function")
            .description("Built with builder")
            .build();

        assertEquals("builder-func", desc.getId());
        assertEquals("1.0.0", desc.getVersion());
        assertEquals("Builder Function", desc.getName());
        assertEquals("Built with builder", desc.getDescription());
    }
}
