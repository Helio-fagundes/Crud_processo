package br.com.support.car_dealer_api.controller;

import br.com.support.car_dealer_api.dto.sale.SaleCreateResponseDTO;
import br.com.support.car_dealer_api.dto.sale.SaleRequestDTO;
import br.com.support.car_dealer_api.dto.sale.SaleResponseDTO;
import br.com.support.car_dealer_api.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
@Tag(name = "Sale Management", description = "Operations related to managing sales in the car dealer system.")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    @Operation(summary = "Get all sale", description = "Returns a list of all registered sale.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of sales",
            content = @Content(schema = @Schema(implementation = SaleResponseDTO.class)))
    public ResponseEntity<List<SaleResponseDTO>> getAll() {
        return ResponseEntity.ok(saleService.getAllSales());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get sale by ID", description = "Returns a single sale based on the provided ID.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved sale",
            content = @Content(schema = @Schema(implementation = SaleResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "sale not found")
    public ResponseEntity<SaleResponseDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(saleService.getSaleById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new sale", description = "Registers a new sale in the system.")
    @ApiResponse(responseCode = "200", description = "Successfully sale created",
            content = @Content(schema = @Schema(implementation = SaleResponseDTO.class)))
    public ResponseEntity<SaleCreateResponseDTO> create(@RequestBody @Valid SaleRequestDTO dto) {
        SaleCreateResponseDTO response = saleService.createSale(dto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update an existing sale", description = "Updates the details of an existing sale.")
    @ApiResponse(responseCode = "200", description = "Successfully updated sale",
            content = @Content(schema = @Schema(implementation = SaleResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "sale not found")
    public ResponseEntity<SaleResponseDTO> update(
            @PathVariable String id,
            @RequestBody SaleRequestDTO dto) {
        return ResponseEntity.ok(saleService.updateSale(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a sale by ID", description = "Removes a sale from the system.")
    @ApiResponse(responseCode = "204", description = "Successfully deleted sale")
    @ApiResponse(responseCode = "404", description = "sale not found")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }

}
