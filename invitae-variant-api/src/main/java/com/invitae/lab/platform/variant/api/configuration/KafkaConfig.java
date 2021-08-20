package com.invitae.lab.platform.variant.api.configuration;

import com.github.daniel.shuy.kafka.protobuf.serde.KafkaProtobufSerializer;
import com.invitae.lab.platform.variant.kafka.protobuf.Events;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

/**
 * Application configuration for Kafka template used by Variant API for persisting new variants
 *
 * @author dgoodman
 */
@Configuration
@ComponentScan("com.invitae.lab.platform.variant.kafka")
public class KafkaConfig {
    @Value("${kafka.bootstrap.servers}")
    private String kafkaBootstrapServers;
    @Value("${kafka.topic}")
    private String topic;

    @Bean(name = "topic")
    public String getTopic() {
        return topic;
    }

    @Bean
    public ProducerFactory<String, Events.VariantEvent> producerFactory() {
        Map<String, Object> configProps = Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaBootstrapServers
        );
        return new DefaultKafkaProducerFactory<>(configProps, new StringSerializer(), new KafkaProtobufSerializer<>());
    }

    @Bean
    public KafkaTemplate<String, Events.VariantEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
