package com.champsoft.restaurantreservationssystem.table.application.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TableNotFoundExceptionTest {

    @Test
    void carriesIdInMessage() {
        var ex = new TableNotFoundException(7L);
        assertThat(ex).hasMessageContaining("7");
        assertThat(ex).isInstanceOf(RuntimeException.class);
    }
}
