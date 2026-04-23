package com.champsoft.restaurantreservationssystem.menu.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiGroupsConfig {



    @Bean
    GroupedOpenApi menuitemApi() {
        return GroupedOpenApi.builder().group("menuitems").pathsToMatch("/api/menu-items/**").build();
    }


}
