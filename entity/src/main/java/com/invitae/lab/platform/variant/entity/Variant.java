package com.invitae.lab.platform.variant.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * POJO representing a Variant entity
 *
 * @author dgoodman
 */
@JsonDeserialize(builder = Variant.Builder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Variant {
    private final UUID variantId;
    private final String gene;
    private final String nucleotideChange;
    private final String proteinChange;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final LocalDate lastEvaluated;

    private Variant(final Builder builder) {
        variantId = builder.variantId;
        gene = builder.gene;
        nucleotideChange = builder.nucleotideChange;
        proteinChange = builder.proteinChange;
        lastEvaluated = builder.lastEvaluated;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public UUID getVariantId() {
        return variantId;
    }

    public String getGene() {
        return gene;
    }

    public String getNucleotideChange() {
        return nucleotideChange;
    }

    public String getProteinChange() {
        return proteinChange;
    }

    public LocalDate getLastEvaluated() {
        return lastEvaluated;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Variant variant = (Variant) o;
        return variantId.equals(variant.variantId) &&
                gene.equals(variant.gene) &&
                nucleotideChange.equals(variant.nucleotideChange) &&
                proteinChange.equals(variant.proteinChange) &&
                lastEvaluated.equals(variant.lastEvaluated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(variantId, gene, nucleotideChange, proteinChange, lastEvaluated);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("variantId", variantId)
                .add("gene", gene)
                .add("nucleotideChange", nucleotideChange)
                .add("proteinChange", proteinChange)
                .add("lastEvaluated", lastEvaluated)
                .toString();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder {
        private UUID variantId;
        private String gene;
        private String nucleotideChange;
        private String proteinChange;
        private LocalDate lastEvaluated;

        private Builder() {
        }

        public Builder withVariantId(final String val) {
            variantId = UUID.fromString(Objects.requireNonNull(val));
            return this;
        }

        public Builder withGene(final String val) {
            gene = val;
            return this;
        }

        public Builder withNucleotideChange(final String val) {
            nucleotideChange = val;
            return this;
        }

        public Builder withProteinChange(final String val) {
            proteinChange = val;
            return this;
        }

        public Builder withLastEvaluated(final LocalDate val) {
            lastEvaluated = val;
            return this;
        }

        public Variant build() {
            Objects.requireNonNull(gene);
            Objects.requireNonNull(nucleotideChange);
            Objects.requireNonNull(proteinChange);
            Objects.requireNonNull(lastEvaluated);
            return new Variant(this);
        }
    }
}
