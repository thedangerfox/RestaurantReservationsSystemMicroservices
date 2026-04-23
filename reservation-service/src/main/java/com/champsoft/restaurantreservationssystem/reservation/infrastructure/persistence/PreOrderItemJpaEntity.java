package com.champsoft.restaurantreservationssystem.reservation.infrastructure.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "pre_order_items")
public class PreOrderItemJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "item_id", nullable = false)
    public Long itemId;

    @Column(nullable = false)
    public int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    public ReservationJpaEntity reservation;
}
