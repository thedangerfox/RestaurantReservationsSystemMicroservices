package com.champsoft.restaurantreservationssystem.table.api;

import com.champsoft.restaurantreservationssystem.table.api.dto.TableResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class TableRepresentationAssembler
        implements RepresentationModelAssembler<TableResponse, EntityModel<TableResponse>> {

    @Override
    public EntityModel<TableResponse> toModel(TableResponse response) {
        return EntityModel.of(
                response,
                linkTo(methodOn(TableController.class).get(response.id())).withSelfRel(),
                linkTo(methodOn(TableController.class).list()).withRel("tables")
        );
    }
}
