package com.champsoft.restaurantreservationssystem.menu.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handlesGenericException() {
        var req = new MockHttpServletRequest("GET", "/api/menu-items/1");
        var response = handler.handleAny(new RuntimeException("boom"), req);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody().message()).isEqualTo("boom");
        assertThat(response.getBody().status()).isEqualTo(500);
        assertThat(response.getBody().path()).isEqualTo("/api/menu-items/1");
    }

    @Test
    void handlesNullMessage() {
        var response = handler.handleAny(new RuntimeException(), new MockHttpServletRequest("GET", "/x"));
        assertThat(response.getBody().message()).isEqualTo("Unexpected error");
    }

    @Test
    void apiErrorResponseAccessors() {
        var now = Instant.now();
        var body = new ApiErrorResponse(now, 400, "BAD", "msg", "/p");
        assertThat(body.timestamp()).isEqualTo(now);
        assertThat(body.status()).isEqualTo(400);
        assertThat(body.error()).isEqualTo("BAD");
        assertThat(body.message()).isEqualTo("msg");
        assertThat(body.path()).isEqualTo("/p");
    }
}
