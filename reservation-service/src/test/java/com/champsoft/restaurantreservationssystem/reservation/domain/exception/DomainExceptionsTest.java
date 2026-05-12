package com.champsoft.restaurantreservationssystem.reservation.domain.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DomainExceptionsTest {

    @Test
    void invalidPartySize() {
        var ex = new InvalidPartySizeException("any input");
        assertThat(ex).hasMessage("Party size must be at least 1");
        assertThat(ex).isInstanceOf(DomainException.class);
    }

    @Test
    void invalidPreOrderQuantity() {
        var ex = new InvalidPreOrderQuantityException("ignored");
        assertThat(ex).hasMessage("Pre-order quantity must be at least 1");
    }

    @Test
    void invalidReservationTime() {
        var ex = new InvalidReservationTimeException("ignored");
        assertThat(ex).hasMessage("Reservation time must be in the future");
    }

    @Test
    void reservationAlreadyCancelled() {
        var ex = new ReservationAlreadyCancelledException("ignored");
        assertThat(ex).hasMessage("Reservation is already cancelled");
    }

    @Test
    void reservationNotFound() {
        var ex = new ReservationNotFoundException(7L);
        assertThat(ex).hasMessageContaining("7");
    }
}
