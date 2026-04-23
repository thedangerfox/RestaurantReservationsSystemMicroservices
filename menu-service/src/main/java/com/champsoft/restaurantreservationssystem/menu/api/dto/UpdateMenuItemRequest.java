package com.champsoft.restaurantreservationssystem.menu.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateMenuItemRequest(
        @NotBlank String name,
        @NotNull BigDecimal price
) {}
