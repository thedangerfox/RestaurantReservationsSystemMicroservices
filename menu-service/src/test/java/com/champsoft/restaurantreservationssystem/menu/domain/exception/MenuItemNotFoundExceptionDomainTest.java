package com.champsoft.restaurantreservationssystem.menu.domain.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MenuItemNotFoundExceptionDomainTest {

    @Test
    void carriesMessage() {
        var ex = new MenuItemNotFoundException("nope");
        assertThat(ex).hasMessage("nope");
    }
}
