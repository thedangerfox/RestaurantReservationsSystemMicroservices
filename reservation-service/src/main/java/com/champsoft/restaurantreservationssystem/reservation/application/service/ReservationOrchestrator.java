package com.champsoft.restaurantreservationssystem.reservation.application.service;

import com.champsoft.restaurantreservationssystem.reservation.api.dto.PreOrderItemRequest;
import com.champsoft.restaurantreservationssystem.reservation.application.port.out.CustomerEligibilityPort;
import com.champsoft.restaurantreservationssystem.reservation.application.port.out.TableEligibilityPort;
import com.champsoft.restaurantreservationssystem.reservation.application.port.out.MenuItemEligibilityPort;
import com.champsoft.restaurantreservationssystem.reservation.domain.model.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationOrchestrator {

    private final CustomerEligibilityPort customerPort;
    private final TableEligibilityPort tablePort;
    private final MenuItemEligibilityPort menuPort;

    public ReservationOrchestrator(
            CustomerEligibilityPort customerPort,
            TableEligibilityPort tablePort,
            MenuItemEligibilityPort menuPort
    ) {
        this.customerPort = customerPort;
        this.tablePort = tablePort;
        this.menuPort = menuPort;
    }

    @Transactional
    public Reservation create(
            Long customerId,
            Long tableId,
            LocalDateTime reservationTime,
            Integer partySize,
            List<PreOrderItemRequest> preOrderItems
    ) {

        // Build reservation aggregate
        var reservation = new Reservation(
                ReservationId.newId(),
                new CustomerRef(customerId),
                new TableRef(tableId),
                new ReservationTime(reservationTime),
                new PartySize(partySize)
        );

        // Add preorder items
        if (preOrderItems != null) {
            for (var item : preOrderItems) {
                reservation.addPreOrderItem(item.itemId(), item.quantity());
            }
        }

        // For now, just return it (no persistence yet)
        return reservation;
    }
}
