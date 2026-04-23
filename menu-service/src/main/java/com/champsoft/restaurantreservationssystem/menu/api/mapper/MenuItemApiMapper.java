package com.champsoft.restaurantreservationssystem.menu.api.mapper;

import com.champsoft.restaurantreservationssystem.menu.api.dto.*;
import com.champsoft.restaurantreservationssystem.menu.domain.model.MenuItem;

public class MenuItemApiMapper {



    public static MenuItemResponse toResponse(MenuItem item) {
        return new MenuItemResponse(
                item.id().value(),
                item.name().value(),
                item.price().value(),
                item.status().name()
        );
    }
}
