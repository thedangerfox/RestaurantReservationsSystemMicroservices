package com.champsoft.restaurantreservationssystem.table.application.service;

import com.champsoft.restaurantreservationssystem.table.application.exception.TableNotFoundException;
import com.champsoft.restaurantreservationssystem.table.application.port.out.TableRepositoryPort;
import com.champsoft.restaurantreservationssystem.table.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TableCrudServiceTest {

    private TableRepositoryPort repo;
    private TableCrudService service;

    @BeforeEach
    void setup() {
        repo = mock(TableRepositoryPort.class);
        service = new TableCrudService(repo);
    }

    @Test
    void shouldCreate() {
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));
        var t = service.create(5, 4);
        assertThat(t.number().value()).isEqualTo(5);
    }

    @Test
    void getByIdReturns() {
        var t = new Table(TableId.of(1L), new TableNumber(1), new TableCapacity(2));
        when(repo.findById(1L)).thenReturn(Optional.of(t));
        assertThat(service.getById(1L)).isSameAs(t);
    }

    @Test
    void getByIdThrows() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getById(99L)).isInstanceOf(TableNotFoundException.class);
    }

    @Test
    void list() {
        when(repo.findAll()).thenReturn(List.of());
        assertThat(service.list()).isEmpty();
    }

    @Test
    void update() {
        var t = new Table(TableId.of(1L), new TableNumber(1), new TableCapacity(2));
        when(repo.findById(1L)).thenReturn(Optional.of(t));
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));

        var updated = service.update(1L, 7, 6);

        assertThat(updated.number().value()).isEqualTo(7);
        assertThat(updated.capacity().value()).isEqualTo(6);
    }

    @Test
    void deleteCall() {
        var t = new Table(TableId.of(1L), new TableNumber(1), new TableCapacity(2));
        when(repo.findById(1L)).thenReturn(Optional.of(t));

        service.delete(1L);

        verify(repo).deleteById(1L);
    }

    @Test
    void deleteMissingThrows() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(TableNotFoundException.class);
    }
}
