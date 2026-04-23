package com.champsoft.restaurantreservationssystem.menu.infrastructure.persistence;

import com.champsoft.restaurantreservationssystem.menu.domain.model.*;

public class MenuItemMapper {

    public static MenuItem toDomain(MenuItemJpaEntity e) {
        return new MenuItem(
                new MenuItemId(e.id),
                new MenuItemName(e.name),
                new MenuItemPrice(e.price)

        );
    }

    public static MenuItemJpaEntity toEntity(MenuItem item) {
        var e = new MenuItemJpaEntity();
        e.id = item.id() != null ? item.id().value() : null;
        e.name = item.name().value();
        e.price = item.price().value();
        e.status = item.status();
        return e;
    }
}

