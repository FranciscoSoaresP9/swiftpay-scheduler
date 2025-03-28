CREATE SEQUENCE user_sequence START 1;

CREATE TABLE users (
   id BIGINT NOT NULL DEFAULT nextval('user_sequence') PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   email VARCHAR(255) NOT NULL,
   external_id VARCHAR(255),
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   balance DECIMAL(15, 2)
);

ALTER SEQUENCE user_sequence OWNED BY users.id;