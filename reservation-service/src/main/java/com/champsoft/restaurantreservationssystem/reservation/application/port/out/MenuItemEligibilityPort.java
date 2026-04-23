package com.champsoft.restaurantreservationssystem.reservation.application.port.out;

public interface MenuItemEligibilityPort {
    boolean isEligible(Long menuItemId);
}

