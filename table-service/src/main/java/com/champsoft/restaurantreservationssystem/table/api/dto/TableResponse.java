package com.champsoft.restaurantreservationssystem.table.api.dto;

public record TableResponse(
        Long id,
        Integer tableNumber,
        Integer capacity,
        String status
) {}
