package br.com.support.car_dealer_api.mapper;

import br.com.support.car_dealer_api.dto.vehicle.VehicleCreateResponseDTO;
import br.com.support.car_dealer_api.dto.vehicle.VehicleRequestDTO;
import br.com.support.car_dealer_api.dto.vehicle.VehicleResponseDTO;
import br.com.support.car_dealer_api.entity.Vehicle;
import br.com.support.car_dealer_api.util.UUIDUtil;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    public Vehicle toEntity(VehicleRequestDTO dto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(UUIDUtil.newUUID());
        vehicle.setBrand(dto.getBrand());
        vehicle.setModel(dto.getModel());
        vehicle.setYear(dto.getYear());
        vehicle.setColor(dto.getColor());
        vehicle.setPlate(dto.getPlate());
        vehicle.setChassis(dto.getChassis());
        vehicle.setFuelType(dto.getFuelType());
        vehicle.setPrice(dto.getPrice());
        vehicle.setStatus(dto.getStatus());
        vehicle.setReleased(dto.isReleased());
        return vehicle;
    }

    public VehicleCreateResponseDTO toCreateResponseDTO(Vehicle vehicle) {
        VehicleCreateResponseDTO dto = new VehicleCreateResponseDTO();
        dto.setId(vehicle.getId());
        return dto;
    }

    public VehicleResponseDTO toResponseDto(Vehicle vehicle) {
        VehicleResponseDTO dto = new VehicleResponseDTO();
        dto.setId(vehicle.getId());
        dto.setBrand(vehicle.getBrand());
        dto.setModel(vehicle.getModel());
        dto.setYear(vehicle.getYear());
        dto.setColor(vehicle.getColor());
        dto.setPlate(vehicle.getPlate());
        dto.setChassis(vehicle.getChassis());
        dto.setFuelType(vehicle.getFuelType());
        dto.setPrice(vehicle.getPrice());
        dto.setStatus(vehicle.getStatus());
        dto.setReleased(vehicle.isReleased());
        return dto;
    }

    public Vehicle updateEntity(Vehicle entity, VehicleRequestDTO dto) {
        if (dto.getBrand() != null) {
            entity.setBrand(dto.getBrand());
        }
        if (dto.getModel() != null) {
            entity.setModel(dto.getModel());
        }
        if (dto.getYear() != 0) {
            entity.setYear(dto.getYear());
        }
        if (dto.getColor() != null) {
            entity.setColor(dto.getColor());
        }
        if (dto.getPlate() != null) {
            entity.setPlate(dto.getPlate());
        }
        if (dto.getChassis() != null) {
            entity.setChassis(dto.getChassis());
        }
        if (dto.getFuelType() != null) {
            entity.setFuelType(dto.getFuelType());
        }
        if (dto.getPrice() != 0.0) {
            entity.setPrice(dto.getPrice());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        entity.setReleased(dto.isReleased());

        return entity;
    }
}
