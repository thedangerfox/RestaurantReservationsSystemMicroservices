package com.champsoft.restaurantreservationssystem.customer.persistence;

import com.champsoft.restaurantreservationssystem.customer.domain.model.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerMapperTest {

    @Test
    void shouldMapDomainToEntity() {
        // Arrange
        var customer = new Customer(
                CustomerId.of(42L),
                new CustomerName("Jane Doe"),
                new CustomerPhone("555-0000")
        );

        // Act
        var entity = CustomerMapper.toEntity(customer);

        // Assert
        assertThat(entity.id).isEqualTo(42L);
        assertThat(entity.fullName).isEqualTo("Jane Doe");
        assertThat(entity.phone).isEqualTo("555-0000");
        assertThat(entity.status).isEqualTo("ACTIVE");
    }

    @Test
    void shouldMapDeactivatedDomainToEntity() {
        // Arrange
        var customer = new Customer(
                CustomerId.of(7L),
                new CustomerName("Inactive"),
                new CustomerPhone("000-0000")
        );
        customer.deactivate();

        // Act
        var entity = CustomerMapper.toEntity(customer);

        // Assert
        assertThat(entity.status).isEqualTo("INACTIVE");
    }

    @Test
    void shouldMapEntityToDomain() {
        // Arrange
        var entity = new CustomerJpaEntity();
        entity.id = 5L;
        entity.fullName = "Bob";
        entity.phone = "111-2222";
        entity.status = "ACTIVE";

        // Act
        var domain = CustomerMapper.toDomain(entity);

        // Assert
        assertThat(domain.id().value()).isEqualTo(5L);
        assertThat(domain.name().value()).isEqualTo("Bob");
        assertThat(domain.phone().value()).isEqualTo("111-2222");
        assertThat(domain.status()).isEqualTo(CustomerStatus.ACTIVE);
    }

    @Test
    void shouldMapInactiveEntityToDomain() {
        // Arrange
        var entity = new CustomerJpaEntity();
        entity.id = 6L;
        entity.fullName = "Eve";
        entity.phone = "999-9999";
        entity.status = "INACTIVE";

        // Act
        var domain = CustomerMapper.toDomain(entity);

        // Assert
        assertThat(domain.status()).isEqualTo(CustomerStatus.INACTIVE);
    }

    @Test
    void shouldHandleCaseInsensitiveInactiveStatus() {
        // Arrange
        var entity = new CustomerJpaEntity();
        entity.id = 9L;
        entity.fullName = "Sam";
        entity.phone = "555-5555";
        entity.status = "inactive";

        // Act
        var domain = CustomerMapper.toDomain(entity);

        // Assert
        assertThat(domain.status()).isEqualTo(CustomerStatus.INACTIVE);
    }

    @Test
    void mapperCanBeInstantiated() {
        // simply for coverage of default constructor
        new CustomerMapper();
    }
}
