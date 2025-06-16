package br.com.support.car_dealer_api.repository;

import br.com.support.car_dealer_api.entity.Payment;
import br.com.support.car_dealer_api.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, String> {
    List<Payment> findBySaleId(Sale saleId);
}