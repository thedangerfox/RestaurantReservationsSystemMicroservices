package com.champsoft.restaurantreservationssystem.customer.domain.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DomainExceptionsTest {

    @Test
    void customerNotFoundExceptionCarriesMessage() {
        var ex = new CustomerNotFoundException("nope");
        assertThat(ex).hasMessage("nope");
        assertThat(ex).isInstanceOf(RuntimeException.class);
    }
}
