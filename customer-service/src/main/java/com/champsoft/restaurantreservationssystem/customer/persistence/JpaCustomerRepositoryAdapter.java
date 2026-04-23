package com.champsoft.restaurantreservationssystem.customer.persistence;

import com.champsoft.restaurantreservationssystem.customer.application.port.out.CustomerRepositoryPort;
import com.champsoft.restaurantreservationssystem.customer.domain.model.Customer;
import com.champsoft.restaurantreservationssystem.customer.domain.model.CustomerId;
import com.champsoft.restaurantreservationssystem.customer.domain.model.CustomerName;
import com.champsoft.restaurantreservationssystem.customer.domain.model.CustomerPhone;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JpaCustomerRepositoryAdapter implements CustomerRepositoryPort {

    private final SpringDataCustomerRepository jpa;

    public JpaCustomerRepositoryAdapter(SpringDataCustomerRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Customer save(Customer customer) {
        var entity = toEntity(customer);
        var saved = jpa.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return jpa.findById(id).map(this::toDomain);
    }

    @Override
    public List<Customer> findAll() {
        return jpa.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }

    // -------------------------
    // Mapping Helpers
    // -------------------------

    private CustomerJpaEntity toEntity(Customer c) {
        var e = new CustomerJpaEntity();
        e.id = c.id().value();
        e.fullName = c.name().value();
        e.phone = c.phone().value();
        e.status = c.status().name();
        return e;
    }

    private Customer toDomain(CustomerJpaEntity e) {
        var c = new Customer(
                new CustomerId(e.id),
                new CustomerName(e.fullName),
                new CustomerPhone(e.phone)
        );

        if ("INACTIVE".equalsIgnoreCase(e.status)) {
            c.deactivate();
        }

        return c;
    }
}
