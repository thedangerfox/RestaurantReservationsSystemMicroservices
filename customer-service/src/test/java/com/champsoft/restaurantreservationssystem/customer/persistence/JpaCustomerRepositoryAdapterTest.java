package com.champsoft.restaurantreservationssystem.customer.persistence;

import com.champsoft.restaurantreservationssystem.customer.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class JpaCustomerRepositoryAdapterTest {

    private SpringDataCustomerRepository jpa;
    private JpaCustomerRepositoryAdapter adapter;

    @BeforeEach
    void setup() {
        jpa = mock(SpringDataCustomerRepository.class);
        adapter = new JpaCustomerRepositoryAdapter(jpa);
    }

    @Test
    void shouldSaveCustomer() {
        // Arrange
        var customer = new Customer(
                CustomerId.of(1L),
                new CustomerName("John"),
                new CustomerPhone("111-1111")
        );

        var savedEntity = new CustomerJpaEntity();
        savedEntity.id = 1L;
        savedEntity.fullName = "John";
        savedEntity.phone = "111-1111";
        savedEntity.status = "ACTIVE";

        when(jpa.save(any(CustomerJpaEntity.class))).thenReturn(savedEntity);

        // Act
        var result = adapter.save(customer);

        // Assert
        ArgumentCaptor<CustomerJpaEntity> captor = ArgumentCaptor.forClass(CustomerJpaEntity.class);
        verify(jpa).save(captor.capture());
        assertThat(captor.getValue().fullName).isEqualTo("John");
        assertThat(result.id().value()).isEqualTo(1L);
        assertThat(result.status()).isEqualTo(CustomerStatus.ACTIVE);
    }

    @Test
    void shouldFindById() {
        // Arrange
        var entity = new CustomerJpaEntity();
        entity.id = 2L;
        entity.fullName = "Jane";
        entity.phone = "222-2222";
        entity.status = "ACTIVE";

        when(jpa.findById(2L)).thenReturn(Optional.of(entity));

        // Act
        var found = adapter.findById(2L);

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().name().value()).isEqualTo("Jane");
    }

    @Test
    void shouldReturnEmptyWhenNotFound() {
        when(jpa.findById(99L)).thenReturn(Optional.empty());
        assertThat(adapter.findById(99L)).isEmpty();
    }

    @Test
    void shouldMapInactiveEntityToDeactivatedDomain() {
        var entity = new CustomerJpaEntity();
        entity.id = 3L;
        entity.fullName = "Old";
        entity.phone = "333-3333";
        entity.status = "INACTIVE";

        when(jpa.findById(3L)).thenReturn(Optional.of(entity));

        var found = adapter.findById(3L);

        assertThat(found).isPresent();
        assertThat(found.get().status()).isEqualTo(CustomerStatus.INACTIVE);
    }

    @Test
    void shouldFindAll() {
        var e1 = new CustomerJpaEntity();
        e1.id = 1L; e1.fullName = "A"; e1.phone = "1"; e1.status = "ACTIVE";
        var e2 = new CustomerJpaEntity();
        e2.id = 2L; e2.fullName = "B"; e2.phone = "2"; e2.status = "INACTIVE";

        when(jpa.findAll()).thenReturn(List.of(e1, e2));

        var all = adapter.findAll();

        assertThat(all).hasSize(2);
        assertThat(all.get(0).name().value()).isEqualTo("A");
        assertThat(all.get(1).status()).isEqualTo(CustomerStatus.INACTIVE);
    }

    @Test
    void shouldDeleteById() {
        adapter.deleteById(7L);
        verify(jpa).deleteById(7L);
    }
}
