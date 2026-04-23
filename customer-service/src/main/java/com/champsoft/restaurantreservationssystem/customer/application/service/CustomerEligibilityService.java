package com.champsoft.restaurantreservationssystem.customer.application.service;

import com.champsoft.restaurantreservationssystem.customer.application.exception.CustomerNotFoundException;
import com.champsoft.restaurantreservationssystem.customer.application.port.out.CustomerRepositoryPort;
import com.champsoft.restaurantreservationssystem.customer.domain.model.CustomerStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerEligibilityService {

    private final CustomerRepositoryPort repo;

    public CustomerEligibilityService(CustomerRepositoryPort repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public boolean isEligible(Long customerId) {
        return repo.findById(customerId)
                .map(c -> c.status() == CustomerStatus.ACTIVE)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + customerId));
    }
}
