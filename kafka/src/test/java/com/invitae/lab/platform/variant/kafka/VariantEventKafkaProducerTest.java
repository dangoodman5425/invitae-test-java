package com.invitae.lab.platform.variant.kafka;

import com.google.common.io.Resources;
import com.invitae.lab.platform.variant.kafka.protobuf.Events;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link VariantEventKafkaProducer}
 *
 * @author dgoodman
 */
@ExtendWith(MockitoExtension.class)
class VariantEventKafkaProducerTest {

    private static final String TEST_TOPIC = "test-topic";

    @Mock
    private KafkaTemplate<String, Events.VariantEvent> kafkaTemplate;
    private VariantEventKafkaProducer producer;

    @BeforeEach
    void init() {
        producer = new VariantEventKafkaProducer(kafkaTemplate);
    }

    @Test
    void test() throws IOException {
        final Events.VariantEvent variantEvent = loadProtobufFromFile("fixtures/testProto.bin");
        producer.persist(TEST_TOPIC, variantEvent);
        verify(kafkaTemplate, times(1)).send(TEST_TOPIC, variantEvent);
    }

    private static Events.VariantEvent loadProtobufFromFile(final String path) throws IOException {
        try (final InputStream input = Resources.getResource(path).openStream()) {
            return Events.VariantEvent.parseDelimitedFrom(input);
        }
    }
}
