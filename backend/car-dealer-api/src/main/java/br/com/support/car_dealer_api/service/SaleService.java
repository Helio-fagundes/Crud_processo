package br.com.support.car_dealer_api.service;

import br.com.support.car_dealer_api.dto.sale.SaleCreateResponseDTO;
import br.com.support.car_dealer_api.dto.sale.SaleRequestDTO;
import br.com.support.car_dealer_api.dto.sale.SaleResponseDTO;
import br.com.support.car_dealer_api.entity.Sale;
import br.com.support.car_dealer_api.exception.ResourceNotFoundException;
import br.com.support.car_dealer_api.mapper.SaleMapper;
import br.com.support.car_dealer_api.repository.ClientRepository;
import br.com.support.car_dealer_api.repository.SaleRepository;
import br.com.support.car_dealer_api.repository.SellerRepository;
import br.com.support.car_dealer_api.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;
    private final VehicleRepository vehicleRepository;
    private final SellerRepository sellerRepository;
    private final ClientRepository clientRepository;

    public SaleService(SaleRepository saleRepository, SaleMapper saleMapper,
                       VehicleRepository vehicleRepository, SellerRepository sellerRepository, ClientRepository clientRepository)  {
        this.saleRepository = saleRepository;
        this.saleMapper = saleMapper;
        this.vehicleRepository = vehicleRepository;
        this.sellerRepository = sellerRepository;
        this.clientRepository = clientRepository;
    }

    private void CustomerBusinessRules(String id, SaleRequestDTO dto) {

        if (clientRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }
    }

    private  void VehicleBusinessRules(String id, SaleRequestDTO dto) {

        if (vehicleRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Veículo não encontrado");
        }

        if( vehicleRepository.findById(id).get().isReleased()) {
            throw new IllegalArgumentException("Veículo já vendido");
        }
    }

    private  void SellerBusinessRules(String id, SaleRequestDTO dto) {
        if (sellerRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Vendedor não encontrado");
        }
    }

    private void validateSaleRequest(SaleRequestDTO dto) {
        if (dto.getInstallmentQuantity() < 0) {
            throw new IllegalArgumentException("Quantidade de parcelas deve ser maior que zero");
        }
        if (dto.getSalePrice() < 0) {
            throw new IllegalArgumentException("Preço da venda deve ser maior que zero");
        }
    }

    @Transactional
    public SaleCreateResponseDTO createSale(SaleRequestDTO dto) {

        CustomerBusinessRules(String.valueOf(dto.getClienteId()), dto);
        VehicleBusinessRules(String.valueOf(dto.getVehicleId()), dto);
        SellerBusinessRules(String.valueOf(dto.getSellerId()), dto);
        validateSaleRequest(dto);

        Sale sale = saleMapper.toEntity(dto);
        Sale modified = saleRepository.save(sale);
        return saleMapper.toCreateResponseDTO(modified);

    }
    @Transactional
    public List<SaleResponseDTO> getAllSales() {
        return saleRepository.findAll().stream()
                .map(saleMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public SaleResponseDTO getSaleById(String id) {
        return saleRepository.findById(id)
                .map(saleMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Sale", id, Sale.class));
    }

    @Transactional
    public void deleteSale(String id) {
        if (!saleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sale", id, Sale.class);
        }
        saleRepository.deleteById(id);
    }

    @Transactional
    public SaleResponseDTO updateSale(String id, SaleRequestDTO dto) {
        Sale existing = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale", id, Sale.class));

        CustomerBusinessRules(String.valueOf(dto.getClienteId()), dto);
        VehicleBusinessRules(String.valueOf(dto.getVehicleId()), dto);
        SellerBusinessRules(String.valueOf(dto.getSellerId()), dto);
        validateSaleRequest(dto);

        Sale modified = saleRepository.save(existing);
        return saleMapper.toResponseDto(modified);

    }

}
