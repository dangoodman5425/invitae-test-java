package com.invitae.lab.platform.variant.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invitae.lab.platform.variant.api.service.VariantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.invitae.lab.platform.variant.api.Fixtures.CREATE_VARIANT_REQUEST;
import static com.invitae.lab.platform.variant.api.Fixtures.VARIANT;
import static com.invitae.lab.platform.variant.api.Fixtures.VARIANT_ID;
import static com.invitae.lab.platform.variant.api.Fixtures.VARIANT_ID_STRING;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for {@link VariantController}
 *
 * @author dgoodman
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers={VariantController.class})
class VariantControllerTest {
    private static final String BASE_URL = "/api/v1/variants";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private VariantService variantService;

    @Test
    void testGetVariant() throws Exception {
        // Variant found (Status Code: 200 with valid response body)
        doReturn(Optional.of(VARIANT)).when(variantService).getVariant(VARIANT_ID);
        mockMvc.perform(get(String.join("/", BASE_URL, VARIANT_ID_STRING)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(VARIANT)));
        // Variant not found (Status Code: 404)
        mockMvc.perform(get(String.join("/", BASE_URL, UUID.randomUUID().toString())))
                .andDo(print())
                .andExpect(status().isNotFound());
        // Invalid UUID (Status Code: 400)
        mockMvc.perform(get(String.join("/", BASE_URL, "not-a-uuid")))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateVariant() throws Exception {
        // Accepted request
        doReturn(Optional.of(VARIANT)).when(variantService).createVariant(CREATE_VARIANT_REQUEST);
        mockMvc.perform(
                post(BASE_URL)
                        .content(objectMapper.writeValueAsString(CREATE_VARIANT_REQUEST))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().string(objectMapper.writeValueAsString(VARIANT)));
        // Duplicate/error in creating object
        doReturn(Optional.empty()).when(variantService).createVariant(CREATE_VARIANT_REQUEST);
        mockMvc.perform(
                post(BASE_URL)
                        .content(objectMapper.writeValueAsString(CREATE_VARIANT_REQUEST))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
        // Malformed request
        mockMvc.perform(
                post(BASE_URL)
                        .content(objectMapper.writeValueAsString(Map.of("malformed", "request")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
