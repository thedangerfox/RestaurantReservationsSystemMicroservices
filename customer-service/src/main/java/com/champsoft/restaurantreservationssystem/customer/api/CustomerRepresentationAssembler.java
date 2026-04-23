package com.champsoft.restaurantreservationssystem.customer.api;

import com.champsoft.restaurantreservationssystem.customer.api.dto.CustomerResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerRepresentationAssembler
        implements RepresentationModelAssembler<CustomerResponse, EntityModel<CustomerResponse>> {

    @Override
    public EntityModel<CustomerResponse> toModel(CustomerResponse response) {
        return EntityModel.of(
                response,
                linkTo(methodOn(CustomerController.class).get(response.id())).withSelfRel(),
                linkTo(methodOn(CustomerController.class).list()).withRel("customers")
        );
    }
}
