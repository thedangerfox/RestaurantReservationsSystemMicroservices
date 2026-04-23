package com.champsoft.restaurantreservationssystem.reservation.application.port.out;

public interface TableEligibilityPort {
    boolean isEligible(Long tableId);
}

