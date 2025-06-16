package br.com.support.car_dealer_api.controller;

import br.com.support.car_dealer_api.dto.client.ClientResponseDTO;
import br.com.support.car_dealer_api.dto.seller.SellerCreateResponseDTO;
import br.com.support.car_dealer_api.dto.seller.SellerRequestDTO;
import br.com.support.car_dealer_api.dto.seller.SellerResponseDTO;
import br.com.support.car_dealer_api.service.SellerService;
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
@RequestMapping("/sellers")
@Tag(name = "Seller Management", description = "Operations related to managing sellers in the car dealer system.")
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @GetMapping
    @Operation(summary = "Get all Sellers", description = "Returns a list of all registered Sellers.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of sellers",
            content = @Content(schema = @Schema(implementation = SellerResponseDTO.class)))
    public ResponseEntity<List<SellerResponseDTO>> getAll() {
        return ResponseEntity.ok(sellerService.getAllSellers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Seller by ID", description = "Returns a single Seller based on the provided ID.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved Seller",
            content = @Content(schema = @Schema(implementation = SellerResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Seller not found")
    public ResponseEntity<SellerResponseDTO> getById(
            @Parameter(description = "ID of the Seller to retrieve", example = "1") @PathVariable String id) {
        return ResponseEntity.ok(sellerService.getSellerById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new Seller", description = "Registers a new seller in the system.")
    @ApiResponse(responseCode = "200", description = "Successfully created seller",
            content = @Content(schema = @Schema(implementation = SellerResponseDTO.class)))
    public ResponseEntity<SellerCreateResponseDTO> create(@RequestBody @Valid SellerRequestDTO dto) {
        SellerCreateResponseDTO response = sellerService.createSeller(dto);
        return ResponseEntity.ok(response);

    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update an existing client", description = "Updates the details of an existing client.")
    @ApiResponse(responseCode = "200", description = "Successfully updated client",
            content = @Content(schema = @Schema(implementation = ClientResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Client not found")
    public ResponseEntity<SellerResponseDTO> update(
            @PathVariable String id,
            @RequestBody @Valid SellerRequestDTO dto) {
        return ResponseEntity.ok(sellerService.updateSeller(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a seller by ID", description = "Removes a seller from the system.")
    @ApiResponse(responseCode = "204", description = "Successfully deleted seller")
    @ApiResponse(responseCode = "404", description = "seller not found")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        sellerService.deleteSeller(id);
        return ResponseEntity.noContent().build();
    }

}
