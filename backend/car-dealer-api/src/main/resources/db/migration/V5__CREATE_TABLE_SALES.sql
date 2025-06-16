CREATE TABLE "sales" (
    "id"                  VARCHAR(36) PRIMARY KEY,
    "client_id"           VARCHAR(36) NOT NULL,
    "vehicle_id"          VARCHAR(36) NOT NULL,
    "seller_id"           VARCHAR(36) NOT NULL,
    "sale_date"           TIMESTAMP NOT NULL,
    "sale_price"          DECIMAL(10, 2) NOT NULL,
    "installment_count"   INT NOT NULL,
    "created_at"         TIMESTAMP NOT NULL,
    "updated_at"         TIMESTAMP NOT NULL,

    CONSTRAINT "fk_sales_client" FOREIGN KEY ("client_id") REFERENCES "clients"("id"),
    CONSTRAINT "fk_sales_vehicle" FOREIGN KEY ("vehicle_id") REFERENCES "vehicles"("id"),
    CONSTRAINT "fk_sales_seller" FOREIGN KEY ("seller_id") REFERENCES "sellers"("id")
);
