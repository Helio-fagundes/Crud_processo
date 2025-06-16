package br.com.support.car_dealer_api.mapper;

import br.com.support.car_dealer_api.dto.sale.SaleCreateResponseDTO;
import br.com.support.car_dealer_api.dto.sale.SaleRequestDTO;
import br.com.support.car_dealer_api.dto.sale.SaleResponseDTO;
import br.com.support.car_dealer_api.entity.Sale;
import br.com.support.car_dealer_api.repository.ClientRepository;
import br.com.support.car_dealer_api.repository.SellerRepository;
import br.com.support.car_dealer_api.repository.VehicleRepository;
import br.com.support.car_dealer_api.util.UUIDUtil;
import org.springframework.stereotype.Component;

@Component
public class SaleMapper {

    private final ClientRepository clientRepository;
    private final SellerRepository sellerRepository;
    private final VehicleRepository vehicleRepository;

    public SaleMapper(ClientRepository clientRepository, SellerRepository sellerRepository, VehicleRepository vehicleRepository) {
        this.clientRepository = clientRepository;
        this.sellerRepository = sellerRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public Sale toEntity(SaleRequestDTO dto) {
        Sale entity = new Sale();
        entity.setId(UUIDUtil.newUUID());
        entity.setClient(clientRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado")));
        entity.setVehicle(vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado")));
        entity.setSeller(sellerRepository.findById(dto.getSellerId())
                .orElseThrow(() -> new RuntimeException("Vendedor não encontrado")));
        entity.setSaleDate(dto.getSaleDate());
        entity.setSalePrice(dto.getSalePrice());
        entity.setInstallmentQuantity(dto.getInstallmentQuantity());
        return entity;
    }

    public SaleCreateResponseDTO toCreateResponseDTO(Sale sale) {
        SaleCreateResponseDTO dto = new SaleCreateResponseDTO();
        dto.setId(sale.getId());
        return dto;
    }

    public SaleResponseDTO toResponseDto(Sale sale) {
        SaleResponseDTO dto = new SaleResponseDTO();
        dto.setId(sale.getId());

        SaleResponseDTO.ClientInfo clientInfo = new SaleResponseDTO.ClientInfo();
        clientInfo.setName(sale.getClient().getName());
        clientInfo.setCpf(sale.getClient().getSocialSecurity());
        clientInfo.setPhone(sale.getClient().getPhone());
        dto.setClient(clientInfo);

        SaleResponseDTO.VehicleInfo vehicleInfo = new SaleResponseDTO.VehicleInfo();
        vehicleInfo.setModel(sale.getVehicle().getModel());
        vehicleInfo.setColor(sale.getVehicle().getColor());
        vehicleInfo.setPlate(sale.getVehicle().getPlate());
        dto.setVehicle(vehicleInfo);

        SaleResponseDTO.SellerInfo sellerInfo = new SaleResponseDTO.SellerInfo();
        sellerInfo.setName(sale.getSeller().getName());
        sellerInfo.setCpf(sale.getSeller().getCpf());
        sellerInfo.setPhone(sale.getSeller().getPhone());
        dto.setSeller(sellerInfo);

        dto.setSaleDate(sale.getSaleDate().toLocalDate());
        dto.setSalePrice(sale.getSalePrice());
        dto.setInstallmentQuantity(sale.getInstallmentQuantity());
        return dto;
    }

    public Sale updateEntity(Sale entity, SaleRequestDTO dto) {

        if (entity.getSalePrice() != null) {
            entity.setSalePrice(dto.getSalePrice());
        }
        if( dto.getInstallmentQuantity() != 0) {
            entity.setInstallmentQuantity(dto.getInstallmentQuantity());
        }
        return entity;
    }
}
