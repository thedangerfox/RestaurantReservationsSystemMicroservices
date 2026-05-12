package com.champsoft.restaurantreservationssystem.customer.application.service;

import com.champsoft.restaurantreservationssystem.customer.application.exception.CustomerNotFoundException;
import com.champsoft.restaurantreservationssystem.customer.application.port.out.CustomerRepositoryPort;
import com.champsoft.restaurantreservationssystem.customer.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerEligibilityServiceTest {

    private CustomerRepositoryPort repo;
    private CustomerEligibilityService service;

    @BeforeEach
    void setup() {
        repo = mock(CustomerRepositoryPort.class);
        service = new CustomerEligibilityService(repo);
    }

    @Test
    void activeCustomerIsEligible() {
        var c = new Customer(CustomerId.of(1L), new CustomerName("A"), new CustomerPhone("1"));
        when(repo.findById(1L)).thenReturn(Optional.of(c));

        assertThat(service.isEligible(1L)).isTrue();
    }

    @Test
    void inactiveCustomerIsNotEligible() {
        var c = new Customer(CustomerId.of(2L), new CustomerName("B"), new CustomerPhone("2"));
        c.deactivate();
        when(repo.findById(2L)).thenReturn(Optional.of(c));

        assertThat(service.isEligible(2L)).isFalse();
    }

    @Test
    void missingCustomerThrows() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.isEligible(99L))
                .isInstanceOf(CustomerNotFoundException.class);
    }
}
