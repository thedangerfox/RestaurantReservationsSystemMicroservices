package com.champsoft.restaurantreservationssystem.reservation.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiGroupsConfig {



    @Bean
    GroupedOpenApi reservationApi() {
        return GroupedOpenApi.builder().group("reservations").pathsToMatch("/api/reservations/**").build();
    }
}
