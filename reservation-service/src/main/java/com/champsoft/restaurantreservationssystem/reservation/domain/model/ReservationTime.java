package com.champsoft.restaurantreservationssystem.reservation.domain.model;

import com.champsoft.restaurantreservationssystem.reservation.domain.exception.InvalidReservationTimeException;

import java.time.LocalDateTime;

public record ReservationTime(LocalDateTime value) {
    public ReservationTime {
        if (value == null) throw new InvalidReservationTimeException("Reservation time is required");
        if (value.isBefore(LocalDateTime.now()))
            throw new InvalidReservationTimeException("Reservation time must be in the future");
    }
}
