package com.soen390.erp.inventory.service;

import com.soen390.erp.inventory.controller.PlantController;
import com.soen390.erp.inventory.model.Plant;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;


@Component
public class PlantModelAssembler implements RepresentationModelAssembler<Plant, EntityModel<Plant>> {
    public EntityModel<Plant> toModel(Plant plant){
        return EntityModel.of(plant,
                linkTo(methodOn(PlantController.class).one(plant.getId())).withSelfRel(),
                linkTo(methodOn(PlantController.class).all()).withRel("plants"));
    }
}
