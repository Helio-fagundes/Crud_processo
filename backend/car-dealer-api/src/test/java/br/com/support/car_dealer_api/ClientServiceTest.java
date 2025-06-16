package br.com.support.car_dealer_api;

import br.com.support.car_dealer_api.dto.client.ClientCreateResponseDTO;
import br.com.support.car_dealer_api.dto.client.ClientRequestDTO;
import br.com.support.car_dealer_api.entity.Client;
import br.com.support.car_dealer_api.exception.BusinessException;
import br.com.support.car_dealer_api.mapper.ClientMapper;
import br.com.support.car_dealer_api.repository.ClientRepository;
import br.com.support.car_dealer_api.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowBusinessExceptionWhenEmailAlreadyExists() {
        ClientRequestDTO dto = new ClientRequestDTO();
        dto.setEmail("test@example.com");
        dto.setSocialSecurity("12345678900");
        dto.setState("SP");
        dto.setPhone("11912345678");
        dto.setAddress("Rua A");
        dto.setNumber("10");
        dto.setNeighborhood("Centro");
        dto.setCity("São Paulo");
        dto.setName("João");

        when(clientRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new Client()));

        assertThatThrownBy(() -> clientService.createClient(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("E-mail já existente");

        verify(clientRepository).findByEmail("test@example.com");
    }

    @Test
    void shouldThrowBusinessExceptionWhenCpfAlreadyExists() {
        ClientRequestDTO dto = new ClientRequestDTO();
        dto.setEmail("test@example.com");
        dto.setSocialSecurity("12345678900");
        dto.setState("SP");
        dto.setPhone("11912345678");
        dto.setAddress("Rua A");
        dto.setNumber("10");
        dto.setNeighborhood("Centro");
        dto.setCity("São Paulo");
        dto.setName("João");

        when(clientRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(clientRepository.findBySocialSecurity("12345678900")).thenReturn(Optional.of(new Client()));

        assertThatThrownBy(() -> clientService.createClient(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("CPF já existente");

        verify(clientRepository).findBySocialSecurity("12345678900");
    }

    @Test
    void shouldThrowBusinessExceptionWhenStateHasMoreThanTwoCharacters() {
        ClientRequestDTO dto = new ClientRequestDTO();
        dto.setEmail("test@example.com");
        dto.setSocialSecurity("12345678900");
        dto.setState("SPO");  // Estado inválido
        dto.setPhone("11912345678");
        dto.setAddress("Rua A");
        dto.setNumber("10");
        dto.setNeighborhood("Centro");
        dto.setCity("São Paulo");
        dto.setName("João");

        when(clientRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(clientRepository.findBySocialSecurity(dto.getSocialSecurity())).thenReturn(Optional.empty());
        Client client = new Client();
        client.setState("SPO");
        when(clientMapper.toEntity(dto)).thenReturn(client);

        assertThatThrownBy(() -> clientService.createClient(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("estado deve ter apenas duas caracteres");
    }

    @Test
    void shouldSaveClientSuccessfully() {
        ClientRequestDTO dto = new ClientRequestDTO();
        dto.setEmail("test@example.com");
        dto.setSocialSecurity("12345678900");
        dto.setState("SP");
        dto.setPhone("11912345678");
        dto.setAddress("Rua A");
        dto.setNumber("10");
        dto.setNeighborhood("Centro");
        dto.setCity("São Paulo");
        dto.setName("João");

        when(clientRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(clientRepository.findBySocialSecurity(dto.getSocialSecurity())).thenReturn(Optional.empty());

        Client client = new Client();
        client.setEmail(dto.getEmail());
        client.setState(dto.getState());
        when(clientMapper.toEntity(dto)).thenReturn(client);
        when(clientRepository.save(client)).thenReturn(client);

        ClientCreateResponseDTO response = new ClientCreateResponseDTO();
        when(clientMapper.toCreateResponseDto(client)).thenReturn(response);

        clientService.createClient(dto);

        verify(clientRepository).save(client);
    }
}
