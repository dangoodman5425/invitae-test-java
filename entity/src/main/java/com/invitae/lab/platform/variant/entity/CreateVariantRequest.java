package com.invitae.lab.platform.variant.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = CreateVariantRequest.Builder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateVariantRequest {
    private final String gene;
    private final String nucleotideChange;
    private final String proteinChange;

    @JsonIgnoreProperties(ignoreUnknown = true)
    private CreateVariantRequest(final Builder builder) {
        gene = builder.gene;
        nucleotideChange = builder.nucleotideChange;
        proteinChange = builder.proteinChange;
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

    public static final class Builder {
        private String gene;
        private String nucleotideChange;
        private String proteinChange;

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

        public CreateVariantRequest build() {
            return new CreateVariantRequest(this);
        }
    }
}
