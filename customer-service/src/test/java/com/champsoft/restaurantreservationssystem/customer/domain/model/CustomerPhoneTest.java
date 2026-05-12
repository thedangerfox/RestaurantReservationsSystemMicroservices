package com.champsoft.restaurantreservationssystem.customer.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerPhoneTest {

    @Test
    void shouldAllowValidPhone() {
        var phone = new CustomerPhone("555-1234");
        assertThat(phone.value()).isEqualTo("555-1234");
    }

    @Test
    void shouldAllowNull() {
        var phone = new CustomerPhone(null);
        assertThat(phone.value()).isNull();
    }

    @Test
    void shouldRejectTooLong() {
        var tooLong = "1".repeat(21);
        assertThatThrownBy(() -> new CustomerPhone(tooLong))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("too long");
    }

    @Test
    void shouldAcceptMaxLength() {
        var atMax = "1".repeat(20);
        assertThat(new CustomerPhone(atMax).value()).hasSize(20);
    }
}
