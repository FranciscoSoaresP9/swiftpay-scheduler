CREATE TABLE fee_ranges (
    fee_id BIGINT NOT NULL REFERENCES fees(id) ON DELETE CASCADE,
    min_days INT,
    max_days INT,
    fixed_fee DECIMAL(19, 2),
    tax_percentage DECIMAL(5, 2),
    PRIMARY KEY (fee_id, min_days)
);