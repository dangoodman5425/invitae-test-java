package com.invitae.lab.platform.variant.api.service;

import com.invitae.lab.platform.variant.api.mapper.ModelMapperSingleton;
import com.invitae.lab.platform.variant.entity.Variant;
import com.invitae.lab.platform.variant.kafka.VariantEventKafkaProducer;
import com.invitae.lab.platform.variant.store.VariantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.invitae.lab.platform.variant.api.Fixtures.CREATE_VARIANT_RECORD;
import static com.invitae.lab.platform.variant.api.Fixtures.CREATE_VARIANT_REQUEST;
import static com.invitae.lab.platform.variant.api.Fixtures.TEST_TOPIC;
import static com.invitae.lab.platform.variant.api.Fixtures.VARIANT;
import static com.invitae.lab.platform.variant.api.Fixtures.VARIANT_EVENT;
import static com.invitae.lab.platform.variant.api.Fixtures.VARIANT_ID;
import static com.invitae.lab.platform.variant.api.Fixtures.VARIANT_RECORD;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;


/**
 * Tests for {@link VariantService}
 *
 * @author dgoodman
 */
@ExtendWith(MockitoExtension.class)
class VariantServiceTest {

    private VariantService service;

    @Mock
    private VariantRepository repository;
    @Mock
    private VariantEventKafkaProducer producer;

    @BeforeEach
    void init() {
        service = new VariantService(repository, ModelMapperSingleton.INSTANCE, producer, TEST_TOPIC);
    }

    @Test
    void testGetVariantById() {
        // Entity is found and mapped to correct POJO
        doReturn(Optional.of(VARIANT_RECORD)).when(repository).getVariantById(VARIANT_ID);
        assertEquals(VARIANT, service.getVariant(VARIANT_ID).get());
        // No entity with ID exists
        doReturn(Optional.empty()).when(repository).getVariantById(VARIANT_ID);
        assertTrue(service.getVariant(VARIANT_ID).isEmpty());
    }

    @Test
    void testCreateVariant() {
        // Entity failed to be created and Kafka persister not called
        assertTrue(service.getVariant(VARIANT_ID).isEmpty());
        verifyNoInteractions(producer);
        // Entity is created in data store and persisted to Kafka (have to use any() matcher because jOOQ record
        // does not implement #equals()
        doReturn(Optional.of(VARIANT_RECORD)).when(repository).createVariant(any());
        final Optional<Variant> variant = service.createVariant(CREATE_VARIANT_REQUEST);
        verify(producer, times(1)).persist(TEST_TOPIC, VARIANT_EVENT);
        assertTrue(variant.isPresent());
        assertEquals(VARIANT, variant.get());
    }
}
