package com.champsoft.restaurantreservationssystem.reservation.infrastructure.persistence;

import com.champsoft.restaurantreservationssystem.reservation.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class JpaReservationRepositoryAdapterTest {

    private SpringDataReservationRepository jpa;
    private JpaReservationRepositoryAdapter adapter;

    @BeforeEach
    void setup() {
        jpa = mock(SpringDataReservationRepository.class);
        adapter = new JpaReservationRepositoryAdapter(jpa);
    }

    private ReservationJpaEntity entity(Long id) {
        var e = new ReservationJpaEntity();
        e.id = id;
        e.customerId = 1L;
        e.tableId = 2L;
        e.reservationTime = LocalDateTime.now().plusDays(1);
        e.partySize = 3;
        e.status = ReservationStatus.PENDING;
        return e;
    }

    @Test
    void shouldSave() {
        var r = new Reservation(
                ReservationId.of("1"),
                new CustomerRef(1L),
                new TableRef(2L),
                new ReservationTime(LocalDateTime.now().plusDays(1)),
                new PartySize(3)
        );
        when(jpa.save(any())).thenReturn(entity(1L));

        var saved = adapter.save(r);

        assertThat(saved.customerIdValue()).isEqualTo(1L);
    }

    @Test
    void shouldFindById() {
        when(jpa.findById(1L)).thenReturn(Optional.of(entity(1L)));
        assertThat(adapter.findById(1L)).isPresent();
    }

    @Test
    void shouldReturnEmpty() {
        when(jpa.findById(99L)).thenReturn(Optional.empty());
        assertThat(adapter.findById(99L)).isEmpty();
    }

    @Test
    void shouldFindAll() {
        when(jpa.findAll()).thenReturn(List.of(entity(1L), entity(2L)));
        assertThat(adapter.findAll()).hasSize(2);
    }

    @Test
    void shouldDelete() {
        adapter.deleteById(5L);
        verify(jpa).deleteById(5L);
    }
}
