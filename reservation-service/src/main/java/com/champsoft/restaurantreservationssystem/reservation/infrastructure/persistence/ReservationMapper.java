package com.champsoft.restaurantreservationssystem.reservation.infrastructure.persistence;

import com.champsoft.restaurantreservationssystem.reservation.domain.model.*;

import java.util.stream.Collectors;

public class ReservationMapper {

    // -------------------------
    // JPA → DOMAIN
    // -------------------------
    public static Reservation toDomain(ReservationJpaEntity e) {

        var reservation = new Reservation(
                ReservationId.of(e.id.toString()),
                new CustomerRef(e.customerId),
                new TableRef(e.tableId),
                new ReservationTime(e.reservationTime),
                new PartySize(e.partySize)
        );

        // Set status
        if (e.status == ReservationStatus.CANCELLED) {
            reservation.cancel();
        }

        // Add preorder items
        e.preOrderItems.forEach(item ->
                reservation.addPreOrderItem(item.itemId, item.quantity)
        );

        return reservation;
    }

    // -------------------------
    // DOMAIN → JPA
    // -------------------------
    public static ReservationJpaEntity toEntity(Reservation r) {

        var e = new ReservationJpaEntity();

        // ID (convert ReservationId → Long)
        if (r.id() != null) {
            try {
                e.id = Long.valueOf(r.id().value());
            } catch (NumberFormatException ex) {
                // If ID is UUID, leave null (JPA will generate)
                e.id = null;
            }
        }

        e.customerId = r.customerIdValue();
        e.tableId = r.tableIdValue();
        e.reservationTime = r.reservationTimeValue();
        e.partySize = r.partySizeValue();
        e.status = r.status();

        // Preorder items
        e.preOrderItems = r.preOrderItems().stream()
                .map(item -> {
                    var i = new PreOrderItemJpaEntity();
                    i.itemId = item.itemId();
                    i.quantity = item.quantity().value();
                    i.reservation = e;
                    return i;
                })
                .collect(Collectors.toList());

        return e;
    }
}
