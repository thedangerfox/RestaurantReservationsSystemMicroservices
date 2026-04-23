package com.champsoft.restaurantreservationssystem.reservation.domain.model;

public record CustomerRef(Long value) {
    public CustomerRef {
        if (value == null) throw new IllegalArgumentException("customerId is required");
        if (value <= 0) throw new IllegalArgumentException("customerId must be positive");
    }
}
