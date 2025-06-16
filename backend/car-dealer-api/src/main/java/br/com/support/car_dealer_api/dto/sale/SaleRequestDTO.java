package br.com.support.car_dealer_api.dto.sale;

import br.com.support.car_dealer_api.entity.Client;
import br.com.support.car_dealer_api.entity.Seller;
import br.com.support.car_dealer_api.entity.Vehicle;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SaleRequestDTO {
    private String clienteId;
    private String vehicleId;
    private String sellerId;
    private LocalDateTime saleDate;
    private double salePrice;
    private int installmentQuantity;

    public String getClienteId() {
        return clienteId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public int getInstallmentQuantity() {
        return installmentQuantity;
    }
}
