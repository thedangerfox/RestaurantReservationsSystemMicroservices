package com.champsoft.restaurantreservationssystem.reservation.api.dto;

import jakarta.validation.constraints.NotNull;

public record PreOrderItemRequest(
        @NotNull Long itemId,
        @NotNull Integer quantity
) {}
