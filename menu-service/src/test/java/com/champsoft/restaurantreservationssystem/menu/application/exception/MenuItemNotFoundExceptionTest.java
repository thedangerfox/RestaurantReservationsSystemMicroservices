package com.champsoft.restaurantreservationssystem.menu.application.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MenuItemNotFoundExceptionTest {

    @Test
    void carriesIdInMessage() {
        var ex = new MenuItemNotFoundException(42L);
        assertThat(ex).hasMessageContaining("42");
        assertThat(ex).isInstanceOf(RuntimeException.class);
    }
}
