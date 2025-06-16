package br.com.support.car_dealer_api.service;

import br.com.support.car_dealer_api.dto.vehicle.VehicleCreateResponseDTO;
import br.com.support.car_dealer_api.dto.vehicle.VehicleRequestDTO;
import br.com.support.car_dealer_api.dto.vehicle.VehicleResponseDTO;
import br.com.support.car_dealer_api.entity.Vehicle;
import br.com.support.car_dealer_api.exception.BusinessException;
import br.com.support.car_dealer_api.exception.ResourceNotFoundException;
import br.com.support.car_dealer_api.mapper.VehicleMapper;
import br.com.support.car_dealer_api.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    private VehicleRepository vehicleRepository;
    private VehicleMapper vehicleMapper;

    public VehicleService(VehicleRepository vehicleRepository, VehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
    }

    @Transactional
    public VehicleCreateResponseDTO createVehicle(VehicleRequestDTO dto){
        var vehicle = vehicleMapper.toEntity(dto);

        Optional<Vehicle> plate = vehicleRepository.findByPlate(dto.getPlate());
        Optional<Vehicle> chassis = vehicleRepository.findByChassis(dto.getChassis());

        if(plate.isPresent() && chassis.isPresent()){
            throw new BusinessException("Veiculo com essa numeração de chassi e placa já cadastrado");
        }
        if(chassis.isPresent() && plate.isEmpty()){
            throw new BusinessException("Esta numeração já está cadastrada em outro veículo, verifique novamente a numeração do chassi");
        }
        if(plate.isPresent() && chassis.isEmpty()){
            throw new BusinessException("Esta placa já está cadastrada em outro veículo, verifique novamente a numeração da placa");
        }
        if(vehicle.getPlate().length() != 7){
            throw new BusinessException("coloque uma numeração de placa válida");
        }
        if(vehicle.getChassis().length() != 17){
            throw new BusinessException("coloque uma numeração de chassi válida");
        }

        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return vehicleMapper.toCreateResponseDTO(savedVehicle);
    }

    @Transactional
    public List<VehicleResponseDTO> getAllVehicles() {
        return vehicleRepository.findAll().stream()
                .map(vehicleMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public  VehicleResponseDTO getVehicleById(String id){
        return vehicleRepository.findById(id)
                .map(vehicleMapper::toResponseDto)
                .orElseThrow(() -> new BusinessException("Veículo não encontrado"));
    }

    @Transactional
    public void deleteVehicle(String id) {
        if (!vehicleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicle", id, Vehicle.class);
        }
        vehicleRepository.deleteById(id);
    }

    @Transactional
    public VehicleResponseDTO updateVehicle(String id, VehicleRequestDTO dto){
        Vehicle existing = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", id, Vehicle.class));

        if (dto.getBrand() != null && dto.getBrand().length() < 3) {
            throw new BusinessException("Marca deve ter ao menos três caracteres");
        }
        if (dto.getModel() != null && dto.getModel().length() < 3) {
            throw new BusinessException("Modelo deve ter ao menos três caracteres");
        }
        if (dto.getFuelType() != null && dto.getFuelType().length() < 3) {
            throw new BusinessException("Tipo de combustível deve ter ao menos três caracteres");
        }
        if (dto.getYear() != 0 && (dto.getYear() < 1886 || dto.getYear() > 2025)) {
            throw new BusinessException("Ano deve ser entre 1886 e 2023");
        }
        if (dto.getPlate() != null && dto.getPlate().length() != 7) {
            throw new BusinessException("Placa deve ter exatamente 7 caracteres");
        }
        if (dto.getChassis() != null && dto.getChassis().length() != 17) {
            throw new BusinessException("Chassi deve ter exatamente 17 caracteres");
        }
        if (dto.getPrice() != 0.0 && dto.getPrice() < 0) {
            throw new BusinessException("Preço não pode ser negativo");
        }
        Vehicle updated = vehicleMapper.updateEntity(existing, dto);
        Vehicle saved = vehicleRepository.save(updated);

        return vehicleMapper.toResponseDto(saved);
    }

}