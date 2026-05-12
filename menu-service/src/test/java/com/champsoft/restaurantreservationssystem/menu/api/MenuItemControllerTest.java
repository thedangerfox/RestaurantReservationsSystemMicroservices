package com.champsoft.restaurantreservationssystem.menu.api;

import com.champsoft.restaurantreservationssystem.menu.application.exception.MenuItemNotFoundException;
import com.champsoft.restaurantreservationssystem.menu.application.service.MenuItemCrudService;
import com.champsoft.restaurantreservationssystem.menu.application.service.MenuItemEligibilityService;
import com.champsoft.restaurantreservationssystem.menu.domain.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MenuItemController.class)
class MenuItemControllerTest {

    @Autowired private MockMvc mvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @MockitoBean private MenuItemCrudService crud;
    @MockitoBean private MenuItemEligibilityService eligibility;

    private MenuItem sample() {
        return new MenuItem(MenuItemId.of(1L), new MenuItemName("Pizza"), new MenuItemPrice(new BigDecimal("12.99")));
    }

    @Test
    void create() throws Exception {
        when(crud.create(anyString(), any(BigDecimal.class))).thenReturn(sample());

        mvc.perform(post("/api/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(Map.of("name", "Pizza", "price", "12.99"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Pizza"));
    }

    @Test
    void getById() throws Exception {
        when(crud.getById(1L)).thenReturn(sample());

        mvc.perform(get("/api/menu-items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getReturns404() throws Exception {
        when(crud.getById(99L)).thenThrow(new MenuItemNotFoundException(99L));

        mvc.perform(get("/api/menu-items/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getReturns400() throws Exception {
        when(crud.getById(1L)).thenThrow(new IllegalArgumentException("bad"));

        mvc.perform(get("/api/menu-items/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void list() throws Exception {
        when(crud.list()).thenReturn(List.of(sample()));

        mvc.perform(get("/api/menu-items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Pizza"));
    }

    @Test
    void update() throws Exception {
        when(crud.update(eq(1L), anyString(), any(BigDecimal.class))).thenReturn(sample());

        mvc.perform(put("/api/menu-items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(Map.of("name", "Pizza", "price", "12.99"))))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCall() throws Exception {
        mvc.perform(delete("/api/menu-items/1"))
                .andExpect(status().isNoContent());
        verify(crud).delete(1L);
    }

    @Test
    void eligibilityTrue() throws Exception {
        when(eligibility.isEligible(1L)).thenReturn(true);

        mvc.perform(get("/api/menu-items/1/eligibility"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
