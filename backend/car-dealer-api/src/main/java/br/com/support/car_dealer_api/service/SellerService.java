package br.com.support.car_dealer_api.service;

import br.com.support.car_dealer_api.dto.seller.SellerCreateResponseDTO;
import br.com.support.car_dealer_api.dto.seller.SellerRequestDTO;
import br.com.support.car_dealer_api.dto.seller.SellerResponseDTO;
import br.com.support.car_dealer_api.entity.Seller;
import br.com.support.car_dealer_api.exception.BusinessException;
import br.com.support.car_dealer_api.exception.ResourceNotFoundException;
import br.com.support.car_dealer_api.mapper.SellerMapper;
import br.com.support.car_dealer_api.repository.SellerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SellerService {

    private final SellerRepository sellerRepository;
    private final SellerMapper sellerMapper;

    public SellerService(SellerRepository sellerRepository, SellerMapper sellerMapper) {
        this.sellerRepository = sellerRepository;
        this.sellerMapper = sellerMapper;
    }

    @Transactional
    public SellerCreateResponseDTO createSeller(SellerRequestDTO dto) {
        Seller seller = sellerMapper.toEntity(dto);
        Optional<Seller> email = sellerRepository.findByEmail(dto.getEmail());
        Optional<Seller> cpf = sellerRepository.findByCpf(dto.getCpf());

        if (email.isPresent()) {
            throw new BusinessException("E-mail existente");
        }
        if (cpf.isPresent()) {
            throw new BusinessException("CPF existente");
        }
        Seller savedSeller = sellerRepository.save(seller);
        return sellerMapper.toCreateResponseDTO(savedSeller);
    }

    @Transactional
    public List<SellerResponseDTO> getAllSellers() {
    return sellerRepository.findAll().stream()
            .map(sellerMapper::toResponseDto)
            .collect(Collectors.toList());
    }

    @Transactional
    public SellerResponseDTO getSellerById(String id) {
        return sellerRepository.findById(id)
                .map(sellerMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Seller", id , Seller.class));
    }

    @Transactional
    public void  deleteSeller(String id) {
        if (!sellerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Seller", id, Seller.class);
        }
        sellerRepository.deleteById(id);}

    @Transactional
    public SellerResponseDTO updateSeller(String id, SellerRequestDTO dto) {
        Seller modified = sellerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Seller", id, Seller.class));

        if (dto.getName() != null && dto.getName().length() < 3) {
            throw new BusinessException("O nome deve ter pelo menos 3 caracteres");}

        if (!dto.getEmail().isEmpty()){
            throw new BusinessException("E-mail não pode ser atualizado");}


        if (!dto.getCpf().isEmpty()) {
            throw new BusinessException("CPF não pode ser atualizado");
        }

        if (dto.getPhone() != null && dto.getPhone().length() != 11) {
            throw new BusinessException("O telefone deve ter pelo menos 11 caracteres");
        }


        Seller updated = sellerMapper.updateEntity(modified, dto);
        Seller saved = sellerRepository.save(updated);

        return sellerMapper.toResponseDto(saved);

    }

}
