package com.champsoft.restaurantreservationssystem.table.domain.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DomainTableNotFoundExceptionTest {

    @Test
    void carriesMessage() {
        var ex = new TableNotFoundException("nope");
        assertThat(ex).hasMessage("nope");
    }
}
