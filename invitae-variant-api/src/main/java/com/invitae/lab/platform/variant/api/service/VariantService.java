package com.invitae.lab.platform.variant.api.service;

import com.invitae.lab.platform.variant.entity.CreateVariantRequest;
import com.invitae.lab.platform.variant.entity.Variant;
import com.invitae.lab.platform.variant.kafka.VariantEventKafkaProducer;
import com.invitae.lab.platform.variant.kafka.protobuf.Events;
import com.invitae.lab.platform.variant.schema.jooq.tables.records.VariantRecord;
import com.invitae.lab.platform.variant.store.VariantRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

/**
 * Application {@link Service} layer containing business logic for interfacing with a {@link VariantRepository}
 * and {@link VariantEventKafkaProducer}
 *
 * @author dgoodman
 */
@Service
public class VariantService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VariantService.class);

    private final VariantRepository repository;
    private final ModelMapper modelMapper;
    private final VariantEventKafkaProducer producer;
    private final String topic;

    @Autowired
    public VariantService(final VariantRepository repository,
                          final ModelMapper modelMapper,
                          final VariantEventKafkaProducer producer,
                          final String topic) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.producer = producer;
        this.topic = topic;
    }

    /**
     * Retrieves a {@link Variant} for the database if it exists
     *
     * @param variantId UUID for variant to retrieve from the database
     * @return Optional of {@link Variant}; Optional#empty if none exists
     */
    public Optional<Variant> getVariant(final UUID variantId) {
        final Optional<VariantRecord> variantRecord = repository.getVariantById(variantId);
        if (variantRecord.isEmpty()) {
            LOGGER.info("Unable to find variant record with ID {}", variantId);
            return Optional.empty();
        }
        return variantRecord.map(r -> modelMapper.map(r, Variant.Builder.class).build());
    }

    /**
     * Creates a {@link VariantRecord} in the database and persists it to Kafka as a {@link Events.VariantEvent}
     *
     * @param request {@link CreateVariantRequest} POJO containing attributes necessary for creating a variant
     * @return Optional of the {@link Variant} created; Optional#empty if unable to create
     */
    public Optional<Variant> createVariant(final CreateVariantRequest request) {
        final VariantRecord newRecord = new VariantRecord()
                .setGene(request.getGene())
                .setNucleotideChange(request.getNucleotideChange())
                .setProteinChange(request.getProteinChange())
                .setLastEvaluated(request.getLastEvaluated());
        final Optional<VariantRecord> variantRecordOptional = repository.createVariant(newRecord);
        if (variantRecordOptional.isPresent()) {
            final VariantRecord record = variantRecordOptional.get();
            final int formattedLastEvaluated = Integer.parseInt(
                    DateTimeFormatter.BASIC_ISO_DATE.format(record.getLastEvaluated()));
            final Events.VariantEvent variantEvent = Events.VariantEvent.newBuilder()
                    .setId(record.getVariantId().toString())
                    .setGene(record.getGene())
                    .setNucleotideChange(record.getNucleotideChange())
                    .setProteinChange(record.getProteinChange())
                    .setLastEvaluated(formattedLastEvaluated)
                    .setCreated(Math.toIntExact(record.getCreated().toEpochSecond(ZoneOffset.UTC)))
                    .build();
            producer.persist(topic, variantEvent);
        }
        return variantRecordOptional.map(r -> modelMapper.map(r, Variant.Builder.class).build());
    }
}
