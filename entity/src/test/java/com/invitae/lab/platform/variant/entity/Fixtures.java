package com.invitae.lab.platform.variant.entity;

import java.time.LocalDate;
import java.util.UUID;

public final class Fixtures {

    private Fixtures() {

    }

    public static final String VARIANT_ID_STRING = "bb3d291c-02a1-41dd-809d-c33be465bd2f";
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
}
