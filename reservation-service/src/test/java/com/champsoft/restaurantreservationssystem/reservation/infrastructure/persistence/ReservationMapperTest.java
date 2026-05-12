package com.champsoft.restaurantreservationssystem.reservation.infrastructure.persistence;

import com.champsoft.restaurantreservationssystem.reservation.domain.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationMapperTest {

    @Test
    void shouldMapEntityToDomain() {
        var e = new ReservationJpaEntity();
        e.id = 1L;
        e.customerId = 100L;
        e.tableId = 200L;
        e.reservationTime = LocalDateTime.now().plusDays(1);
        e.partySize = 4;
        e.status = ReservationStatus.PENDING;

        var domain = ReservationMapper.toDomain(e);

        assertThat(domain.id().value()).isEqualTo("1");
        assertThat(domain.customerIdValue()).isEqualTo(100L);
        assertThat(domain.tableIdValue()).isEqualTo(200L);
        assertThat(domain.partySizeValue()).isEqualTo(4);
        assertThat(domain.status()).isEqualTo(ReservationStatus.PENDING);
    }

    @Test
    void cancelledEntityMapsToCancelledDomain() {
        var e = new ReservationJpaEntity();
        e.id = 1L;
        e.customerId = 1L;
        e.tableId = 1L;
        e.reservationTime = LocalDateTime.now().plusDays(1);
        e.partySize = 2;
        e.status = ReservationStatus.CANCELLED;

        var domain = ReservationMapper.toDomain(e);
        assertThat(domain.status()).isEqualTo(ReservationStatus.CANCELLED);
    }

    @Test
    void entityWithPreorderItemsMapsToDomain() {
        var e = new ReservationJpaEntity();
        e.id = 1L;
        e.customerId = 1L;
        e.tableId = 1L;
        e.reservationTime = LocalDateTime.now().plusDays(1);
        e.partySize = 2;
        e.status = ReservationStatus.PENDING;

        var item = new PreOrderItemJpaEntity();
        item.id = 1L;
        item.itemId = 5L;
        item.quantity = 3;
        e.preOrderItems.add(item);

        var domain = ReservationMapper.toDomain(e);

        assertThat(domain.preOrderItems()).hasSize(1);
        assertThat(domain.preOrderItems().get(0).itemId()).isEqualTo(5L);
        assertThat(domain.preOrderItems().get(0).quantity().value()).isEqualTo(3);
    }

    @Test
    void shouldMapDomainToEntityWithNumericId() {
        var r = new Reservation(
                ReservationId.of("42"),
                new CustomerRef(1L),
                new TableRef(2L),
                new ReservationTime(LocalDateTime.now().plusDays(1)),
                new PartySize(3)
        );
        r.addPreOrderItem(7L, 2);

        var e = ReservationMapper.toEntity(r);

        assertThat(e.id).isEqualTo(42L);
        assertThat(e.customerId).isEqualTo(1L);
        assertThat(e.tableId).isEqualTo(2L);
        assertThat(e.partySize).isEqualTo(3);
        assertThat(e.preOrderItems).hasSize(1);
        assertThat(e.preOrderItems.get(0).itemId).isEqualTo(7L);
        assertThat(e.preOrderItems.get(0).quantity).isEqualTo(2);
        assertThat(e.preOrderItems.get(0).reservation).isSameAs(e);
    }

    @Test
    void shouldMapDomainToEntityWithUuidIdSetsNullId() {
        var r = new Reservation(
                ReservationId.newId(),
                new CustomerRef(1L),
                new TableRef(2L),
                new ReservationTime(LocalDateTime.now().plusDays(1)),
                new PartySize(3)
        );

        var e = ReservationMapper.toEntity(r);

        assertThat(e.id).isNull();
    }

    @Test
    void mapperCanBeInstantiated() {
        new ReservationMapper();
    }
}
