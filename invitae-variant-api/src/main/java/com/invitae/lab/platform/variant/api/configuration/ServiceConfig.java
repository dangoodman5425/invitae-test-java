package com.invitae.lab.platform.variant.api.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invitae.lab.platform.variant.entity.jackson.ObjectMapperSingleton;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.modelmapper.convention.NameTransformers;
import org.modelmapper.convention.NamingConventions;
import org.modelmapper.jooq.RecordValueReader;
import org.modelmapper.module.jsr310.Jsr310Module;
import org.modelmapper.module.jsr310.Jsr310ModuleConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Configuration
public class ServiceConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return ObjectMapperSingleton.INSTANCE;
    }

    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper mapper = new ModelMapper();

        final Jsr310ModuleConfig config = Jsr310ModuleConfig.builder()
                .dateTimeFormatter(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                .dateFormatter(DateTimeFormatter.ISO_LOCAL_DATE)
                .zoneId(ZoneOffset.UTC)
                .build();
        mapper.registerModule(new Jsr310Module(config));

        mapper.getConfiguration()
                // Allows mapper to convert snake_case column names to camelCase attributes
                .setSourceNameTokenizer(NameTokenizers.UNDERSCORE)
                // Configures model mapper to read jOOQ records
                .addValueReader(new RecordValueReader())
                // Indicates the builder methods are prefixed with '.with'
                .setDestinationNameTransformer(NameTransformers.builder("with"))
                .setDestinationNamingConvention(NamingConventions.builder("with"))
                .setPropertyCondition(Conditions.isNotNull());
        return mapper;
    }
}
