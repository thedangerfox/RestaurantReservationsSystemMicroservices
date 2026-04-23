package com.champsoft.restaurantreservationssystem.reservation.domain.model;

public record PreOrderItem(Long itemId, PreOrderQuantity quantity) {
    public PreOrderItem {
        if (itemId == null || itemId <= 0)
            throw new IllegalArgumentException("itemId is required");
    }
}
