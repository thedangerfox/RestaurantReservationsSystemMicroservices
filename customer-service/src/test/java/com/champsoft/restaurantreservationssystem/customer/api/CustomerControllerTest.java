package com.champsoft.restaurantreservationssystem.customer.api;

import com.champsoft.restaurantreservationssystem.customer.application.exception.CustomerNotFoundException;
import com.champsoft.restaurantreservationssystem.customer.application.service.CustomerCrudService;
import com.champsoft.restaurantreservationssystem.customer.application.service.CustomerEligibilityService;
import com.champsoft.restaurantreservationssystem.customer.domain.model.*;
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

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired private MockMvc mvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @MockitoBean private CustomerCrudService crud;
    @MockitoBean private CustomerEligibilityService eligibility;

    private Customer sample() {
        return new Customer(CustomerId.of(1L), new CustomerName("Alice"), new CustomerPhone("555-0001"));
    }

    @Test
    void createReturnsOk() throws Exception {
        when(crud.create(anyString(), anyString())).thenReturn(sample());

        mvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(Map.of("fullName", "Alice", "phone", "555-0001"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fullName").value("Alice"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void getReturnsCustomer() throws Exception {
        when(crud.getById(1L)).thenReturn(sample());

        mvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getReturns404WhenMissing() throws Exception {
        when(crud.getById(99L)).thenThrow(new CustomerNotFoundException("Customer not found: 99"));

        mvc.perform(get("/api/customers/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getReturns400WhenIllegal() throws Exception {
        when(crud.getById(1L)).thenThrow(new IllegalArgumentException("bad"));

        mvc.perform(get("/api/customers/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listReturnsAll() throws Exception {
        when(crud.list()).thenReturn(List.of(sample()));

        mvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Alice"));
    }

    @Test
    void updateReturnsUpdated() throws Exception {
        when(crud.update(eq(1L), anyString(), anyString())).thenReturn(sample());

        mvc.perform(put("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(Map.of("fullName", "Alice", "phone", "555-0001"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Alice"));
    }

    @Test
    void deleteReturnsNoContent() throws Exception {
        mvc.perform(delete("/api/customers/1"))
                .andExpect(status().isNoContent());
        verify(crud).delete(1L);
    }

    @Test
    void eligibilityReturnsTrue() throws Exception {
        when(eligibility.isEligible(1L)).thenReturn(true);

        mvc.perform(get("/api/customers/1/eligibility"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void eligibilityReturnsFalse() throws Exception {
        when(eligibility.isEligible(2L)).thenReturn(false);

        mvc.perform(get("/api/customers/2/eligibility"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
}
