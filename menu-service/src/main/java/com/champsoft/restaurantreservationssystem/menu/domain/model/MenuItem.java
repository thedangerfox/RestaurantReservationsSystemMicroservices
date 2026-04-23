package com.champsoft.restaurantreservationssystem.menu.domain.model;

public class MenuItem {

    private final MenuItemId id;
    private MenuItemName name;
    private MenuItemPrice price;
    private MenuItemStatus status;

    public MenuItem(MenuItemId id, MenuItemName name, MenuItemPrice price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = MenuItemStatus.ACTIVE;
    }

    public MenuItemId id() { return id; }
    public MenuItemName name() { return name; }
    public MenuItemPrice price() { return price; }
    public MenuItemStatus status() { return status; }

    public void update(MenuItemName newName, MenuItemPrice newPrice) {
        this.name = newName;
        this.price = newPrice;
    }

    public void deactivate() {
        this.status = MenuItemStatus.INACTIVE;
    }
}
