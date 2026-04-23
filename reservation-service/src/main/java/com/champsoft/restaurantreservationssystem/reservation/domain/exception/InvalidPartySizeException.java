package com.champsoft.restaurantreservationssystem.reservation.domain.exception;

public class InvalidPartySizeException extends DomainException {
    public InvalidPartySizeException(String s) {
        super("Party size must be at least 1");
    }
}
