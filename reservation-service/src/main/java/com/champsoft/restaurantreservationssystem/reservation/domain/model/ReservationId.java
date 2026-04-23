package com.champsoft.restaurantreservationssystem.reservation.domain.model;

import java.util.UUID;

public record ReservationId(String value) {
    public ReservationId {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("ReservationId is required");
    }

    public static ReservationId newId() {
        return new ReservationId(UUID.randomUUID().toString());
    }

    public static ReservationId of(String value) {
        return new ReservationId(value);
    }
}
