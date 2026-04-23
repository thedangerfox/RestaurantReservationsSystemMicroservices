package com.champsoft.restaurantreservationssystem.reservation.domain.model;

import com.champsoft.restaurantreservationssystem.reservation.domain.exception.InvalidPreOrderQuantityException;

public record PreOrderQuantity(int value) {
    public PreOrderQuantity {
        if (value < 1) throw new InvalidPreOrderQuantityException("Quantity must be at least 1");
    }
}
