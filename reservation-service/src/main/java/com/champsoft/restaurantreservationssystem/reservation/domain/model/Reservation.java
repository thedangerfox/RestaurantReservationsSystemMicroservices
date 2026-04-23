package com.champsoft.restaurantreservationssystem.reservation.domain.model;

import com.champsoft.restaurantreservationssystem.reservation.domain.exception.ReservationAlreadyCancelledException;

import java.util.ArrayList;
import java.util.List;

public class Reservation {

    private final ReservationId id;
    private final CustomerRef customerId;
    private final TableRef tableId;
    private ReservationTime reservationTime;
    private PartySize partySize;
    private ReservationStatus status;
    private final List<PreOrderItem> preOrderItems = new ArrayList<>();

    public Reservation(
            ReservationId id,
            CustomerRef customerId,
            TableRef tableId,
            ReservationTime reservationTime,
            PartySize partySize
    ) {
        this.id = id;
        this.customerId = customerId;
        this.tableId = tableId;
        this.reservationTime = reservationTime;
        this.partySize = partySize;
        this.status = ReservationStatus.PENDING;
    }

    public void addPreOrderItem(Long itemId, int quantity) {
        preOrderItems.add(new PreOrderItem(itemId, new PreOrderQuantity(quantity)));
    }

    public void update(ReservationTime newTime, PartySize newPartySize, List<PreOrderItem> newItems) {
        this.reservationTime = newTime;
        this.partySize = newPartySize;
        this.preOrderItems.clear();
        this.preOrderItems.addAll(newItems);
    }

    public void cancel() {
        if (status == ReservationStatus.CANCELLED)
            throw new ReservationAlreadyCancelledException("Reservation already cancelled");
        this.status = ReservationStatus.CANCELLED;
    }

    // getters
    public ReservationId id() { return id; }
    public CustomerRef customerId() { return customerId; }
    public TableRef tableId() { return tableId; }
    public ReservationTime reservationTime() { return reservationTime; }
    public PartySize partySize() { return partySize; }
    public ReservationStatus status() { return status; }
    public List<PreOrderItem> preOrderItems() { return preOrderItems; }

    // convenience accessors for API
    public Long customerIdValue() { return customerId.value(); }
    public Long tableIdValue() { return tableId.value(); }
    public int partySizeValue() { return partySize.value(); }
    public java.time.LocalDateTime reservationTimeValue() { return reservationTime.value(); }
}
