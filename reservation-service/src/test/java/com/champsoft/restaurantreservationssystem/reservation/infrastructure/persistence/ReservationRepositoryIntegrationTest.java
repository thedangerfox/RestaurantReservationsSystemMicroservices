package com.champsoft.restaurantreservationssystem.reservation.infrastructure.persistence;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.champsoft.restaurantreservationssystem.reservation.domain.model.ReservationStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("testing")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ReservationRepositoryIntegrationTest {

    @Autowired
    private SpringDataReservationRepository repository;

    @Test
    @DisplayName("Should save a reservation successfully")
    void shouldSaveReservationSuccessfully() {

        // ------------------- Arrange -------------------
        ReservationJpaEntity r = new ReservationJpaEntity();
        r.customerId = 900L;
        r.tableId = 800L;
        r.reservationTime = LocalDateTime.now().plusDays(1);
        r.partySize = 4;
        r.status = ReservationStatus.PENDING;

        // ------------------- Act -------------------
        ReservationJpaEntity saved = repository.save(r);

        // ------------------- Assert -------------------
        assertThat(saved).isNotNull();
        assertThat(saved.id).isNotNull();
        assertThat(saved.customerId).isEqualTo(900L);
        assertThat(saved.tableId).isEqualTo(800L);
        assertThat(saved.partySize).isEqualTo(4);
        assertThat(saved.status).isEqualTo(ReservationStatus.PENDING);
    }

    @Test
    @DisplayName("Should find a reservation by ID")
    void shouldFindReservationById() {

        // ------------------- Arrange -------------------
        ReservationJpaEntity r = new ReservationJpaEntity();
        r.customerId = 901L;
        r.tableId = 801L;
        r.reservationTime = LocalDateTime.now().plusDays(2);
        r.partySize = 3;
        r.status = ReservationStatus.CONFIRMED;

        ReservationJpaEntity saved = repository.save(r);

        // ------------------- Act -------------------
        Optional<ReservationJpaEntity> found = repository.findById(saved.id);

        // ------------------- Assert -------------------
        assertThat(found).isPresent();
        assertThat(found.get().id).isEqualTo(saved.id);
        assertThat(found.get().customerId).isEqualTo(901L);
        assertThat(found.get().tableId).isEqualTo(801L);
        assertThat(found.get().partySize).isEqualTo(3);
        assertThat(found.get().status).isEqualTo(ReservationStatus.CONFIRMED);
    }

    @Test
    @DisplayName("Should return empty when reservation ID is not found")
    void shouldReturnEmptyWhenReservationIdNotFound() {

        // ------------------- Act -------------------
        Optional<ReservationJpaEntity> found = repository.findById(99999L);

        // ------------------- Assert -------------------
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Should find all reservations (flexible)")
    void shouldFindAllReservations() {

        // ------------------- Arrange -------------------
        ReservationJpaEntity r1 = new ReservationJpaEntity();
        r1.customerId = 910L;
        r1.tableId = 810L;
        r1.reservationTime = LocalDateTime.now().plusDays(1);
        r1.partySize = 2;
        r1.status = ReservationStatus.PENDING;

        ReservationJpaEntity r2 = new ReservationJpaEntity();
        r2.customerId = 911L;
        r2.tableId = 811L;
        r2.reservationTime = LocalDateTime.now().plusDays(3);
        r2.partySize = 5;
        r2.status = ReservationStatus.CONFIRMED;

        repository.save(r1);
        repository.save(r2);

        // ------------------- Act -------------------
        List<ReservationJpaEntity> all = repository.findAll();

        // ------------------- Assert -------------------
        assertThat(all)
                .extracting(r -> r.customerId)
                .contains(910L, 911L);
    }

    @Test
    @DisplayName("Should delete a reservation successfully")
    void shouldDeleteReservationSuccessfully() {

        // ------------------- Arrange -------------------
        ReservationJpaEntity r = new ReservationJpaEntity();
        r.customerId = 920L;
        r.tableId = 820L;
        r.reservationTime = LocalDateTime.now().plusDays(5);
        r.partySize = 6;
        r.status = ReservationStatus.PENDING;

        ReservationJpaEntity saved = repository.save(r);

        // ------------------- Act -------------------
        repository.deleteById(saved.id);

        Optional<ReservationJpaEntity> found = repository.findById(saved.id);

        // ------------------- Assert -------------------
        assertThat(found).isEmpty();
    }
}

