package br.com.support.car_dealer_api.dto.payment;

import br.com.support.car_dealer_api.entity.Sale;

public class PaymentRequestDTO {
    private String saleId;
    private String paymentType;
    private String paymentMethod;
    private boolean installment;
    private double amount;
    private boolean received;
    private String movement;

    public String getSaleId() {
        return saleId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public boolean isInstallment() {
        return installment;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isReceived() {
        return received;
    }

    public String getMovement() {
        return movement;
    }
}
