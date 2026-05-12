package com.champsoft.restaurantreservationssystem.reservation.api;

import com.champsoft.restaurantreservationssystem.reservation.api.dto.PreOrderItemRequest;
import com.champsoft.restaurantreservationssystem.reservation.api.dto.ReservationResponse;
import com.champsoft.restaurantreservationssystem.reservation.application.service.ReservationCrudService;
import com.champsoft.restaurantreservationssystem.reservation.application.service.ReservationOrchestrator;
import com.champsoft.restaurantreservationssystem.reservation.domain.exception.ReservationNotFoundException;
import com.champsoft.restaurantreservationssystem.reservation.domain.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ReservationController.class)
class ReservationControllerTest {

    @Autowired private MockMvc mvc;

    @MockitoBean private ReservationOrchestrator orchestrator;
    @MockitoBean private ReservationCrudService crud;
    @MockitoBean private ReservationRepresentationAssembler assembler;

    private final ObjectMapper json = new ObjectMapper().registerModule(new JavaTimeModule());

    private Reservation sample() {
        return new Reservation(
                ReservationId.of("1"),
                new CustomerRef(1L),
                new TableRef(2L),
                new ReservationTime(LocalDateTime.now().plusDays(1)),
                new PartySize(4)
        );
    }

    @BeforeEach
    void stubAssembler() {
        when(assembler.toModel(any(ReservationResponse.class)))
                .thenAnswer(inv -> EntityModel.of(inv.getArgument(0)));
        when(assembler.toCollectionModel(anyList()))
                .thenAnswer(inv -> CollectionModel.of(
                        ((List<ReservationResponse>) inv.getArgument(0)).stream().map(EntityModel::of).toList()
                ));
    }

    @Test
    void create() throws Exception {
        when(orchestrator.create(anyLong(), anyLong(), any(), anyInt(), any()))
                .thenReturn(sample());

        var body = Map.of(
                "customerId", 1,
                "tableId", 2,
                "reservationTime", LocalDateTime.now().plusDays(1).toString(),
                "partySize", 4,
                "preOrderItems", List.of()
        );

        mvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1));
    }

    @Test
    void getById() throws Exception {
        when(crud.get(1L)).thenReturn(sample());

        mvc.perform(get("/api/reservations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1));
    }

    @Test
    void getById404() throws Exception {
        when(crud.get(99L)).thenThrow(new ReservationNotFoundException(99L));

        mvc.perform(get("/api/reservations/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void list() throws Exception {
        when(crud.list()).thenReturn(List.of(sample()));

        mvc.perform(get("/api/reservations"))
                .andExpect(status().isOk());
    }

    @Test
    void update() throws Exception {
        when(crud.update(eq(1L), any(), anyInt(), any())).thenReturn(sample());

        var body = Map.of(
                "reservationTime", LocalDateTime.now().plusDays(2).toString(),
                "partySize", 6,
                "preOrderItems", List.of(new PreOrderItemRequest(5L, 2))
        );

        mvc.perform(put("/api/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(body)))
                .andExpect(status().isOk());
    }

    @Test
    void cancel() throws Exception {
        when(crud.cancel(1L)).thenReturn(sample());

        mvc.perform(post("/api/reservations/1/cancel"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCall() throws Exception {
        mvc.perform(delete("/api/reservations/1"))
                .andExpect(status().isNoContent());
        verify(crud).delete(1L);
    }
}
