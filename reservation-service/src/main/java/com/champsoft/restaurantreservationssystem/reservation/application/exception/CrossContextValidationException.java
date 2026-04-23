package com.champsoft.restaurantreservationssystem.reservation.application.exception;

public class CrossContextValidationException extends RuntimeException {
    public CrossContextValidationException(String message) {
        super(message);
    }
}
