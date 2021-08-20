package com.invitae.lab.platform.variant.api;

import com.invitae.lab.platform.variant.entity.CreateVariantRequest;
import com.invitae.lab.platform.variant.entity.Variant;
import com.invitae.lab.platform.variant.kafka.protobuf.Events;
import com.invitae.lab.platform.variant.schema.jooq.tables.records.VariantRecord;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

public final class Fixtures {

    private Fixtures() {

    }

    private static final Instant TIMESTAMP = Instant.now();
    public static final String VARIANT_ID_STRING = "bb3d291c-02a1-41dd-809d-c33be465bd2f";
    public static final UUID VARIANT_ID = UUID.fromString("bb3d291c-02a1-41dd-809d-c33be465bd2f");
    public static final Variant VARIANT = Variant.newBuilder()
            .withVariantId(VARIANT_ID_STRING)
            .withGene("test_gene")
            .withProteinChange("protein_change_1")
            .withNucleotideChange("nucleotide_change_1")
            .withLastEvaluated(LocalDate.parse("2021-08-17"))
            .build();
    public static final CreateVariantRequest CREATE_VARIANT_REQUEST = CreateVariantRequest.newBuilder()
            .withGene("test_gene")
            .withProteinChange("protein_change_1")
            .withNucleotideChange("nucleotide_change_1")
            .withLastEvaluated(LocalDate.parse("2021-08-17"))
            .build();
    public static final VariantRecord VARIANT_RECORD = new VariantRecord()
            .setVariantId(VARIANT_ID)
            .setGene("test_gene")
            .setProteinChange("protein_change_1")
            .setNucleotideChange("nucleotide_change_1")
            .setLastEvaluated(LocalDate.parse("2021-08-17"))
            .setCreated(LocalDateTime.ofInstant(TIMESTAMP, ZoneOffset.UTC));
    public static final VariantRecord CREATE_VARIANT_RECORD = new VariantRecord()
            .setGene("test_gene")
            .setProteinChange("protein_change_1")
            .setNucleotideChange("nucleotide_change_1")
            .setLastEvaluated(LocalDate.parse("2021-08-17"));
    public static final Events.VariantEvent VARIANT_EVENT = Events.VariantEvent.newBuilder()
            .setId(VARIANT_ID_STRING)
            .setGene("test_gene")
            .setProteinChange("protein_change_1")
            .setNucleotideChange("nucleotide_change_1")
            .setLastEvaluated(20210817)
            .setCreated(Math.toIntExact(TIMESTAMP.getEpochSecond()))
            .build();
    public static final String TEST_TOPIC = "test-topic";
}
