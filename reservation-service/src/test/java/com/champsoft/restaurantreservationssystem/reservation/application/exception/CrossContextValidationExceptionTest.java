package com.champsoft.restaurantreservationssystem.reservation.application.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CrossContextValidationExceptionTest {

    @Test
    void carriesMessage() {
        var ex = new CrossContextValidationException("bad");
        assertThat(ex).hasMessage("bad");
        assertThat(ex).isInstanceOf(RuntimeException.class);
    }
}
