package com.champsoft.restaurantreservationssystem.gateway.config;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OpenApiConfigTest {

    @Test
    void gatewayOpenApiContainsExpectedMetadata() {
        var openApi = new OpenApiConfig().gatewayOpenAPI();

        assertThat(openApi.getInfo()).isNotNull();
        assertThat(openApi.getInfo().getTitle()).isEqualTo("RRS API Gateway");
        assertThat(openApi.getInfo().getVersion()).isEqualTo("1.0.0");
        assertThat(openApi.getInfo().getDescription()).isEqualTo("Restaurant Reservation System - REST API");
        assertThat(openApi.getInfo().getContact()).isNotNull();
        assertThat(openApi.getInfo().getContact().getName()).isEqualTo("RRS Team");
        assertThat(openApi.getInfo().getContact().getEmail()).isEqualTo("n/a");
        assertThat(openApi.getInfo().getLicense()).isNotNull();
        assertThat(openApi.getInfo().getLicense().getName()).isEqualTo("Apache 2.0");
    }
}
