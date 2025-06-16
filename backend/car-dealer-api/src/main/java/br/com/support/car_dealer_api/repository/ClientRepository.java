package br.com.support.car_dealer_api.repository;

import br.com.support.car_dealer_api.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, String> {
    Optional<Client>  findByEmail(String email);
    Optional<Client> findBySocialSecurity(String socialSecurity);
}