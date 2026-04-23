package com.champsoft.restaurantreservationssystem.reservation.api.dto;

import com.champsoft.restaurantreservationssystem.reservation.domain.model.ReservationStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ReservationResponse(
        String id,
        Long customerId,
        Long tableId,
        LocalDateTime reservationTime,
        int partySize,
        ReservationStatus status,
        List<PreOrderItemResponse> preOrderItems
) {}
