package com.champsoft.restaurantreservationssystem.reservation.domain.model;

import com.champsoft.restaurantreservationssystem.reservation.domain.exception.InvalidPartySizeException;
import com.champsoft.restaurantreservationssystem.reservation.domain.exception.InvalidPreOrderQuantityException;
import com.champsoft.restaurantreservationssystem.reservation.domain.exception.InvalidReservationTimeException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValueObjectsTest {

    @Test
    void partySizeAccepts() {
        assertThat(new PartySize(1).value()).isEqualTo(1);
        assertThat(new PartySize(99).value()).isEqualTo(99);
    }

    @Test
    void partySizeRejectsZeroOrNegative() {
        assertThatThrownBy(() -> new PartySize(0)).isInstanceOf(InvalidPartySizeException.class);
        assertThatThrownBy(() -> new PartySize(-1)).isInstanceOf(InvalidPartySizeException.class);
    }

    @Test
    void preOrderQuantityAccepts() {
        assertThat(new PreOrderQuantity(1).value()).isEqualTo(1);
    }

    @Test
    void preOrderQuantityRejects() {
        assertThatThrownBy(() -> new PreOrderQuantity(0)).isInstanceOf(InvalidPreOrderQuantityException.class);
        assertThatThrownBy(() -> new PreOrderQuantity(-1)).isInstanceOf(InvalidPreOrderQuantityException.class);
    }

    @Test
    void preOrderItemAccepts() {
        var item = new PreOrderItem(1L, new PreOrderQuantity(2));
        assertThat(item.itemId()).isEqualTo(1L);
        assertThat(item.quantity().value()).isEqualTo(2);
    }

    @Test
    void preOrderItemRejects() {
        assertThatThrownBy(() -> new PreOrderItem(null, new PreOrderQuantity(1)))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new PreOrderItem(0L, new PreOrderQuantity(1)))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new PreOrderItem(-1L, new PreOrderQuantity(1)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void reservationTimeAccepts() {
        var when = LocalDateTime.now().plusHours(1);
        assertThat(new ReservationTime(when).value()).isEqualTo(when);
    }

    @Test
    void reservationTimeRejectsNull() {
        assertThatThrownBy(() -> new ReservationTime(null)).isInstanceOf(InvalidReservationTimeException.class);
    }

    @Test
    void reservationTimeRejectsPast() {
        assertThatThrownBy(() -> new ReservationTime(LocalDateTime.now().minusDays(1)))
                .isInstanceOf(InvalidReservationTimeException.class);
    }

    @Test
    void customerRefAccepts() {
        assertThat(new CustomerRef(1L).value()).isEqualTo(1L);
    }

    @Test
    void customerRefRejects() {
        assertThatThrownBy(() -> new CustomerRef(null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new CustomerRef(0L)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new CustomerRef(-1L)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void tableRefAccepts() {
        assertThat(new TableRef(1L).value()).isEqualTo(1L);
    }

    @Test
    void tableRefRejects() {
        assertThatThrownBy(() -> new TableRef(null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new TableRef(0L)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new TableRef(-1L)).isInstanceOf(IllegalArgumentException.class);
    }
}
