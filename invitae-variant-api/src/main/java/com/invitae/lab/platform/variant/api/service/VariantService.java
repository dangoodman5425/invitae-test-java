package com.invitae.lab.platform.variant.api.service;

import com.invitae.lab.platform.variant.entity.CreateVariantRequest;
import com.invitae.lab.platform.variant.entity.Variant;
import com.invitae.lab.platform.variant.entity.protobuf.Events;
import com.invitae.lab.platform.variant.schema.jooq.tables.records.VariantRecord;
import com.invitae.lab.platform.variant.store.VariantRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
public class VariantService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VariantService.class);

    private final VariantRepository repository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<String, Events.VariantEvent> kafkaTemplate;

    @Autowired
    public VariantService(final VariantRepository repository,
                          final ModelMapper modelMapper,
                          final KafkaTemplate<String, Events.VariantEvent> kafkaTemplate) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Optional<Variant> getVariant(final UUID variantId) {
        final Optional<VariantRecord> variantRecord = repository.getVariantById(variantId);
        if (variantRecord.isEmpty()) {
            // Orchestrate
            return Optional.empty();
        }
        return variantRecord.map(r -> modelMapper.map(r, Variant.Builder.class).build());
    }

    public void createVariant(final CreateVariantRequest request) {
        final VariantRecord newRecord = new VariantRecord()
                .setGene(request.getGene())
                .setNucleotideChange(request.getNucleotideChange())
                .setProteinChange(request.getProteinChange());
        final Optional<VariantRecord> variantRecordOptional = repository.createVariant(newRecord);
        if (variantRecordOptional.isPresent()) {
            final VariantRecord record = variantRecordOptional.get();
            final UUID variantId = record.getVariantId();
            final int formattedLastEvaluated = Integer.parseInt(
                    DateTimeFormatter.BASIC_ISO_DATE.format(record.getLastEvaluated()));
            final Events.VariantEvent variantEvent = Events.VariantEvent.newBuilder()
                    .setId(record.getVariantId().toString())
                    .setGene(record.getGene())
                    .setNucleotideChange(record.getNucleotideChange())
                    .setProteinChange(record.getProteinChange())
                    .setLastEvaluated(formattedLastEvaluated)
                    .build();
            kafkaTemplate.send("test-topic", variantEvent);
            LOGGER.debug("Successfully persisted {} to test-topic", variantId);
        }
    }
}
