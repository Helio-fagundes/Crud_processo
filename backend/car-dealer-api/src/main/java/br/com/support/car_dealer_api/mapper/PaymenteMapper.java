package br.com.support.car_dealer_api.mapper;

import br.com.support.car_dealer_api.dto.payment.PaymentCreateResponseDTO;
import br.com.support.car_dealer_api.dto.payment.PaymentRequestDTO;
import br.com.support.car_dealer_api.dto.payment.PaymentResponseDTO;
import br.com.support.car_dealer_api.dto.sale.SaleResponseDTO;
import br.com.support.car_dealer_api.entity.Payment;
import br.com.support.car_dealer_api.repository.SaleRepository;
import br.com.support.car_dealer_api.util.UUIDUtil;
import org.springframework.stereotype.Component;

@Component
public class PaymenteMapper {

    private final SaleRepository saleRepository;

    public PaymenteMapper(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public Payment toEntity(PaymentRequestDTO dto){
        Payment payment = new Payment();
        payment.setId(UUIDUtil.newUUID());
        payment.setSaleId(saleRepository.findById(dto.getSaleId())
                .orElseThrow(() -> new RuntimeException("Venda não encontrada")));
        payment.setPaymentType(dto.getPaymentType());
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setInstallment(dto.isInstallment());
        payment.setAmount(dto.getAmount());
        payment.setReceived(dto.isReceived());
        payment.setMovement(dto.getMovement());
        return payment;
    }

    public PaymentCreateResponseDTO paymentCreateResponseDTO(Payment payment){
        PaymentCreateResponseDTO dto = new PaymentCreateResponseDTO();
        dto.setId(payment.getId());
        return dto;
    }

    public PaymentResponseDTO toResponseDTO(Payment payment){
        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setId(payment.getId());

        SaleResponseDTO.ClientInfo clientInfo = new SaleResponseDTO.ClientInfo();
        clientInfo.setName(payment.getSaleId().getClient().getName());
        clientInfo.setCpf(payment.getSaleId().getClient().getSocialSecurity());
        clientInfo.setPhone(payment.getSaleId().getClient().getPhone());
        dto.setClient(clientInfo);

        SaleResponseDTO.VehicleInfo vehicleInfo = new SaleResponseDTO.VehicleInfo();
        vehicleInfo.setModel(payment.getSaleId().getVehicle().getModel());
        vehicleInfo.setColor(payment.getSaleId().getVehicle().getColor());
        vehicleInfo.setPlate(payment.getSaleId().getVehicle().getPlate());
        dto.setVehicle(vehicleInfo);

        SaleResponseDTO.SellerInfo sellerInfo = new SaleResponseDTO.SellerInfo();
        sellerInfo.setName(payment.getSaleId().getSeller().getName());
        sellerInfo.setCpf(payment.getSaleId().getSeller().getCpf());
        sellerInfo.setPhone(payment.getSaleId().getSeller().getPhone());
        dto.setSeller(sellerInfo);

        dto.setPaymentType(payment.getPaymentType());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setInstallment(payment.isInstallment());
        dto.setAmount(payment.getAmount());
        dto.setReceived(payment.isReceived());
        dto.setMovement(payment.getMovement());
        return dto;
    }

    public Payment updateEntity(Payment entity, PaymentRequestDTO dto){

        if (entity.getPaymentType().equals(dto.getPaymentType())){
            entity.setPaymentType(dto.getPaymentType());
        }

        if (entity.getPaymentMethod().equals(dto.getPaymentMethod())){
            entity.setPaymentMethod(dto.getPaymentMethod());
        }

        if (entity.isReceived() != dto.isReceived()){
            entity.setReceived(dto.isReceived());
        }

        return entity;
    }
}
