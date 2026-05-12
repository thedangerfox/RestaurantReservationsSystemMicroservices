package com.champsoft.restaurantreservationssystem.customer.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerNameTest {

    @Test
    void shouldCreateValidName() {
        var name = new CustomerName("Alice");
        assertThat(name.value()).isEqualTo("Alice");
    }

    @Test
    void shouldRejectNull() {
        assertThatThrownBy(() -> new CustomerName(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("required");
    }

    @Test
    void shouldRejectBlank() {
        assertThatThrownBy(() -> new CustomerName("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("required");
    }

    @Test
    void shouldRejectTooLong() {
        var tooLong = "a".repeat(121);
        assertThatThrownBy(() -> new CustomerName(tooLong))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("too long");
    }

    @Test
    void shouldAcceptMaxLength() {
        var atMax = "a".repeat(120);
        assertThat(new CustomerName(atMax).value()).hasSize(120);
    }
}
