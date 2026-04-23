package com.champsoft.restaurantreservationssystem.table.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiGroupsConfig {



    @Bean
    GroupedOpenApi tableApi() {
        return GroupedOpenApi.builder().group("tables").pathsToMatch("/api/tables/**").build();
    }

}
