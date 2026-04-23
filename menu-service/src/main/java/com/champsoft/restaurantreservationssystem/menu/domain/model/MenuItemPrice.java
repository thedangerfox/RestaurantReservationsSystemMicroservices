package com.champsoft.restaurantreservationssystem.menu.domain.model;

import java.math.BigDecimal;

public record MenuItemPrice(BigDecimal value) {
    public MenuItemPrice {
        if (value == null)
            throw new IllegalArgumentException("Price is required");
        if (value.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Price must be positive");
    }
}
