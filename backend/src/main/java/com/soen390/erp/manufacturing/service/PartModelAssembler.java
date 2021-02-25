package com.soen390.erp.manufacturing.service;

import com.soen390.erp.manufacturing.controller.MaterialController;
import com.soen390.erp.manufacturing.controller.PartController;
import com.soen390.erp.manufacturing.model.Material;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.soen390.erp.manufacturing.model.Part;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;


@Component
public class PartModelAssembler implements RepresentationModelAssembler<Part, EntityModel<Part>> {

    @Override
    public EntityModel<Part> toModel(Part part) {
        return EntityModel.of(part,
                linkTo(methodOn(PartController.class).one(part.getId())).withSelfRel(),
                linkTo(methodOn(PartController.class).all()).withRel("parts"));
    }
}
