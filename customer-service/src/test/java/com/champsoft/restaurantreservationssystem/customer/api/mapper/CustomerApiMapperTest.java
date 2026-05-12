package com.champsoft.restaurantreservationssystem.customer.api.mapper;

import com.champsoft.restaurantreservationssystem.customer.domain.model.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerApiMapperTest {

    @Test
    void shouldMapCustomerToResponse() {
        var customer = new Customer(
                CustomerId.of(42L),
                new CustomerName("Jane"),
                new CustomerPhone("555-1234")
        );

        var response = CustomerApiMapper.toResponse(customer);

        assertThat(response.id()).isEqualTo(42L);
        assertThat(response.fullName()).isEqualTo("Jane");
        assertThat(response.phone()).isEqualTo("555-1234");
        assertThat(response.status()).isEqualTo("ACTIVE");
    }

    @Test
    void shouldMapDeactivatedCustomer() {
        var customer = new Customer(
                CustomerId.of(2L),
                new CustomerName("Jim"),
                new CustomerPhone("0")
        );
        customer.deactivate();

        var response = CustomerApiMapper.toResponse(customer);

        assertThat(response.status()).isEqualTo("INACTIVE");
    }

    @Test
    void mapperCanBeInstantiated() {
        new CustomerApiMapper();
    }
}
