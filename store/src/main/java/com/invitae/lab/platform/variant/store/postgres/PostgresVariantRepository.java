package com.invitae.lab.platform.variant.store.postgres;

import com.invitae.lab.platform.variant.schema.jooq.tables.records.VariantRecord;
import com.invitae.lab.platform.variant.store.VariantRepository;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static com.invitae.lab.platform.variant.schema.jooq.tables.Variant.VARIANT;

/**
 * Implementation of a {@link VariantRepository} for a Postgres data store
 *
 * @author dgoodman
 */
@Repository
public class PostgresVariantRepository implements VariantRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostgresVariantRepository.class);

    private final DSLContext dsl;

    public PostgresVariantRepository(final DSLContext dsl) {
        this.dsl = dsl;
    }

    /**
     * Retrieves a variant from Postgres by its ID
     *
     * @param variantId UUID representing the variant to retrieve
     * @return Optional of a {@link VariantRecord}; Optional#empty if none exists
     */
    public Optional<VariantRecord> getVariantById(final UUID variantId) {
        return dsl.selectFrom(VARIANT)
                .where(VARIANT.VARIANT_ID.eq(variantId))
                .fetchOptional();
    }

    /**
     * Creates a {@link VariantRecord} in Postgres
     * On duplicate no insertion will occur and Optional#empty will be returned
     *
     * @param record {@link VariantRecord} to create
     * @return Optional of a {@link VariantRecord}; Optional#empty if duplicate record
     */
    public Optional<VariantRecord> createVariant(final VariantRecord record) {
        final Optional<VariantRecord> variantRecord =  dsl.insertInto(VARIANT)
                .set(record)
                // This will return Optional#empty if duplicate key exists
                .onDuplicateKeyIgnore()
                .returning()
                .fetchOptional();
        if (variantRecord.isEmpty()) {
            LOGGER.debug("Variant {} already exists in table variant", record);
        } else {
            LOGGER.info("Successfully added entity to table variant");
        }
        return variantRecord;

    }
}
