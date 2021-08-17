package com.invitae.lab.platform.variant.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

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
            variantId = UUID.fromString(val);
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

        public Builder withLastEvaluated(final LocalDateTime val) {
            lastEvaluated = val.toLocalDate();
            return this;
        }

        public Variant build() {
            return new Variant(this);
        }
    }
}
