DROP SCHEMA IF EXISTS payment CASCADE;

CREATE SCHEMA payment;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TYPE IF EXISTS payment_status;

CREATE TYPE payment_status AS ENUM ('COMPLETED', 'CANCELLED', 'FAILED');

DROP TABLE IF EXISTS "payment".payments CASCADE;

CREATE TABLE "payment".payments
(
    id uuid NOT NULL,
    customer_id uuid NOT NULL,
    order_id uuid NOT NULL,
    price numeric(10,2) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    status payment_status NOT NULL,
    CONSTRAINT payments_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "payment".credit_entry CASCADE;

CREATE TABLE "payment".credit_entry
(
    id uuid NOT NULL,
    customer_id uuid NOT NULL,
    total_credit_amount numeric(10,2) NOT NULL,
    CONSTRAINT credit_entry_pkey PRIMARY KEY (id)
);

DROP TYPE IF EXISTS transaction_type;

CREATE TYPE transaction_type AS ENUM ('DEBIT', 'CREDIT');

DROP TABLE IF EXISTS "payment".credit_history CASCADE;

CREATE TABLE "payment".credit_history
(
    id uuid NOT NULL,
    customer_id uuid NOT NULL,
    amount numeric(10,2) NOT NULL,
    type transaction_type NOT NULL,
    CONSTRAINT credit_history_pkey PRIMARY KEY (id)
);


INSERT INTO payment.credit_entry(id, customer_id, total_credit_amount)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb21', 'd215b5f8-0249-4dc5-89a3-51fd148cfb41', 500.00);
INSERT INTO payment.credit_history(id, customer_id, amount, type)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb23', 'd215b5f8-0249-4dc5-89a3-51fd148cfb41', 100.00, 'CREDIT');
INSERT INTO payment.credit_history(id, customer_id, amount, type)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb24', 'd215b5f8-0249-4dc5-89a3-51fd148cfb41', 600.00, 'CREDIT');
INSERT INTO payment.credit_history(id, customer_id, amount, type)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb25', 'd215b5f8-0249-4dc5-89a3-51fd148cfb41', 200.00, 'DEBIT');


INSERT INTO payment.credit_entry(id, customer_id, total_credit_amount)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb22', 'd215b5f8-0249-4dc5-89a3-51fd148cfb43', 100.00);
INSERT INTO payment.credit_history(id, customer_id, amount, type)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb26', 'd215b5f8-0249-4dc5-89a3-51fd148cfb43', 100.00, 'CREDIT');
