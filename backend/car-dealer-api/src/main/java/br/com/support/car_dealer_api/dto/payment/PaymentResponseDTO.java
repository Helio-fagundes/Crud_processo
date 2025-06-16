package br.com.support.car_dealer_api.dto.payment;

import br.com.support.car_dealer_api.dto.sale.SaleResponseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PaymentResponseDTO extends SaleResponseDTO {
    private String id;
    private SaleResponseDTO.ClientInfo client;
    private SaleResponseDTO.VehicleInfo vehicle;
    private SaleResponseDTO.SellerInfo seller;
    private String saleId;
    private String paymentType;
    private String paymentMethod;
    private boolean installment;
    private double amount;
    private boolean received;
    private String movement;


}
