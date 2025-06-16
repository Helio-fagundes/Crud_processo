package br.com.support.car_dealer_api.service;

import br.com.support.car_dealer_api.dto.client.ClientCreateResponseDTO;
import br.com.support.car_dealer_api.dto.client.ClientRequestDTO;
import br.com.support.car_dealer_api.dto.client.ClientResponseDTO;
import br.com.support.car_dealer_api.entity.Client;
import br.com.support.car_dealer_api.exception.BusinessException;
import br.com.support.car_dealer_api.exception.ResourceNotFoundException;
import br.com.support.car_dealer_api.mapper.ClientMapper;
import br.com.support.car_dealer_api.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Transactional
    public ClientCreateResponseDTO createClient(ClientRequestDTO dto) {
        Client client = clientMapper.toEntity(dto);

        Optional<Client> email = clientRepository.findByEmail(dto.getEmail());
        Optional<Client> cpf = clientRepository.findBySocialSecurity(dto.getSocialSecurity());

        if(email.isPresent()){
            throw new BusinessException("E-mail já existente");
        }

        if(cpf.isPresent()){
            throw new BusinessException("CPF já existente");
        }

        if(client.getState().length() > 2){
            throw new BusinessException("O estado deve ter apenas duas caracteres");
        }

        if (client.getPhone().length() != 11) {
            throw new BusinessException("Telefone invalido");
        }

        if (
            !client.getEmail().contains("@") ||
            !client.getEmail().contains(".")) {
            throw new BusinessException("E-mail inválido");
        }

        if (client.getAddress().length() < 3) {
            throw new BusinessException("Endereço deve ter ao menos três caracteres");
        }

        if (client.getNumber().length() < 1) {
            throw new BusinessException("Número deve ter ao menos um caractere");
        }

        if (client.getNeighborhood().length() < 3) {
            throw new BusinessException("Bairro deve ter ao menos três caracteres");
        }

        if (client.getCity().length() < 3) {
            throw new BusinessException("Cidade deve ter ao menos três caracteres");
        }

        if (client.getName().length() < 3) {
            throw new BusinessException("Nome deve ter ao menos três caracteres");
        }

        if (client.getComplement() != null && client.getComplement().length() < 3) {
            throw new BusinessException("Complemento deve ter ao menos três caracteres");
        }

        if (client.getState().length() > 2){
            throw new BusinessException("O estado deve ter apenas duas caracteres");
        }

        Client savedClient = clientRepository.save(client);
        return clientMapper.toCreateResponseDto(savedClient);
    }

    @Transactional
    public List<ClientResponseDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(clientMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ClientResponseDTO getClientById(String id) {
        return clientRepository.findById(id)
                .map(clientMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", id, Client.class));
    }

    @Transactional
    public void deleteClient(String id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente", id, Client.class);
        }
        clientRepository.deleteById(id);
    }

    @Transactional
    public ClientResponseDTO updateClient(String id, ClientRequestDTO dto) {
        Client existing = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", id, Client.class));

        if (dto.getName() != null && dto.getName().length() < 3) {
            throw new BusinessException("Nome deve ter ao menos três caracteres");
        }

        if(!dto.getEmail().isEmpty()){
            throw new BusinessException("E-mail não pode ser atualizado");
        }

        if(!dto.getSocialSecurity().isEmpty()){
            throw new BusinessException("CPF não pode ser atualizado");
        }

        if (dto.getState().length() > 2){
            throw new BusinessException("O estado deve ter apenas duas caracteres");
        }

        if (dto.getPhone() != null && dto.getPhone().length() < 11) {
            throw new BusinessException("Telefone deve ter ao menos 11 caracteres");
        }

        if (dto.getAddress() != null && dto.getAddress().length() < 3) {
            throw new BusinessException("Endereço deve ter ao menos três caracteres");
        }

        if (dto.getNumber() != null && dto.getNumber().length() < 1) {
            throw new BusinessException("Número deve ter ao menos um caractere");
        }

        if (dto.getNeighborhood() != null && dto.getNeighborhood().length() < 3) {
            throw new BusinessException("Bairro deve ter ao menos três caracteres");
        }

        if (dto.getCity() != null && dto.getCity().length() < 3) {
            throw new BusinessException("Cidade deve ter ao menos três caracteres");
        }

        Client updated = clientMapper.updateEntity(existing, dto);
        Client saved = clientRepository.save(updated);

        return clientMapper.toResponseDto(saved);
    }
}
