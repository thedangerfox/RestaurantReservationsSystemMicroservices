package com.champsoft.restaurantreservationssystem.reservation.application.service;

import com.champsoft.restaurantreservationssystem.reservation.api.dto.PreOrderItemRequest;
import com.champsoft.restaurantreservationssystem.reservation.application.port.out.CustomerEligibilityPort;
import com.champsoft.restaurantreservationssystem.reservation.application.port.out.MenuItemEligibilityPort;
import com.champsoft.restaurantreservationssystem.reservation.application.port.out.TableEligibilityPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ReservationOrchestratorTest {

    private ReservationOrchestrator orchestrator;

    @BeforeEach
    void setup() {
        orchestrator = new ReservationOrchestrator(
                mock(CustomerEligibilityPort.class),
                mock(TableEligibilityPort.class),
                mock(MenuItemEligibilityPort.class)
        );
    }

    @Test
    void createWithoutItems() {
        var r = orchestrator.create(1L, 2L, LocalDateTime.now().plusDays(1), 4, null);

        assertThat(r.customerIdValue()).isEqualTo(1L);
        assertThat(r.tableIdValue()).isEqualTo(2L);
        assertThat(r.partySizeValue()).isEqualTo(4);
        assertThat(r.preOrderItems()).isEmpty();
    }

    @Test
    void createWithItems() {
        var r = orchestrator.create(1L, 2L, LocalDateTime.now().plusDays(1), 4,
                List.of(new PreOrderItemRequest(7L, 2)));

        assertThat(r.preOrderItems()).hasSize(1);
        assertThat(r.preOrderItems().get(0).itemId()).isEqualTo(7L);
    }
}
