CREATE TABLE transfer_fees (
    transfer_id BIGINT PRIMARY KEY,
    fixed_fee DECIMAL(15, 2),
    tax_percentage DECIMAL(5, 2),
    CONSTRAINT fk_transfer_id FOREIGN KEY (transfer_id) REFERENCES transfers(id) ON DELETE CASCADE
);
