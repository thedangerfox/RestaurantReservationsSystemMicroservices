package com.champsoft.restaurantreservationssystem.customer.persistence;

import com.champsoft.restaurantreservationssystem.customer.domain.model.*;

public class CustomerMapper {

    public static CustomerJpaEntity toEntity(Customer c) {
        var e = new CustomerJpaEntity();
        e.id = c.id().value();
        e.fullName = c.name().value();
        e.phone = c.phone().value();
        e.status = c.status().name(); // FIXED
        return e;
    }

    public static Customer toDomain(CustomerJpaEntity e) {
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
