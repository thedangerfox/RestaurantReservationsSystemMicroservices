package com.champsoft.restaurantreservationssystem.customer.application.service;

import com.champsoft.restaurantreservationssystem.customer.application.exception.CustomerNotFoundException;
import com.champsoft.restaurantreservationssystem.customer.application.port.out.CustomerRepositoryPort;
import com.champsoft.restaurantreservationssystem.customer.domain.model.Customer;
import com.champsoft.restaurantreservationssystem.customer.domain.model.CustomerId;
import com.champsoft.restaurantreservationssystem.customer.domain.model.CustomerName;
import com.champsoft.restaurantreservationssystem.customer.domain.model.CustomerPhone;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerCrudService {

    private final CustomerRepositoryPort repo;

    public CustomerCrudService(CustomerRepositoryPort repo) {
        this.repo = repo;
    }

    @Transactional
    public Customer create(String fullName, String phone) {
        var customer = new Customer(
                CustomerId.newId(),
                new CustomerName(fullName),
                new CustomerPhone(phone)
        );
        return repo.save(customer);
    }

    @Transactional(readOnly = true)
    public Customer getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<Customer> list() {
        return repo.findAll();
    }

    @Transactional
    public Customer update(Long id, String fullName, String phone) {
        var c = getById(id);
        c.update(new CustomerName(fullName), new CustomerPhone(phone));
        return repo.save(c);
    }

    @Transactional
    public void delete(Long id) {
        getById(id);
        repo.deleteById(id);
    }
}
