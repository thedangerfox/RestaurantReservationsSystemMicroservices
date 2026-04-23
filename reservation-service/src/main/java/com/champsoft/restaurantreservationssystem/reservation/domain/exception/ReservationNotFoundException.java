package com.champsoft.restaurantreservationssystem.reservation.domain.exception;

public class ReservationNotFoundException extends DomainException {
    public ReservationNotFoundException(Long id) {
        super("Reservation not found: " + id);
    }
}
