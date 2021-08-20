package com.invitae.lab.platform.variant.api.mapper;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.Module;
import org.modelmapper.convention.NameTokenizers;
import org.modelmapper.convention.NameTransformers;
import org.modelmapper.convention.NamingConventions;
import org.modelmapper.jooq.RecordValueReader;
import org.modelmapper.module.jsr310.Jsr310Module;
import org.modelmapper.module.jsr310.Jsr310ModuleConfig;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicBoolean;

public class ModelMapperSingleton {
    public static final ModelMapper INSTANCE;

    static {
        final UnmodifiableModelMapper mapper = new UnmodifiableModelMapper();

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
        mapper.seal();
        INSTANCE = mapper;
    }

    private static class UnmodifiableModelMapper extends ModelMapper {
        private final AtomicBoolean sealed;

        UnmodifiableModelMapper() {
            sealed = new AtomicBoolean(false);
        }

        @Override
        public ModelMapper registerModule(final Module module) {
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
