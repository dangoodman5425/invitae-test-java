package com.invitae.lab.platform.variant.entity;

import com.invitae.lab.platform.variant.entity.jackson.ObjectMapperSingleton;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.invitae.lab.platform.variant.entity.Fixtures.VARIANT;
import static junit.framework.TestCase.assertEquals;

/**
 * Tests for {@link Variant}
 *
 * @author dgoodman
 */
class VariantTest {

    @Test
    void test() throws IOException {
        final Variant variant = ObjectMapperSingleton.INSTANCE.readValue(
                FileUtils.getFileAsString("fixtures/variant.json"),
                Variant.class);
        assertEquals(VARIANT, variant);
        // Serialize and deserialize
        assertEquals(VARIANT, ObjectMapperSingleton.INSTANCE.readValue(
                ObjectMapperSingleton.INSTANCE.writeValueAsString(variant), Variant.class));
    }
}
