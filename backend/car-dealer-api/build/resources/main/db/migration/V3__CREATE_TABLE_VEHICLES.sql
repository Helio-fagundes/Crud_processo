CREATE TABLE "vehicles" (
    "id"              VARCHAR(36) PRIMARY KEY,
    "brand"           VARCHAR(50) NOT NULL,
    "model"           VARCHAR(50) NOT NULL,
    "year"            INT NOT NULL,
    "color"           VARCHAR(30) NOT NULL,
    "plate"   VARCHAR(7) NOT NULL UNIQUE,
    "chassis"         VARCHAR(17) NOT NULL UNIQUE,
    "fuel_type"       VARCHAR(20) NOT NULL,
    "price"           DECIMAL(10, 2) NOT NULL,
    "status"          VARCHAR(20) NOT NULL,
    "released"        BOOLEAN NOT NULL,
    "created_at"     TIMESTAMP NOT NULL,
    "updated_at"     TIMESTAMP NOT NULL
);
