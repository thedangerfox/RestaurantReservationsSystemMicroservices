package com.champsoft.restaurantreservationssystem.customer.domain.model;



import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {

    @Test
    void shouldCreateCustomerWithActiveStatus() {

        // ------------------- Arrange -------------------
        CustomerId id = CustomerId.of(1L);
        CustomerName name = new CustomerName("John Doe");
        CustomerPhone phone = new CustomerPhone("555-1234");

        // ------------------- Act -------------------
        Customer customer = new Customer(id, name, phone);

        // ------------------- Assert -------------------
        assertThat(customer.id()).isEqualTo(id);
        assertThat(customer.name()).isEqualTo(name);
        assertThat(customer.phone()).isEqualTo(phone);

        // New customers start ACTIVE
        assertThat(customer.status()).isEqualTo(CustomerStatus.ACTIVE);
    }

    @Test
    void shouldUpdateCustomerInformationSuccessfully() {

        // ------------------- Arrange -------------------
        Customer customer = new Customer(
                CustomerId.of(1L),
                new CustomerName("Old Name"),
                new CustomerPhone("111-1111")
        );

        CustomerName newName = new CustomerName("New Name");
        CustomerPhone newPhone = new CustomerPhone("222-2222");

        // ------------------- Act -------------------
        customer.update(newName, newPhone);

        // ------------------- Assert -------------------
        assertThat(customer.name()).isEqualTo(newName);
        assertThat(customer.phone()).isEqualTo(newPhone);

        // Status should not change when updating info
        assertThat(customer.status()).isEqualTo(CustomerStatus.ACTIVE);
    }

    @Test
    void shouldDeactivateCustomerSuccessfully() {

        // ------------------- Arrange -------------------
        Customer customer = new Customer(
                CustomerId.of(1L),
                new CustomerName("Alice"),
                new CustomerPhone("555-9876")
        );

        // ------------------- Act -------------------
        customer.deactivate();

        // ------------------- Assert -------------------
        assertThat(customer.status()).isEqualTo(CustomerStatus.INACTIVE);
    }

    @Test
    void shouldAllowUpdatingCustomerEvenAfterDeactivation() {

        // ------------------- Arrange -------------------
        Customer customer = new Customer(
                CustomerId.of(1L),
                new CustomerName("Bob"),
                new CustomerPhone("333-3333")
        );

        customer.deactivate();

        CustomerName updatedName = new CustomerName("Updated Bob");
        CustomerPhone updatedPhone = new CustomerPhone("444-4444");

        // ------------------- Act -------------------
        customer.update(updatedName, updatedPhone);

        // ------------------- Assert -------------------
        assertThat(customer.name()).isEqualTo(updatedName);
        assertThat(customer.phone()).isEqualTo(updatedPhone);

        // Status remains INACTIVE
        assertThat(customer.status()).isEqualTo(CustomerStatus.INACTIVE);
    }
}

