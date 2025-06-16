CREATE TABLE "sellers" (
    "id"        VARCHAR(36) PRIMARY KEY,
    "name"      VARCHAR(100) NOT NULL,
    "cpf"       VARCHAR(11) NOT NULL UNIQUE,
    "phone"     VARCHAR(11) NOT NULL,
    "email"     VARCHAR(100) NOT NULL UNIQUE,
    "manager"   BOOLEAN NOT NULL,
    "created_at" TIMESTAMP NOT NULL,
    "updated_at" TIMESTAMP NOT NULL
);
