package com.champsoft.restaurantreservationssystem.reservation.api;

import com.champsoft.restaurantreservationssystem.reservation.domain.exception.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationExceptionHandlerTest {

    private final ReservationExceptionHandler handler = new ReservationExceptionHandler();

    @Test
    void notFound() {
        var resp = handler.notFound(new ReservationNotFoundException(99L),
                new MockHttpServletRequest("GET", "/api/reservations/99"));

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(resp.getBody()).isNotNull();
        assertThat(resp.getBody().status()).isEqualTo(404);
        assertThat(resp.getBody().path()).isEqualTo("/api/reservations/99");
        assertThat(resp.getBody().message()).contains("99");
    }

    @Test
    void badRequestForInvalidPartySize() {
        var resp = handler.badRequest(new InvalidPartySizeException("x"),
                new MockHttpServletRequest("POST", "/api/reservations"));
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void badRequestForInvalidReservationTime() {
        var resp = handler.badRequest(new InvalidReservationTimeException("x"),
                new MockHttpServletRequest("POST", "/api/reservations"));
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void badRequestForInvalidPreOrderQuantity() {
        var resp = handler.badRequest(new InvalidPreOrderQuantityException("x"),
                new MockHttpServletRequest("POST", "/api/reservations"));
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void badRequestForIllegalArgument() {
        var resp = handler.badRequest(new IllegalArgumentException("bad"),
                new MockHttpServletRequest("POST", "/api/reservations"));
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(resp.getBody().message()).isEqualTo("bad");
    }
}
