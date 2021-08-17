package com.invitae.lab.platform.variant.entity.jackson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * {@link ObjectMapper} which is used for the serialization/deserialization of the core entities
 *
 * @author dgoodman
 */
public class ObjectMapperSingleton {
    public static final ObjectMapper INSTANCE;

    static {
        final UnmodifiedObjectMapper objectMapper = new UnmodifiedObjectMapper();
        objectMapper.registerModules(new JavaTimeModule(), new Jdk8Module());
        objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        objectMapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);

        objectMapper.seal();

        INSTANCE = objectMapper;
    }

    private static class UnmodifiedObjectMapper extends ObjectMapper {
        private final AtomicBoolean sealed;

        UnmodifiedObjectMapper() {
            sealed = new AtomicBoolean(false);
        }

        @Override
        public ObjectMapper registerModule(final Module module) {
            if (sealed.get()) {
                throw new UnsupportedOperationException();
            }
            return super.registerModule(module);
        }

        void seal() {
            sealed.set(true);
        }
    }
}
