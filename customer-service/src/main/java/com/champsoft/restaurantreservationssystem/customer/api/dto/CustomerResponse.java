package com.champsoft.restaurantreservationssystem.customer.api.dto;

public record CustomerResponse(
        Long id,
        String fullName,
        String phone,
        String status
) {}
