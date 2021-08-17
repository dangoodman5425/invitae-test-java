package com.invitae.lab.platform.variant.api.configuration;

import com.github.daniel.shuy.kafka.protobuf.serde.KafkaProtobufSerializer;
import com.invitae.lab.platform.variant.entity.protobuf.Events;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public ProducerFactory<String, Events.VariantEvent> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
//        configProps.put(
//                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
//                StringSerializer.class);
//        configProps.put(
//                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
//                new KafkaProtobufSerializer<>());
        return new DefaultKafkaProducerFactory<>(configProps, new StringSerializer(), new KafkaProtobufSerializer<>());
    }

    @Bean
    public KafkaTemplate<String, Events.VariantEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
