package com.champsoft.restaurantreservationssystem.reservation.api.mapper;

import com.champsoft.restaurantreservationssystem.reservation.domain.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationApiMapperTest {

    @Test
    void shouldMapToResponse() {
        var r = new Reservation(
                ReservationId.of("42"),
                new CustomerRef(1L),
                new TableRef(2L),
                new ReservationTime(LocalDateTime.now().plusDays(1)),
                new PartySize(3)
        );
        r.addPreOrderItem(5L, 2);

        var resp = ReservationApiMapper.toResponse(r);

        assertThat(resp.id()).isEqualTo("42");
        assertThat(resp.customerId()).isEqualTo(1L);
        assertThat(resp.tableId()).isEqualTo(2L);
        assertThat(resp.partySize()).isEqualTo(3);
        assertThat(resp.status()).isEqualTo(ReservationStatus.PENDING);
        assertThat(resp.preOrderItems()).hasSize(1);
        assertThat(resp.preOrderItems().get(0).itemId()).isEqualTo(5L);
        assertThat(resp.preOrderItems().get(0).quantity()).isEqualTo(2);
    }

    @Test
    void mapperCanBeInstantiated() {
        new ReservationApiMapper();
    }
}
