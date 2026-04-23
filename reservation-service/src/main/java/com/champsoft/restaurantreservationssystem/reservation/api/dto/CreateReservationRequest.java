package com.champsoft.restaurantreservationssystem.reservation.api.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record CreateReservationRequest(
        @NotNull Long customerId,
        @NotNull Long tableId,
        @NotNull LocalDateTime reservationTime,
        @NotNull Integer partySize,
        List<PreOrderItemRequest> preOrderItems
) {}