package com.champsoft.restaurantreservationssystem.reservation.application.service;

import com.champsoft.restaurantreservationssystem.reservation.api.dto.PreOrderItemRequest;
import com.champsoft.restaurantreservationssystem.reservation.application.port.out.ReservationRepositoryPort;
import com.champsoft.restaurantreservationssystem.reservation.domain.exception.ReservationNotFoundException;
import com.champsoft.restaurantreservationssystem.reservation.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReservationCrudServiceTest {

    private ReservationRepositoryPort repo;
    private ReservationCrudService service;

    @BeforeEach
    void setup() {
        repo = mock(ReservationRepositoryPort.class);
        service = new ReservationCrudService(repo);
    }

    private Reservation sample() {
        return new Reservation(
                ReservationId.of("1"),
                new CustomerRef(1L),
                new TableRef(2L),
                new ReservationTime(LocalDateTime.now().plusDays(1)),
                new PartySize(3)
        );
    }

    @Test
    void getReturns() {
        when(repo.findById(1L)).thenReturn(Optional.of(sample()));
        assertThat(service.get(1L)).isNotNull();
    }

    @Test
    void getThrows() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(99L)).isInstanceOf(ReservationNotFoundException.class);
    }

    @Test
    void list() {
        when(repo.findAll()).thenReturn(List.of(sample()));
        assertThat(service.list()).hasSize(1);
    }

    @Test
    void updateWithItems() {
        var r = sample();
        when(repo.findById(1L)).thenReturn(Optional.of(r));
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));

        var items = List.of(new PreOrderItemRequest(5L, 2));
        var updated = service.update(1L, LocalDateTime.now().plusDays(3), 6, items);

        assertThat(updated.partySizeValue()).isEqualTo(6);
        assertThat(updated.preOrderItems()).hasSize(1);
    }

    @Test
    void updateWithNullItems() {
        var r = sample();
        when(repo.findById(1L)).thenReturn(Optional.of(r));
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));

        var updated = service.update(1L, LocalDateTime.now().plusDays(3), 4, null);
        assertThat(updated.preOrderItems()).isEmpty();
    }

    @Test
    void cancel() {
        var r = sample();
        when(repo.findById(1L)).thenReturn(Optional.of(r));
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));

        var cancelled = service.cancel(1L);
        assertThat(cancelled.status()).isEqualTo(ReservationStatus.CANCELLED);
    }

    @Test
    void deleteCall() {
        when(repo.findById(1L)).thenReturn(Optional.of(sample()));
        service.delete(1L);
        verify(repo).deleteById(1L);
    }

    @Test
    void deleteMissing() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ReservationNotFoundException.class);
    }
}
