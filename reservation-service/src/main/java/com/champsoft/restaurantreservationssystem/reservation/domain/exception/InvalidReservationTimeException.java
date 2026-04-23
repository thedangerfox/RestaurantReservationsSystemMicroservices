package com.champsoft.restaurantreservationssystem.reservation.domain.exception;

public class InvalidReservationTimeException extends DomainException {
    public InvalidReservationTimeException(String s) {
        super("Reservation time must be in the future");
    }
}
