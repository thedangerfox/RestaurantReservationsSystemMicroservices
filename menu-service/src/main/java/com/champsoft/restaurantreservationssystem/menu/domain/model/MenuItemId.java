package com.champsoft.restaurantreservationssystem.menu.domain.model;

public record MenuItemId(Long value) {

    public MenuItemId {
        if (value != null && value <= 0)
            throw new IllegalArgumentException("MenuItemId must be positive");
    }

    public static MenuItemId newId() {
        return new MenuItemId(null); // DB will generate ID
    }

    public static MenuItemId of(Long value) {
        return new MenuItemId(value);
    }
}
