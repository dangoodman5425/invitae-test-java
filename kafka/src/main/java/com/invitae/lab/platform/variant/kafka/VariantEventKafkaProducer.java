package com.invitae.lab.platform.variant.kafka;

import com.invitae.lab.platform.variant.kafka.protobuf.Events;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Responsible for persisting {@link Events.VariantEvent} protobuf to a Kafka topic
 *
 * @author dgoodman
 */
@Component
public class VariantEventKafkaProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(VariantEventKafkaProducer.class);

    private final KafkaTemplate<String, Events.VariantEvent> kafkaTemplate;

    @Autowired
    public VariantEventKafkaProducer(final KafkaTemplate<String, Events.VariantEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Attempts to persist a {@link Events.VariantEvent} protobuf to a topic
     *
     * @param topic to send message to
     * @param event {@link Events.VariantEvent} to be persisted
     */
    public void persist(final String topic, final Events.VariantEvent event) {
        final String id = event.getId();
        LOGGER.info("Sending event {} to topic {}", id, topic);
        try {
            kafkaTemplate.send(topic, event);
            LOGGER.info("Successfully persisted event {} to topic {}", id, topic);
        } catch (final Exception e) {
            LOGGER.warn("Unable to persist event {} to topic {}", id, topic, e);
        }
    }
}
