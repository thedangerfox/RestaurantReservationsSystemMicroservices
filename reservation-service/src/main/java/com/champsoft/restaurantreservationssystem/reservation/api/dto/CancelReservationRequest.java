package com.champsoft.restaurantreservationssystem.reservation.api.dto;

import jakarta.validation.constraints.NotNull;

public record CancelReservationRequest(
        @NotNull Long id
) {}
