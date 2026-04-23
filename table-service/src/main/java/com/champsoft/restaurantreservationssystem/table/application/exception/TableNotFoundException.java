package com.champsoft.restaurantreservationssystem.table.application.exception;

public class TableNotFoundException extends RuntimeException {
    public TableNotFoundException(Long id) {
        super("Table not found: " + id);
    }
}
