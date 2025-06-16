package br.com.support.car_dealer_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "vehicles")
public class Vehicle extends Auditable {

    @Id
    private String id;

    private String brand;
    private String model;
    private int year;
    private String color;

    @Column(unique = true)
    private String plate;

    @Column(unique = true)
    private String chassis;

    @Column(name = "fuel_type")
    private String fuelType;

    private double price;
    private String status;
    private boolean released;
}
