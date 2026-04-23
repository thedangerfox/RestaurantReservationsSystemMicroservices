package com.champsoft.restaurantreservationssystem.table.domain.model;

public record TableNumber(Integer value) {
    public TableNumber {
        if (value == null || value <= 0)
            throw new IllegalArgumentException("Table number must be positive");
    }
}
