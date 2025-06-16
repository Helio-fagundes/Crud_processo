package br.com.support.car_dealer_api.controller;

import br.com.support.car_dealer_api.dto.payment.PaymentCreateResponseDTO;
import br.com.support.car_dealer_api.dto.payment.PaymentRequestDTO;
import br.com.support.car_dealer_api.dto.payment.PaymentResponseDTO;
import br.com.support.car_dealer_api.service.PaymentService;
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
@RequestMapping("/payments")
@Tag(name = "Payment Management", description = "Operations related to managing payments in the car dealer system.")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    @Operation(summary = "Get all Payment", description = "Returns a list of all registered payment.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of payments",
            content = @Content(schema = @Schema(implementation = PaymentResponseDTO.class)))
    public ResponseEntity<List<PaymentResponseDTO>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get payment by ID", description = "Returns a single payment based on the provided ID.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved payment",
            content = @Content(schema = @Schema(implementation = PaymentResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "payment not found")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable String id) {
        return ResponseEntity.ok(paymentService.getPaymentsById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new payment", description = "Registers a new payment in the system.")
    @ApiResponse(responseCode = "200", description = "Successfully payment created",
            content = @Content(schema = @Schema(implementation = PaymentResponseDTO.class)))
    public ResponseEntity<PaymentCreateResponseDTO> createPayment(@RequestBody @Valid PaymentRequestDTO dto) {
        PaymentCreateResponseDTO createdPayment = paymentService.createPayment(dto);
        return ResponseEntity.ok(createdPayment);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update an existing payment", description = "Updates the details of an existing payment.")
    @ApiResponse(responseCode = "200", description = "Successfully updated payment",
            content = @Content(schema = @Schema(implementation = PaymentResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "payment not found")
    public ResponseEntity<PaymentResponseDTO> updatePayment(@PathVariable String id, @RequestBody @Valid PaymentRequestDTO dto) {
        PaymentResponseDTO updatedPayment = paymentService.updatePayment(id, dto);
        return ResponseEntity.ok(updatedPayment);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a payment by ID", description = "Removes a payment from the system.")
    @ApiResponse(responseCode = "204", description = "Successfully deleted payment")
    @ApiResponse(responseCode = "404", description = "payment not found")
    public ResponseEntity<Void> deletePayment(@PathVariable String id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

}
