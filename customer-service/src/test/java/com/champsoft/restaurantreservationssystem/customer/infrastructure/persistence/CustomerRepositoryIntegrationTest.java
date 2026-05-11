package com.champsoft.restaurantreservationssystem.customer.infrastructure.persistence;



import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import com.champsoft.restaurantreservationssystem.customer.persistence.CustomerJpaEntity;
import com.champsoft.restaurantreservationssystem.customer.persistence.SpringDataCustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;



@DataJpaTest
@ActiveProfiles("testing")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CustomerRepositoryIntegrationTest {


    @Autowired
    private SpringDataCustomerRepository repository;

    @Test
    @DisplayName("Should save a customer successfully")
    void shouldSaveCustomerSuccessfully() {

        // ------------------- Arrange -------------------
        CustomerJpaEntity customer = new CustomerJpaEntity();
        customer.fullName = "John Doe";
        customer.phone = "555-1234";
        customer.status = "ACTIVE";

        // ------------------- Act -------------------
        CustomerJpaEntity saved = repository.save(customer);

        // ------------------- Assert -------------------
        assertThat(saved).isNotNull();
        assertThat(saved.id).isNotNull(); // Auto-generated ID
        assertThat(saved.fullName).isEqualTo("John Doe");
        assertThat(saved.phone).isEqualTo("555-1234");
        assertThat(saved.status).isEqualTo("ACTIVE");
    }

    @Test
    @DisplayName("Should find a customer by ID")
    void shouldFindCustomerById() {

        // ------------------- Arrange -------------------
        CustomerJpaEntity customer = new CustomerJpaEntity();
        customer.fullName = "Alice Smith";
        customer.phone = "555-9876";
        customer.status = "ACTIVE";

        CustomerJpaEntity saved = repository.save(customer);

        // ------------------- Act -------------------
        Optional<CustomerJpaEntity> found = repository.findById(saved.id);

        // ------------------- Assert -------------------
        assertThat(found).isPresent();
        assertThat(found.get().id).isEqualTo(saved.id);
        assertThat(found.get().fullName).isEqualTo("Alice Smith");
        assertThat(found.get().phone).isEqualTo("555-9876");
        assertThat(found.get().status).isEqualTo("ACTIVE");
    }

    @Test
    @DisplayName("Should return empty when customer ID is not found")
    void shouldReturnEmptyWhenCustomerIdNotFound() {

        // ------------------- Act -------------------
        Optional<CustomerJpaEntity> found = repository.findById(999L);

        // ------------------- Assert -------------------
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Should find all customers")
    void shouldFindAllCustomers() {

        // ------------------- Arrange -------------------
        CustomerJpaEntity c1 = new CustomerJpaEntity();
        c1.fullName = "Tom Hardy";
        c1.phone = "111-1111";
        c1.status = "ACTIVE";

        CustomerJpaEntity c2 = new CustomerJpaEntity();
        c2.fullName = "Emma Stone";
        c2.phone = "222-2222";
        c2.status = "INACTIVE";

        repository.save(c1);
        repository.save(c2);

        // ------------------- Act -------------------
        List<CustomerJpaEntity> all = repository.findAll();

        // ------------------- Assert -------------------
        assertThat(all)
                .extracting(c -> c.fullName)
                .contains("Tom Hardy", "Emma Stone");
    }


    @Test
    @DisplayName("Should delete a customer successfully")
    void shouldDeleteCustomerSuccessfully() {

        // ------------------- Arrange -------------------
        CustomerJpaEntity customer = new CustomerJpaEntity();
        customer.fullName = "Bruce Wayne";
        customer.phone = "999-9999";
        customer.status = "ACTIVE";

        CustomerJpaEntity saved = repository.save(customer);

        // ------------------- Act -------------------
        repository.deleteById(saved.id);

        Optional<CustomerJpaEntity> found = repository.findById(saved.id);

        // ------------------- Assert -------------------
        assertThat(found).isEmpty();
    }
}

