package br.com.support.car_dealer_api.repository;

import br.com.support.car_dealer_api.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, String> {
    Optional<Seller> findByCpf(String cpf);
    Optional<Seller> findByEmail(String email);
}