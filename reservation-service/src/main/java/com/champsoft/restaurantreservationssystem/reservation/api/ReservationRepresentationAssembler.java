package com.champsoft.restaurantreservationssystem.reservation.api;

import com.champsoft.restaurantreservationssystem.reservation.api.dto.ReservationResponse;
import com.champsoft.restaurantreservationssystem.reservation.domain.model.ReservationStatus;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ReservationRepresentationAssembler {

    public EntityModel<ReservationResponse> toModel(ReservationResponse response) {
        EntityModel<ReservationResponse> model = EntityModel.of(response);

        model.add(linkTo(methodOn(ReservationController.class).get(Long.valueOf(response.id()))).withSelfRel());
        model.add(linkTo(methodOn(ReservationController.class).list()).withRel("reservations"));

        if (response.status() == ReservationStatus.PENDING) {
            model.add(linkTo(methodOn(ReservationController.class)
                    .cancel(Long.valueOf(response.id())))
                    .withRel("cancel"));
        }

        return model;
    }

    public CollectionModel<EntityModel<ReservationResponse>> toCollectionModel(List<ReservationResponse> responses) {
        List<EntityModel<ReservationResponse>> items = responses.stream()
                .map(this::toModel)
                .toList();

        return CollectionModel.of(
                items,
                linkTo(methodOn(ReservationController.class).list()).withSelfRel()
        );
    }
}
