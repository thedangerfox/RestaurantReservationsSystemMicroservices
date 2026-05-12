package com.champsoft.restaurantreservationssystem.customer.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerIdTest {

    @Test
    void shouldCreateWithPositiveValue() {
        var id = CustomerId.of(42L);
        assertThat(id.value()).isEqualTo(42L);
    }

    @Test
    void shouldAllowNullForNewId() {
        var id = CustomerId.newId();
        assertThat(id.value()).isNull();
    }

    @Test
    void shouldRejectZero() {
        assertThatThrownBy(() -> CustomerId.of(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("positive");
    }

    @Test
    void shouldRejectNegative() {
        assertThatThrownBy(() -> CustomerId.of(-5L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void toStringWithValue() {
        assertThat(CustomerId.of(7L).toString()).isEqualTo("7");
    }

    @Test
    void toStringWithNull() {
        assertThat(CustomerId.newId().toString()).isEqualTo("null");
    }
}
