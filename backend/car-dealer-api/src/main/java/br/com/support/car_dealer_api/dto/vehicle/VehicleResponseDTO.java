package br.com.support.car_dealer_api.dto.vehicle;

import lombok.Data;

@Data
public class VehicleResponseDTO {
    private String id;
    private  String brand;
    private String model;
    private int year;
    private String color;
    private String plate;
    private String chassis;
    private String fuelType;
    private double price;
    private String status;
    private boolean released;

    public void setId(String id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public void setChassis(String chassis) {
        this.chassis = chassis;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }
}
