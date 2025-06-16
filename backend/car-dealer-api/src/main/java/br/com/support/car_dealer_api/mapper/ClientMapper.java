package br.com.support.car_dealer_api.mapper;

import br.com.support.car_dealer_api.dto.client.ClientRequestDTO;
import br.com.support.car_dealer_api.dto.client.ClientCreateResponseDTO;
import br.com.support.car_dealer_api.dto.client.ClientResponseDTO;
import br.com.support.car_dealer_api.entity.Client;
import br.com.support.car_dealer_api.util.UUIDUtil;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public Client toEntity(ClientRequestDTO dto) {
        Client client = new Client();
        client.setId(UUIDUtil.newUUID());
        client.setName(dto.getName());
        client.setEmail(dto.getEmail());
        client.setPhone(dto.getPhone());
        client.setSocialSecurity(dto.getSocialSecurity());
        client.setAddress(dto.getAddress());
        client.setNumber(dto.getNumber());
        client.setComplement(dto.getComplement());
        client.setNeighborhood(dto.getNeighborhood());
        client.setCity(dto.getCity());
        client.setState(dto.getState());
        return client;
    }

    public ClientCreateResponseDTO toCreateResponseDto(Client client) {
        ClientCreateResponseDTO dto = new ClientCreateResponseDTO();
        dto.setId(client.getId());
        return dto;
    }

    public ClientResponseDTO toResponseDto(Client client) {
        ClientResponseDTO dto = new ClientResponseDTO();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setSocialSecurity(client.getSocialSecurity());
        dto.setPhone(client.getPhone());
        dto.setEmail(client.getEmail());
        dto.setAddress(client.getAddress());
        dto.setNumber(client.getNumber());
        dto.setComplement(client.getComplement());
        dto.setNeighborhood(client.getNeighborhood());
        dto.setCity(client.getCity());
        dto.setState(client.getState());
        return dto;
    }

    public Client updateEntity(Client entity, ClientRequestDTO dto) {
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        if (dto.getPhone() != null) {
            entity.setPhone(dto.getPhone());
        }
        if (dto.getAddress() != null) {
            entity.setAddress(dto.getAddress());
        }
        if (dto.getNumber() != null) {
            entity.setNumber(dto.getNumber());
        }
        if (dto.getComplement() != null) {
            entity.setComplement(dto.getComplement());
        }
        if (dto.getNeighborhood() != null) {
            entity.setNeighborhood(dto.getNeighborhood());
        }
        if (dto.getCity() != null) {
            entity.setCity(dto.getCity());
        }
        if (dto.getState() != null) {
            entity.setState(dto.getState());
        }


        return entity;
    }
}