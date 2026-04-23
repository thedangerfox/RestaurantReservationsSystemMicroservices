package com.champsoft.restaurantreservationssystem.reservation.infrastructure.persistence;


import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataReservationRepository
        extends JpaRepository<ReservationJpaEntity, Long> {
}

