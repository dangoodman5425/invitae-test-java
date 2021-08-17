package com.invitae.lab.platform.variant.store;


import com.invitae.lab.platform.variant.schema.jooq.tables.records.VariantRecord;

import java.util.Optional;
import java.util.UUID;

public interface VariantRepository {

    Optional<VariantRecord> getVariantById(final UUID variantId);

    Optional<VariantRecord> createVariant(final VariantRecord record);
}
