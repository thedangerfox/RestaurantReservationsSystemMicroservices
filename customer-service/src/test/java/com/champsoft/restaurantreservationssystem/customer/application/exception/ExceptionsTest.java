package com.champsoft.restaurantreservationssystem.customer.application.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionsTest {

    @Test
    void duplicateCustomerExceptionCarriesMessage() {
        var ex = new DuplicateCustomerException("dup");
        assertThat(ex).hasMessage("dup");
        assertThat(ex).isInstanceOf(RuntimeException.class);
    }

    @Test
    void customerNotFoundExceptionCarriesMessage() {
        var ex = new CustomerNotFoundException("missing");
        assertThat(ex).hasMessage("missing");
    }
}
