package com.champsoft.restaurantreservationssystem.customer.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class CustomerJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, name = "full_name")
    public String fullName;

    @Column(nullable = false)
    public String phone;

    @Column(nullable = false)
    public String status;
}
