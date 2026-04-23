package com.champsoft.restaurantreservationssystem.reservation.domain.exception;

public class InvalidPreOrderQuantityException extends DomainException {
    public InvalidPreOrderQuantityException(String s) {
        super("Pre-order quantity must be at least 1");
    }
}
