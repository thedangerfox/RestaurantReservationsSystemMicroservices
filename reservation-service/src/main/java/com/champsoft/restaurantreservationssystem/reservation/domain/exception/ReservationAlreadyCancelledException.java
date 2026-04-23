package com.champsoft.restaurantreservationssystem.reservation.domain.exception;

public class ReservationAlreadyCancelledException extends DomainException {
    public ReservationAlreadyCancelledException(String reservationAlreadyCancelled) {
        super("Reservation is already cancelled");
    }
}
