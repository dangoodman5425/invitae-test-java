package com.invitae.lab.platform.variant.entity;

import com.invitae.lab.platform.variant.entity.jackson.ObjectMapperSingleton;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.invitae.lab.platform.variant.entity.Fixtures.CREATE_VARIANT_REQUEST;
import static junit.framework.TestCase.assertEquals;

/**
 * Tests for {@link CreateVariantRequest}
 *
 * @author dgoodman
 */
class CreateVariantRequestTest {

    @Test
    void test() throws IOException {
        final CreateVariantRequest request = ObjectMapperSingleton.INSTANCE.readValue(
                FileUtils.getFileAsString("fixtures/create-variant-request.json"),
                CreateVariantRequest.class);
        assertEquals(CREATE_VARIANT_REQUEST, request);
        // Serialize and deserialize
        assertEquals(CREATE_VARIANT_REQUEST, ObjectMapperSingleton.INSTANCE.readValue(
                ObjectMapperSingleton.INSTANCE.writeValueAsString(request), CreateVariantRequest.class));
    }
}
