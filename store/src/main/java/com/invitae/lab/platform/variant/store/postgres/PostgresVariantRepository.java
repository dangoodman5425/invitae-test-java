package com.invitae.lab.platform.variant.store.postgres;

import com.invitae.lab.platform.variant.schema.jooq.tables.records.VariantRecord;
import com.invitae.lab.platform.variant.store.VariantRepository;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static com.invitae.lab.platform.variant.schema.jooq.tables.Variant.VARIANT;

@Repository
public class PostgresVariantRepository implements VariantRepository {

    private final DSLContext dsl;

    public PostgresVariantRepository(final DSLContext dsl) {
        this.dsl = dsl;
    }

    public Optional<VariantRecord> getVariantById(final UUID variantId) {
        return dsl.selectFrom(VARIANT)
                .where(VARIANT.VARIANT_ID.eq(variantId))
                .fetchOptional();
    }

    public Optional<VariantRecord> createVariant(final VariantRecord record) {
        return dsl.insertInto(VARIANT)
                .set(record)
                .onDuplicateKeyIgnore()
                .returning()
                .fetchOptional();
    }
}
