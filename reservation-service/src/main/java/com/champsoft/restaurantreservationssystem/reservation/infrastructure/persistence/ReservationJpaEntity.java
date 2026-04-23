package com.champsoft.restaurantreservationssystem.reservation.infrastructure.persistence;

import com.champsoft.restaurantreservationssystem.reservation.domain.model.ReservationStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservations")
public class ReservationJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "customer_id", nullable = false)
    public Long customerId;

    @Column(name = "table_id", nullable = false)
    public Long tableId;

    @Column(name = "reservation_time", nullable = false)
    public LocalDateTime reservationTime;

    @Column(name = "party_size", nullable = false)
    public int partySize;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public ReservationStatus status;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<PreOrderItemJpaEntity> preOrderItems = new ArrayList<>();
}
