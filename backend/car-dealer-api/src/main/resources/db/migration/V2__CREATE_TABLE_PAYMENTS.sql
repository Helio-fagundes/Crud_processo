CREATE TABLE payments(
    "id"             VARCHAR(36) PRIMARY KEY,
    "sale"           VARCHAR(36)    NOT NULL,
    "payment_type"   VARCHAR(50)    NOT NULL,
    "payment_method" VARCHAR(50)    NOT NULL,
    "installment"    BOOLEAN        NOT NULL,
    "amount"         DECIMAL(10, 2) NOT NULL,
    "received"       BOOLEAN        NOT NULL,
    "created_at"     TIMESTAMP      NOT NULL,
    "updated_at"     TIMESTAMP      NOT NULL
);
