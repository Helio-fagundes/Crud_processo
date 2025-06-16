package br.com.support.car_dealer_api.dto.sale;

import br.com.support.car_dealer_api.entity.Client;
import br.com.support.car_dealer_api.entity.Seller;
import br.com.support.car_dealer_api.entity.Vehicle;
import lombok.Data;

import java.time.LocalDate;
@Data
public class SaleResponseDTO {
    private String id;
    private ClientInfo client;
    private VehicleInfo vehicle;
    private SellerInfo seller;
    private LocalDate saleDate;
    private Double salePrice;
    private int installmentQuantity;

    @Data
    public static class ClientInfo {
        private String name;
        private String cpf;
        private String phone;
    }

    @Data
    public static class VehicleInfo {
        private String model;
        private String color;
        private String plate;
    }

    @Data
    public static class SellerInfo {
        private String name;
        private String cpf;
        private String phone;
    }
}

