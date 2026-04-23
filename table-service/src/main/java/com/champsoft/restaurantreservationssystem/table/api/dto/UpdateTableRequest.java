package com.champsoft.restaurantreservationssystem.table.api.dto;

public record UpdateTableRequest(
        Integer tableNumber,
        Integer capacity
) {}
