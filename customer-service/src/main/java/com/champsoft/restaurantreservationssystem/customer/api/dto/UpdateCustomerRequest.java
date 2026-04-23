package com.champsoft.restaurantreservationssystem.customer.api.dto;

public record UpdateCustomerRequest(
        String fullName,
        String phone
) {}
