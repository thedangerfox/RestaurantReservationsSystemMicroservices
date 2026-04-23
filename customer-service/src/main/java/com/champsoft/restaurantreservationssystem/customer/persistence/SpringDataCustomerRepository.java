package com.champsoft.restaurantreservationssystem.customer.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataCustomerRepository
        extends JpaRepository<CustomerJpaEntity, Long> {
}
