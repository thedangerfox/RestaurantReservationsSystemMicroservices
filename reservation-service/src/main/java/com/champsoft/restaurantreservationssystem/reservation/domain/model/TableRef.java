package com.champsoft.restaurantreservationssystem.reservation.domain.model;

public record TableRef(Long value) {
    public TableRef {
        if (value == null) throw new IllegalArgumentException("tableId is required");
        if (value <= 0) throw new IllegalArgumentException("tableId must be positive");
    }
}
