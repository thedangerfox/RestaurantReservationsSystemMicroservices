package com.champsoft.restaurantreservationssystem.table.api.dto;

public record CreateTableRequest(
        Integer tableNumber,
        Integer capacity
) {}
