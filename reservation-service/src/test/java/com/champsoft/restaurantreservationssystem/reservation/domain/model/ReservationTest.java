package com.champsoft.restaurantreservationssystem.reservation.domain.model;

import com.champsoft.restaurantreservationssystem.reservation.domain.exception.ReservationAlreadyCancelledException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationTest {

    private Reservation sample() {
        return new Reservation(
                ReservationId.newId(),
                new CustomerRef(1L),
                new TableRef(2L),
                new ReservationTime(LocalDateTime.now().plusDays(1)),
                new PartySize(4)
        );
    }

    @Test
    void newReservationIsPending() {
        var r = sample();
        assertThat(r.status()).isEqualTo(ReservationStatus.PENDING);
        assertThat(r.customerIdValue()).isEqualTo(1L);
        assertThat(r.tableIdValue()).isEqualTo(2L);
        assertThat(r.partySizeValue()).isEqualTo(4);
        assertThat(r.reservationTimeValue()).isNotNull();
        assertThat(r.preOrderItems()).isEmpty();
    }

    @Test
    void addPreOrderItemAppendsItem() {
        var r = sample();
        r.addPreOrderItem(10L, 2);

        assertThat(r.preOrderItems()).hasSize(1);
        assertThat(r.preOrderItems().get(0).itemId()).isEqualTo(10L);
        assertThat(r.preOrderItems().get(0).quantity().value()).isEqualTo(2);
    }

    @Test
    void updateReplacesFieldsAndItems() {
        var r = sample();
        r.addPreOrderItem(1L, 1);

        var newTime = new ReservationTime(LocalDateTime.now().plusDays(2));
        var newSize = new PartySize(8);
        var newItems = List.of(new PreOrderItem(5L, new PreOrderQuantity(3)));

        r.update(newTime, newSize, newItems);

        assertThat(r.reservationTime()).isEqualTo(newTime);
        assertThat(r.partySize()).isEqualTo(newSize);
        assertThat(r.preOrderItems()).hasSize(1);
        assertThat(r.preOrderItems().get(0).itemId()).isEqualTo(5L);
    }

    @Test
    void cancelMovesToCancelled() {
        var r = sample();
        r.cancel();
        assertThat(r.status()).isEqualTo(ReservationStatus.CANCELLED);
    }

    @Test
    void cancelTwiceThrows() {
        var r = sample();
        r.cancel();
        assertThatThrownBy(r::cancel).isInstanceOf(ReservationAlreadyCancelledException.class);
    }

    @Test
    void reservationIdAcceptsValid() {
        var id = ReservationId.of("abc");
        assertThat(id.value()).isEqualTo("abc");
    }

    @Test
    void reservationIdRejectsBlank() {
        assertThatThrownBy(() -> ReservationId.of("")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ReservationId.of("   ")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ReservationId.of(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void reservationIdNewIdHasUuid() {
        assertThat(ReservationId.newId().value()).isNotBlank();
    }

    @Test
    void reservationStatusValues() {
        assertThat(ReservationStatus.values()).hasSize(3);
    }
}
