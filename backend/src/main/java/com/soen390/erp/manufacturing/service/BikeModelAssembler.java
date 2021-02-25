package com.soen390.erp.manufacturing.service;

import com.soen390.erp.manufacturing.controller.BikeController;
import com.soen390.erp.manufacturing.model.Bike;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class BikeModelAssembler implements RepresentationModelAssembler<Bike, EntityModel<Bike>> {

    @Override
    public EntityModel<Bike> toModel(Bike bike) {
        return EntityModel.of(bike,
                linkTo(methodOn(BikeController.class).one(bike.getId())).withSelfRel(),
                linkTo(methodOn(BikeController.class).all()).withRel("bikes"));
    }
}
