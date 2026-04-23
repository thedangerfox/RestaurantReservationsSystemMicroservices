package com.champsoft.restaurantreservationssystem.customer.api.dto;

public record CreateCustomerRequest(
        String fullName,
        String phone
) {}
