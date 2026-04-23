package com.champsoft.restaurantreservationssystem.table.domain.exception;

public class TableNotFoundException extends RuntimeException {
    public TableNotFoundException(String message) {
        super(message);
    }
}
