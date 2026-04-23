package com.champsoft.restaurantreservationssystem.reservation.application.port.out;

import com.champsoft.restaurantreservationssystem.reservation.domain.model.*;

import java.util.List;
import java.util.Optional;

public interface ReservationRepositoryPort {
    Reservation save(Reservation reservation);
    Optional<Reservation> findById(Long id);
    List<Reservation> findAll();
    void deleteById(Long id);
}

