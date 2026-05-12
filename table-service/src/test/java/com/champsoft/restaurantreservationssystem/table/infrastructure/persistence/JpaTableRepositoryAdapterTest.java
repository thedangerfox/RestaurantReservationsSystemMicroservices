package com.champsoft.restaurantreservationssystem.table.infrastructure.persistence;

import com.champsoft.restaurantreservationssystem.table.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class JpaTableRepositoryAdapterTest {

    private SpringDataTableRepository jpa;
    private JpaTableRepositoryAdapter adapter;

    @BeforeEach
    void setup() {
        jpa = mock(SpringDataTableRepository.class);
        adapter = new JpaTableRepositoryAdapter(jpa);
    }

    private TableJpaEntity entity(Long id, int number, int cap) {
        var e = new TableJpaEntity();
        e.id = id;
        e.tableNumber = number;
        e.capacity = cap;
        e.status = TableStatus.AVAILABLE;
        return e;
    }

    @Test
    void shouldSave() {
        var t = new Table(TableId.of(1L), new TableNumber(5), new TableCapacity(4));
        when(jpa.save(any(TableJpaEntity.class))).thenReturn(entity(1L, 5, 4));

        var saved = adapter.save(t);

        assertThat(saved.number().value()).isEqualTo(5);
    }

    @Test
    void shouldFindById() {
        when(jpa.findById(1L)).thenReturn(Optional.of(entity(1L, 5, 4)));
        assertThat(adapter.findById(1L)).isPresent();
    }

    @Test
    void shouldReturnEmpty() {
        when(jpa.findById(99L)).thenReturn(Optional.empty());
        assertThat(adapter.findById(99L)).isEmpty();
    }

    @Test
    void shouldFindAll() {
        when(jpa.findAll()).thenReturn(List.of(entity(1L, 1, 2), entity(2L, 2, 4)));
        assertThat(adapter.findAll()).hasSize(2);
    }

    @Test
    void shouldDelete() {
        adapter.deleteById(3L);
        verify(jpa).deleteById(3L);
    }
}
