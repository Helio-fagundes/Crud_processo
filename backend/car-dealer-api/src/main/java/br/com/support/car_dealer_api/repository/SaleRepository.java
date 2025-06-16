package br.com.support.car_dealer_api.repository;

import br.com.support.car_dealer_api.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, String> {
}

