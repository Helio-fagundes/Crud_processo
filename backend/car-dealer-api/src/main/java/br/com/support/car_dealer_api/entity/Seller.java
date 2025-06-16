package br.com.support.car_dealer_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@Table(name = "sellers")
public class Seller extends Auditable{

    @Id
    private String id;

    private String name;

    @Column(unique = true)
    private String cpf;

    private String phone;

    @Column(unique = true)
    private String email;

    private boolean manager;
}

