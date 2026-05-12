package com.champsoft.restaurantreservationssystem.customer.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleAnyReturns500WithMessage() {
        var req = new MockHttpServletRequest("GET", "/api/customers/1");

        var response = handler.handleAny(new RuntimeException("boom"), req);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(500);
        assertThat(response.getBody().message()).isEqualTo("boom");
        assertThat(response.getBody().path()).isEqualTo("/api/customers/1");
        assertThat(response.getBody().error()).isEqualTo("INTERNAL_SERVER_ERROR");
        assertThat(response.getBody().timestamp()).isNotNull();
    }

    @Test
    void handleAnyHandlesNullMessage() {
        var req = new MockHttpServletRequest("GET", "/x");

        var response = handler.handleAny(new RuntimeException(), req);

        assertThat(response.getBody().message()).isEqualTo("Unexpected error");
    }

    @Test
    void apiErrorResponseAccessors() {
        var now = java.time.Instant.now();
        var body = new ApiErrorResponse(now, 400, "BAD", "msg", "/p");
        assertThat(body.timestamp()).isEqualTo(now);
        assertThat(body.status()).isEqualTo(400);
        assertThat(body.error()).isEqualTo("BAD");
        assertThat(body.message()).isEqualTo("msg");
        assertThat(body.path()).isEqualTo("/p");
    }
}
