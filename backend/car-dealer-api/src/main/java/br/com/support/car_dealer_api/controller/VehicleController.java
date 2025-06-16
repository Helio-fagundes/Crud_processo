package br.com.support.car_dealer_api.controller;

import br.com.support.car_dealer_api.dto.vehicle.VehicleCreateResponseDTO;
import br.com.support.car_dealer_api.dto.vehicle.VehicleRequestDTO;
import br.com.support.car_dealer_api.dto.vehicle.VehicleResponseDTO;
import br.com.support.car_dealer_api.service.VehicleService;
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
@RequestMapping("/vehicles")
@Tag(name = "Vehicle Management", description = "Operations related to managing vehicles in the car dealer system.")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    @Operation(summary = "Get all Vehicles", description = "Returns a list of all registered vehicles.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of vehicles",
            content = @Content(schema = @Schema(implementation = VehicleResponseDTO.class)))
    public ResponseEntity<List<VehicleResponseDTO>> getAll() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get vehicle by ID", description = "Returns a single vehicle based on the provided ID.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved vehicle",
            content = @Content(schema = @Schema(implementation = VehicleResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Vehicle not found")
    public ResponseEntity<VehicleResponseDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(vehicleService.getVehicleById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new Vehicle", description = "Registers a new vehicle in the system.")
    @ApiResponse(responseCode = "200", description = "Successfully created vehicle",
            content = @Content(schema = @Schema(implementation = VehicleResponseDTO.class)))
    public ResponseEntity<VehicleCreateResponseDTO> create(@RequestBody @Valid VehicleRequestDTO dto) {
        VehicleCreateResponseDTO response = vehicleService.createVehicle(dto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update an existing vehicle", description = "Updates the details of an existing vehicle.")
    @ApiResponse(responseCode = "200", description = "Successfully updated vehicle",
            content = @Content(schema = @Schema(implementation = VehicleResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Vehicle not found")
    public ResponseEntity<VehicleResponseDTO> update(
            @PathVariable String id,
            @RequestBody @Valid VehicleRequestDTO dto) {
        return ResponseEntity.ok(vehicleService.updateVehicle(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a vehicle by ID", description = "Removes a vehicle from the system.")
    @ApiResponse(responseCode = "204", description = "Successfully deleted vehicle")
    @ApiResponse(responseCode = "404", description = "vehicle not found")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

}
