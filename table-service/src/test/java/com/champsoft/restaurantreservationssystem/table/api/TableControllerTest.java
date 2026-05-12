package com.champsoft.restaurantreservationssystem.table.api;

import com.champsoft.restaurantreservationssystem.table.application.exception.TableNotFoundException;
import com.champsoft.restaurantreservationssystem.table.application.service.TableCrudService;
import com.champsoft.restaurantreservationssystem.table.application.service.TableEligibilityService;
import com.champsoft.restaurantreservationssystem.table.domain.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TableController.class)
class TableControllerTest {

    @Autowired private MockMvc mvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @MockitoBean private TableCrudService crud;
    @MockitoBean private TableEligibilityService eligibility;

    private Table sample() {
        return new Table(TableId.of(1L), new TableNumber(5), new TableCapacity(4));
    }

    @Test
    void create() throws Exception {
        when(crud.create(anyInt(), anyInt())).thenReturn(sample());

        mvc.perform(post("/api/tables")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(Map.of("tableNumber", 5, "capacity", 4))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getById() throws Exception {
        when(crud.getById(1L)).thenReturn(sample());
        mvc.perform(get("/api/tables/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tableNumber").value(5));
    }

    @Test
    void getReturns404() throws Exception {
        when(crud.getById(99L)).thenThrow(new TableNotFoundException(99L));
        mvc.perform(get("/api/tables/99")).andExpect(status().isNotFound());
    }

    @Test
    void getReturns400() throws Exception {
        when(crud.getById(1L)).thenThrow(new IllegalArgumentException("bad"));
        mvc.perform(get("/api/tables/1")).andExpect(status().isBadRequest());
    }

    @Test
    void list() throws Exception {
        when(crud.list()).thenReturn(List.of(sample()));
        mvc.perform(get("/api/tables"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void update() throws Exception {
        when(crud.update(eq(1L), anyInt(), anyInt())).thenReturn(sample());
        mvc.perform(put("/api/tables/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(Map.of("tableNumber", 5, "capacity", 4))))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCall() throws Exception {
        mvc.perform(delete("/api/tables/1")).andExpect(status().isNoContent());
        verify(crud).delete(1L);
    }

    @Test
    void eligibilityTrue() throws Exception {
        when(eligibility.isEligible(1L)).thenReturn(true);
        mvc.perform(get("/api/tables/1/eligibility"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
