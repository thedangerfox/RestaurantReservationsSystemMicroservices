package com.champsoft.restaurantreservationssystem.table.domain.model;

public record TableCapacity(Integer value) {
    public TableCapacity {
        if (value == null || value <= 0)
            throw new IllegalArgumentException("Capacity must be positive");
    }
}
