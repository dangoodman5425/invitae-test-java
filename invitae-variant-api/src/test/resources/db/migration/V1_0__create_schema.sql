CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


CREATE TABLE IF NOT EXISTS variant (
    variant_id           UUID            DEFAULT uuid_generate_v4(),
    gene                 VARCHAR(1024)   NOT NULL,
    nucleotide_change    TEXT            NOT NULL,
    protein_change       TEXT            NOT NULL,
    last_evaluated       DATE            NOT NULL,
    created              TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (variant_id),
    UNIQUE (gene, nucleotide_change, protein_change)
);

/* --Test Data-- */
INSERT INTO variant(variant_id, gene, nucleotide_change, protein_change, last_evaluated)
VALUES ('ad7dbf59-1c4a-43ef-a857-c78ddbac944b', 'PGAM4', 'NG_013224.2:g.(?_4960)_(103567_105489)dup,NC_000023.11:g.(?_77910656)_(78009263_78011185)dup', 'NG_013224.2,NC_000023.11', '2021-08-17');
