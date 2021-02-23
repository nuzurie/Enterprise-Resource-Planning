package com.soen390.erp.manufacturing.service;

import com.soen390.erp.manufacturing.controller.MaterialController;
import com.soen390.erp.manufacturing.model.Material;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;


@Component
public class MaterialModelAssembler implements RepresentationModelAssembler<Material, EntityModel<Material>> {

    @Override
    public EntityModel<Material> toModel(Material material) {
        return EntityModel.of(material,
                linkTo(methodOn(MaterialController.class).one(material.getId())).withSelfRel(),
                linkTo(methodOn(MaterialController.class).all()).withRel("materials"));
    }
}
