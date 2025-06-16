package br.com.support.car_dealer_api.repository;

import br.com.support.car_dealer_api.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    Optional<Vehicle> findByPlate(String licensePlate);
    Optional<Vehicle> findByChassis(String chassis);
}
