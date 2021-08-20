package com.invitae.lab.platform.variant.store;


import com.invitae.lab.platform.variant.schema.jooq.tables.records.VariantRecord;

import java.util.Optional;
import java.util.UUID;

/**
 * Interface which defines operations that perform CRUD operations against a variant data store
 *
 * @author dgoodman
 */
public interface VariantRepository {

    /**
     * Retrieves a variant from a data store by its ID
     *
     * @param variantId UUID representing the variant to retrieve
     * @return Optional of a {@link VariantRecord}
     */
    Optional<VariantRecord> getVariantById(final UUID variantId);

    /**
     * Creates a {@link VariantRecord} in a data store
     *
     * @param record {@link VariantRecord} to create
     * @return Optional of a {@link VariantRecord}
     */
    Optional<VariantRecord> createVariant(final VariantRecord record);
}
