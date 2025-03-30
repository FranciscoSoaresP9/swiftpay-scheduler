CREATE SEQUENCE transfer_sequence START 1;

CREATE TABLE transfers (
    id BIGINT NOT NULL DEFAULT nextval('transfer_sequence') PRIMARY KEY,
    sender_account_id BIGINT NOT NULL REFERENCES bank_accounts(id),
    receiver_account_id BIGINT NOT NULL REFERENCES bank_accounts(id),
    amount DECIMAL(15, 2) NOT NULL,
    schedule_date DATE NOT NULL,
    amount_including_fees DECIMAL(15, 2),
    applied_fixed_fee DECIMAL(15, 2),
    applied_tax_percentage DECIMAL(5, 2),
    status VARCHAR(255) NOT NULL
);

ALTER SEQUENCE transfer_sequence OWNED BY transfers.id;