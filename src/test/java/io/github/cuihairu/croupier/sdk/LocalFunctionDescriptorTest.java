package io.github.cuihairu.croupier.sdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class LocalFunctionDescriptorTest {

    @Test
    void constructorWithIdAndVersion() {
        LocalFunctionDescriptor desc = new LocalFunctionDescriptor("local-func", "1.0.0");

        assertEquals("local-func", desc.getId());
        assertEquals("1.0.0", desc.getVersion());
        assertNull(desc.getName());
        assertNull(desc.getDescription());
    }

    @Test
    void fullConstructor() {
        LocalFunctionDescriptor desc = new LocalFunctionDescriptor(
            "local-func2",
            "2.0.0",
            "Local Function",
            "A local function description"
        );

        assertEquals("local-func2", desc.getId());
        assertEquals("2.0.0", desc.getVersion());
        assertEquals("Local Function", desc.getName());
        assertEquals("A local function description", desc.getDescription());
    }

    @Test
    void settersWork() {
        LocalFunctionDescriptor desc = new LocalFunctionDescriptor("f", "1.0");

        desc.setId("updated-f");
        assertEquals("updated-f", desc.getId());

        desc.setVersion("3.0.0");
        assertEquals("3.0.0", desc.getVersion());

        desc.setName("Updated Name");
        assertEquals("Updated Name", desc.getName());

        desc.setDescription("Updated Description");
        assertEquals("Updated Description", desc.getDescription());
    }

    @Test
    void toStringContainsId() {
        LocalFunctionDescriptor desc = new LocalFunctionDescriptor("test-local", "1.0.0");
        String str = desc.toString();

        assertTrue(str.contains("test-local"));
    }

    @Test
    void canExtendLocalFunctionDescriptor() {
        class CustomDescriptor extends LocalFunctionDescriptor {
            private String customField;

            CustomDescriptor(String id, String version) {
                super(id, version);
            }

            void setCustomField(String value) {
                customField = value;
            }

            String getCustomField() {
                return customField;
            }
        }

        CustomDescriptor custom = new CustomDescriptor("custom", "1.0");
        custom.setCustomField("custom-value");
        assertEquals("custom-value", custom.getCustomField());
        assertEquals("custom", custom.getId());
    }
}
