package com.champsoft.restaurantreservationssystem.table.application.service;

import com.champsoft.restaurantreservationssystem.table.application.exception.TableNotFoundException;
import com.champsoft.restaurantreservationssystem.table.application.port.out.TableRepositoryPort;
import com.champsoft.restaurantreservationssystem.table.domain.model.*;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TableEligibilityServiceTest {

    @Test
    void existingIsEligible() {
        var repo = mock(TableRepositoryPort.class);
        var t = new Table(TableId.of(1L), new TableNumber(1), new TableCapacity(2));
        when(repo.findById(1L)).thenReturn(Optional.of(t));

        assertThat(new TableEligibilityService(repo).isEligible(1L)).isTrue();
    }

    @Test
    void missingThrows() {
        var repo = mock(TableRepositoryPort.class);
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> new TableEligibilityService(repo).isEligible(99L))
                .isInstanceOf(TableNotFoundException.class);
    }
}
