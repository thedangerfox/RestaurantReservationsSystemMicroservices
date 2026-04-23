package com.champsoft.restaurantreservationssystem.menu.api;


import com.champsoft.restaurantreservationssystem.menu.api.dto.MenuItemResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MenuItemRepresentationAssembler
        implements RepresentationModelAssembler<MenuItemResponse, EntityModel<MenuItemResponse>> {

    @Override
    public EntityModel<MenuItemResponse> toModel(MenuItemResponse response) {
        return EntityModel.of(
                response,
                linkTo(methodOn(MenuItemController.class).get(response.id())).withSelfRel(),
                linkTo(methodOn(MenuItemController.class).list()).withRel("menu-items"),
                linkTo(methodOn(MenuItemController.class).update(response.id(), null)).withRel("update"),
                linkTo(methodOn(MenuItemController.class).delete(response.id())).withRel("delete")
        );
    }
}
