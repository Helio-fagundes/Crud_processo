package br.com.support.car_dealer_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@Table(name = "clients")
public class Client extends Auditable {

    @Id
    private String id;

    private String name;

    @Column(name = "social_security", unique = true)
    private String socialSecurity;

    private String phone;

    @Column(unique = true)
    private String email;

    private String address;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
}
