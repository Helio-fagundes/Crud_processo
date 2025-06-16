package br.com.support.car_dealer_api.controller;

import br.com.support.car_dealer_api.dto.client.ClientCreateResponseDTO;
import br.com.support.car_dealer_api.dto.client.ClientRequestDTO;
import br.com.support.car_dealer_api.dto.client.ClientResponseDTO;
import br.com.support.car_dealer_api.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@Tag(name = "Client Management", description = "Operations related to managing clients in the car dealer system.")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    @Operation(summary = "Get all clients", description = "Returns a list of all registered clients.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of clients",
            content = @Content(schema = @Schema(implementation = ClientResponseDTO.class)))
    public ResponseEntity<List<ClientResponseDTO>> getAll() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get client by ID", description = "Returns a single client based on the provided ID.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved client",
            content = @Content(schema = @Schema(implementation = ClientResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Client not found")
    public ResponseEntity<ClientResponseDTO> getById(
            @Parameter(description = "ID of the client to retrieve", example = "1") @PathVariable String id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new client", description = "Registers a new client in the system.")
    @ApiResponse(responseCode = "200", description = "Successfully created client",
            content = @Content(schema = @Schema(implementation = ClientCreateResponseDTO.class)))
    public ResponseEntity<ClientCreateResponseDTO> create(@Valid @RequestBody ClientRequestDTO dto) {
        ClientCreateResponseDTO response = clientService.createClient(dto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update an existing client", description = "Updates the details of an existing client.")
    @ApiResponse(responseCode = "200", description = "Successfully updated client",
            content = @Content(schema = @Schema(implementation = ClientResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Client not found")
    public ResponseEntity<ClientResponseDTO> update(
            @PathVariable String id,
            @Valid @RequestBody ClientRequestDTO dto) {
        return ResponseEntity.ok(clientService.updateClient(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a client by ID", description = "Removes a client from the system.")
    @ApiResponse(responseCode = "204", description = "Successfully deleted client")
    @ApiResponse(responseCode = "404", description = "Client not found")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}