package com.champsoft.restaurantreservationssystem.customer.api.mapper;

import com.champsoft.restaurantreservationssystem.customer.api.dto.CustomerResponse;
import com.champsoft.restaurantreservationssystem.customer.domain.model.Customer;

public class CustomerApiMapper {

    public static CustomerResponse toResponse(Customer c) {
        return new CustomerResponse(
                c.id().value(),
                c.name().value(),
                c.phone().value(),
                c.status().name()
        );
    }
}
