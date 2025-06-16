package br.com.support.car_dealer_api.dto.vehicle;

public class VehicleRequestDTO {
    private String brand;
    private String model;
    private int year;
    private String color;
    private String plate;
    private String chassis;
    private String fuelType;
    private double price;
    private String status;
    private boolean released;

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public boolean isReleased() {
        return released;
    }

    public String getStatus() {
        return status;
    }

    public double getPrice() {
        return price;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getChassis() {
        return chassis;
    }

    public String getPlate() {
        return plate;
    }

    public String getColor() {
        return color;
    }

    public int getYear() {
        return year;
    }
}
