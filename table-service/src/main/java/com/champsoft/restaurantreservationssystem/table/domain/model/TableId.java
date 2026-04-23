package com.champsoft.restaurantreservationssystem.table.domain.model;

public record TableId(Long value) {

    public TableId {
        if (value != null && value <= 0) {
            throw new IllegalArgumentException("TableId must be positive");
        }
    }

    public static TableId newId() {
        return new TableId(null); // DB will generate ID
    }

    public static TableId of(Long value) {
        return new TableId(value);
    }
}
