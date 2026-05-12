package com.champsoft.restaurantreservationssystem.reservation.api;

import com.champsoft.restaurantreservationssystem.reservation.api.dto.ReservationResponse;
import com.champsoft.restaurantreservationssystem.reservation.domain.model.ReservationStatus;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationRepresentationAssemblerTest {

    @Test
    void pendingReservationGetsCancelLink() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));

        var assembler = new ReservationRepresentationAssembler();
        var resp = new ReservationResponse("1", 1L, 2L, LocalDateTime.now().plusDays(1),
                4, ReservationStatus.PENDING, List.of());

        var model = assembler.toModel(resp);

        assertThat(model.getLink("self")).isPresent();
        assertThat(model.getLink("reservations")).isPresent();
        assertThat(model.getLink("cancel")).isPresent();

        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void cancelledReservationOmitsCancelLink() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));

        var assembler = new ReservationRepresentationAssembler();
        var resp = new ReservationResponse("1", 1L, 2L, LocalDateTime.now().plusDays(1),
                4, ReservationStatus.CANCELLED, List.of());

        var model = assembler.toModel(resp);

        assertThat(model.getLink("self")).isPresent();
        assertThat(model.getLink("cancel")).isEmpty();

        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void toCollectionModel() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));

        var assembler = new ReservationRepresentationAssembler();
        var resp1 = new ReservationResponse("1", 1L, 2L, LocalDateTime.now().plusDays(1),
                4, ReservationStatus.PENDING, List.of());
        var resp2 = new ReservationResponse("2", 1L, 2L, LocalDateTime.now().plusDays(1),
                4, ReservationStatus.CONFIRMED, List.of());

        var collection = assembler.toCollectionModel(List.of(resp1, resp2));

        assertThat(collection.getContent()).hasSize(2);
        assertThat(collection.getLink("self")).isPresent();

        RequestContextHolder.resetRequestAttributes();
    }
}
