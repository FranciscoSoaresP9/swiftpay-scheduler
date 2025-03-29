CREATE SEQUENCE bank_account_sequence START 1;

CREATE TABLE bank_accounts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT UNIQUE NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    iban VARCHAR(255) UNIQUE NOT NULL,
    balance NUMERIC(19, 2) DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT NOW()
);

ALTER SEQUENCE bank_account_sequence OWNED BY bank_accounts.id;