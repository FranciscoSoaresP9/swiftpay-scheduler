CREATE SEQUENCE fee_sequence START 1;

CREATE TABLE fees (
    id BIGINT NOT NULL DEFAULT nextval('fee_sequence') PRIMARY KEY,
    name VARCHAR(255),
    min_amount DECIMAL(15, 2),
    max_amount DECIMAL(15, 2)
);

ALTER SEQUENCE fee_sequence OWNED BY fees.id;