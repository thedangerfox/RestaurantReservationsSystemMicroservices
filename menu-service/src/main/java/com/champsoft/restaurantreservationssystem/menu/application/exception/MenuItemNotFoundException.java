package com.champsoft.restaurantreservationssystem.menu.application.exception;

public class MenuItemNotFoundException extends RuntimeException {
    public MenuItemNotFoundException(Long id) {
        super("Menu item not found: " + id);
    }
}
