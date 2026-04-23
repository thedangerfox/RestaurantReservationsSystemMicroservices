package com.champsoft.restaurantreservationssystem.table.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI tablesServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Tables Service API")
                        .version("1.0.0")
                        .description("Restaurant Reservation System - REST API")
                        .contact(new Contact().name("RRS Team").email("n/a"))
                        .license(new License().name("Apache 2.0")));
    }
}
