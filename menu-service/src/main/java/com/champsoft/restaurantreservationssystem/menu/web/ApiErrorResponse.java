package com.champsoft.restaurantreservationssystem.menu.web;

import java.time.Instant;

public record ApiErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path
) {}
