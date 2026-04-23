package com.champsoft.restaurantreservationssystem.menu.domain.model;

public record MenuItemName(String value) {
    public MenuItemName {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("Menu item name is required");
        if (value.length() > 120)
            throw new IllegalArgumentException("Menu item name too long");
    }
}
