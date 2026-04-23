package com.champsoft.restaurantreservationssystem.customer.domain.model;

public record CustomerPhone(String value) {
    public CustomerPhone {
        if (value != null && value.length() > 20)
            throw new IllegalArgumentException("Phone number too long");
    }
}
