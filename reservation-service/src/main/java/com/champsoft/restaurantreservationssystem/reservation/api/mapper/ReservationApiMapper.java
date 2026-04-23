package com.champsoft.restaurantreservationssystem.reservation.api.mapper;

import com.champsoft.restaurantreservationssystem.reservation.api.dto.*;
import com.champsoft.restaurantreservationssystem.reservation.domain.model.Reservation;

import java.util.stream.Collectors;

public class ReservationApiMapper {

    public static ReservationResponse toResponse(Reservation r) {
        return new ReservationResponse(
                r.id().value(),                         // ReservationId → String
                r.customerIdValue(),                    // CustomerRef → Long
                r.tableIdValue(),                       // TableRef → Long
                r.reservationTimeValue(),               // ReservationTime → LocalDateTime
                r.partySizeValue(),                     // PartySize → int
                r.status(),                             // ReservationStatus enum
                r.preOrderItems().stream()
                        .map(i -> new PreOrderItemResponse(
                                i.itemId(),
                                i.quantity().value()
                        ))
                        .collect(Collectors.toList())
        );
    }
}
