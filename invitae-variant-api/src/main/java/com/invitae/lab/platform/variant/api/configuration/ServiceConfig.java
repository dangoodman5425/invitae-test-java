package com.invitae.lab.platform.variant.api.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invitae.lab.platform.variant.api.mapper.ModelMapperSingleton;
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

/**
 * Primary service-level configuration to instantiate essential beans used by the Variant API
 *
 * @author dgoodman
 */
@Configuration
public class ServiceConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return ObjectMapperSingleton.INSTANCE;
    }

    @Bean
    public ModelMapper modelMapper() {
        return ModelMapperSingleton.INSTANCE;
    }
}
