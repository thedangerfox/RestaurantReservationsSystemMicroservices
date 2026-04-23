package com.champsoft.restaurantreservationssystem.customer.application.port.out;

import com.champsoft.restaurantreservationssystem.customer.domain.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepositoryPort {

    Customer save(Customer customer);

    Optional<Customer> findById(Long id);

    List<Customer> findAll();

    void deleteById(Long id);
}
