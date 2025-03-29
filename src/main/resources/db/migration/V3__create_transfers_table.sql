CREATE SEQUENCE transfer_sequence START 1;

CREATE TABLE transfers (
    id BIGINT NOT NULL DEFAULT nextval('transfer_sequence') PRIMARY KEY,
    sender_id BIGINT NOT NULL REFERENCES users(id),
    receiver_id BIGINT NOT NULL REFERENCES users(id),
    amount DECIMAL(15, 2) NOT NULL,
    schedule_date DATE NOT NULL,
    total_amount DECIMAL(15, 2),
    status VARCHAR(255) NOT NULL
);

ALTER SEQUENCE transfer_sequence OWNED BY transfers.id;