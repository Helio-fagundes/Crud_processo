package br.com.support.car_dealer_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "payments")
public class Payment extends Auditable {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "sale", nullable = false)
    private Sale saleId;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "payment_method")
    private String paymentMethod;

    private boolean installment;

    private double amount;

    private boolean received;

    private String movement;

}
