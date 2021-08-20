package com.invitae.lab.platform.variant.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;

import java.time.LocalDate;
import java.util.Objects;

/**
 * POJO used for making a create variant request
 *
 * @author dgoodman
 */
@JsonDeserialize(builder = CreateVariantRequest.Builder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateVariantRequest {
    private final String gene;
    private final String nucleotideChange;
    private final String proteinChange;
    private final LocalDate lastEvaluated;

    @JsonIgnoreProperties(ignoreUnknown = true)
    private CreateVariantRequest(final Builder builder) {
        gene = builder.gene;
        nucleotideChange = builder.nucleotideChange;
        proteinChange = builder.proteinChange;
        lastEvaluated = builder.lastEvaluated;
    }

    public static Builder newBuilder() {
        return new Builder();
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
        final CreateVariantRequest that = (CreateVariantRequest) o;
        return gene.equals(that.gene) &&
                nucleotideChange.equals(that.nucleotideChange) &&
                proteinChange.equals(that.proteinChange) &&
                lastEvaluated.equals(that.lastEvaluated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gene, nucleotideChange, proteinChange, lastEvaluated);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("gene", gene)
                .add("nucleotideChange", nucleotideChange)
                .add("proteinChange", proteinChange)
                .add("lastEvaluated", lastEvaluated)
                .toString();
    }

    public static final class Builder {
        private String gene;
        private String nucleotideChange;
        private String proteinChange;
        private LocalDate lastEvaluated;

        private Builder() {
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

        public CreateVariantRequest build() {
            Objects.requireNonNull(gene);
            Objects.requireNonNull(nucleotideChange);
            Objects.requireNonNull(proteinChange);
            Objects.requireNonNull(lastEvaluated);
            return new CreateVariantRequest(this);
        }
    }
}
