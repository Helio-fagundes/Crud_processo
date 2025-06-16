CREATE TABLE "clients" (
    "id"              VARCHAR(36) PRIMARY KEY,
    "name"            VARCHAR(100) NOT NULL,
    "social_security" CHAR(11) NOT NULL UNIQUE,
    "phone"           VARCHAR(11) NOT NULL,
    "email"           VARCHAR(100) NOT NULL UNIQUE,
    "address"         VARCHAR(255) NOT NULL,
    "number"          VARCHAR(10) NOT NULL,
    "complement"      VARCHAR(100),
    "neighborhood"    VARCHAR(100) NOT NULL,
    "city"            VARCHAR(100) NOT NULL,
    "state"           CHAR(36) NOT NULL,
    "created_at" TIMESTAMP NOT NULL,
    "updated_at" TIMESTAMP NOT NULL
);
