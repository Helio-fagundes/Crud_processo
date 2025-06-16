ALTER TABLE "payments"
    ADD CONSTRAINT "fk_payment_sale" FOREIGN KEY ("sale") REFERENCES "sales"("id");
