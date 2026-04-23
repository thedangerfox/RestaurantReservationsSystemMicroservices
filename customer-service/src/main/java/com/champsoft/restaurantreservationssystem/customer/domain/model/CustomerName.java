package com.champsoft.restaurantreservationssystem.customer.domain.model;

public record CustomerName(String value) {
    public CustomerName {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("Customer name is required");
        if (value.length() > 120)
            throw new IllegalArgumentException("Customer name too long");
    }
}
