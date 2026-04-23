package com.champsoft.restaurantreservationssystem.menu.api.dto;

import java.math.BigDecimal;

public record MenuItemResponse(
        Long id,
        String name,
        BigDecimal price,
        String status
) {}
