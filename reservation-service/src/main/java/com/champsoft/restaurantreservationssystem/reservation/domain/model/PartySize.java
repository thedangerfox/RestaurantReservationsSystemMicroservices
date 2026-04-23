package com.champsoft.restaurantreservationssystem.reservation.domain.model;

import com.champsoft.restaurantreservationssystem.reservation.domain.exception.InvalidPartySizeException;

public record PartySize(int value) {
    public PartySize {
        if (value < 1) throw new InvalidPartySizeException("Party size must be at least 1");
    }
}
