package com.champsoft.restaurantreservationssystem.customer.application.service;

import com.champsoft.restaurantreservationssystem.customer.application.exception.CustomerNotFoundException;
import com.champsoft.restaurantreservationssystem.customer.application.port.out.CustomerRepositoryPort;
import com.champsoft.restaurantreservationssystem.customer.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerCrudServiceTest {

    private CustomerRepositoryPort repo;
    private CustomerCrudService service;

    @BeforeEach
    void setup() {
        repo = mock(CustomerRepositoryPort.class);
        service = new CustomerCrudService(repo);
    }

    @Test
    void shouldCreateCustomer() {
        when(repo.save(any(Customer.class))).thenAnswer(inv -> inv.getArgument(0));

        var created = service.create("Alice", "555-0001");

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        verify(repo).save(captor.capture());
        assertThat(captor.getValue().name().value()).isEqualTo("Alice");
        assertThat(captor.getValue().phone().value()).isEqualTo("555-0001");
        assertThat(created.status()).isEqualTo(CustomerStatus.ACTIVE);
    }

    @Test
    void shouldGetById() {
        var customer = new Customer(CustomerId.of(1L), new CustomerName("Bob"), new CustomerPhone("1"));
        when(repo.findById(1L)).thenReturn(Optional.of(customer));

        var got = service.getById(1L);

        assertThat(got.name().value()).isEqualTo("Bob");
    }

    @Test
    void getByIdThrowsWhenMissing() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99L))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void shouldList() {
        var c1 = new Customer(CustomerId.of(1L), new CustomerName("A"), new CustomerPhone("1"));
        var c2 = new Customer(CustomerId.of(2L), new CustomerName("B"), new CustomerPhone("2"));
        when(repo.findAll()).thenReturn(List.of(c1, c2));

        var list = service.list();

        assertThat(list).hasSize(2);
    }

    @Test
    void shouldUpdate() {
        var existing = new Customer(CustomerId.of(1L), new CustomerName("Old"), new CustomerPhone("old"));
        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        when(repo.save(any(Customer.class))).thenAnswer(inv -> inv.getArgument(0));

        var updated = service.update(1L, "NewName", "555-9999");

        assertThat(updated.name().value()).isEqualTo("NewName");
        assertThat(updated.phone().value()).isEqualTo("555-9999");
        verify(repo).save(any(Customer.class));
    }

    @Test
    void updateThrowsWhenMissing() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99L, "X", "Y"))
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    void shouldDelete() {
        var existing = new Customer(CustomerId.of(1L), new CustomerName("X"), new CustomerPhone("0"));
        when(repo.findById(1L)).thenReturn(Optional.of(existing));

        service.delete(1L);

        verify(repo).deleteById(1L);
    }

    @Test
    void deleteThrowsWhenMissing() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(99L))
                .isInstanceOf(CustomerNotFoundException.class);
        verify(repo, never()).deleteById(any());
    }
}
