package com.champsoft.restaurantreservationssystem.reservation.application.port.out;

public interface CustomerEligibilityPort {
    boolean isEligible(Long customerId);
}


