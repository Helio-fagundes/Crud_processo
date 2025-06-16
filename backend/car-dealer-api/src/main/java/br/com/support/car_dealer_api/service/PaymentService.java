package br.com.support.car_dealer_api.service;

import br.com.support.car_dealer_api.dto.payment.PaymentCreateResponseDTO;
import br.com.support.car_dealer_api.dto.payment.PaymentRequestDTO;
import br.com.support.car_dealer_api.dto.payment.PaymentResponseDTO;
import br.com.support.car_dealer_api.entity.Payment;
import br.com.support.car_dealer_api.mapper.PaymenteMapper;
import br.com.support.car_dealer_api.repository.PaymentRepository;
import br.com.support.car_dealer_api.repository.SaleRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;
    private SaleRepository saleRepository;
    private PaymenteMapper paymenteMapper;

    public PaymentService(PaymentRepository paymentRepository, SaleRepository saleRepository, PaymenteMapper paymenteMapper) {
        this.paymentRepository = paymentRepository;
        this.saleRepository = saleRepository;
        this.paymenteMapper = paymenteMapper;
    }

    private void validatePaymentRequest(PaymentRequestDTO dto) {
        String saleId = dto.getSaleId();

        if (saleRepository.findById(saleId).isEmpty()) {
            throw new IllegalArgumentException("Venda não encontrada");
        }
        if (dto.getAmount() <= 0) {
            throw new IllegalArgumentException("O valor do pagamento deve ser maior que zero");
        }
        if(dto.getAmount() < saleRepository.findById(saleId).get().getSalePrice()) {
            throw new IllegalArgumentException("O valor do pagamento não pode ser menor que o valor da venda");
        }
        if (dto.getPaymentMethod() == null || dto.getPaymentMethod().isEmpty()) {
            throw new IllegalArgumentException("O método de pagamento não pode ser nulo");
        }
        if (dto.getPaymentType() == null || dto.getPaymentType().isEmpty()) {
            throw new IllegalArgumentException("O tipo de pagamento não pode ser nulo");
        }
    }

    @Transactional
    public PaymentCreateResponseDTO createPayment(PaymentRequestDTO dto) {

        validatePaymentRequest(dto);

        var payment = paymenteMapper.toEntity(dto);
        paymentRepository.save(payment);
        return paymenteMapper.paymentCreateResponseDTO(payment);
    }

    @Transactional
    public List<PaymentResponseDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(paymenteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PaymentResponseDTO getPaymentsById (String id) {
        return paymentRepository.findById(id)
                .map(paymenteMapper::toResponseDTO)
                .orElseThrow(() -> new IllegalArgumentException("Pagamento não encontrado"));
    }

    @Transactional
    public void deletePayment(String id) {
        if (!paymentRepository.existsById(id)) {
            throw new IllegalArgumentException("Pagamento não encontrado");
        }
        paymentRepository.deleteById(id);
    }

    @Transactional
    public PaymentResponseDTO updatePayment(String id, PaymentRequestDTO dto) {
        Payment existing = paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pagamento não encontrado"));

        validatePaymentRequest(dto);

        Payment updated = paymenteMapper.updateEntity(existing, dto);
        paymentRepository.save(updated);
        return paymenteMapper.toResponseDTO(updated);
    }

}
