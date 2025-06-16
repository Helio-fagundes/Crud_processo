package br.com.support.car_dealer_api.mapper;

import br.com.support.car_dealer_api.dto.seller.SellerCreateResponseDTO;
import br.com.support.car_dealer_api.dto.seller.SellerRequestDTO;
import br.com.support.car_dealer_api.dto.seller.SellerResponseDTO;
import br.com.support.car_dealer_api.entity.Seller;
import br.com.support.car_dealer_api.util.UUIDUtil;
import org.springframework.stereotype.Component;

@Component
public class SellerMapper {

    public Seller toEntity(SellerRequestDTO dto) {
        Seller seller = new Seller();
        seller.setId(UUIDUtil.newUUID());
        seller.setName(dto.getName());
        seller.setCpf(dto.getCpf());
        seller.setPhone(dto.getPhone());
        seller.setEmail(dto.getEmail());
        seller.setManager(dto.isManager());
        return seller;
    }

    public SellerCreateResponseDTO toCreateResponseDTO(Seller seller) {
        SellerCreateResponseDTO dto = new SellerCreateResponseDTO();
        dto.setId(seller.getId());
        return dto;
    }

    public SellerResponseDTO toResponseDto(Seller seller) {
        SellerResponseDTO dto = new SellerResponseDTO();
        dto.setId(seller.getId());
        dto.setName(seller.getName());
        dto.setCpf(seller.getCpf());
        dto.setPhone(seller.getPhone());
        dto.setEmail(seller.getEmail());
        dto.setManager(seller.isManager());
        return dto;
    }

    public Seller updateEntity(Seller entity, SellerRequestDTO dto) {
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        if (dto.getPhone() != null) {
            entity.setPhone(dto.getPhone());
        }

        return entity;
    }
}
