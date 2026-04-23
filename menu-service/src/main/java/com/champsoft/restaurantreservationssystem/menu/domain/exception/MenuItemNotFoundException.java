package com.champsoft.restaurantreservationssystem.menu.domain.exception;

public class MenuItemNotFoundException extends RuntimeException {
    public MenuItemNotFoundException(String message) {
        super(message);
    }
}
