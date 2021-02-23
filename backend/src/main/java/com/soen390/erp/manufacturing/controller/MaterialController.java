package com.soen390.erp.manufacturing.controller;

import com.soen390.erp.manufacturing.exceptions.MaterialNotFoundException;
import com.soen390.erp.manufacturing.model.Material;
import com.soen390.erp.manufacturing.repository.MaterialRepository;
import com.soen390.erp.manufacturing.service.MaterialModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MaterialController {

    private final MaterialRepository materialRepository;
    private final MaterialModelAssembler assembler;

    public MaterialController(MaterialRepository materialRepository, MaterialModelAssembler assembler) {
        this.materialRepository = materialRepository;
        this.assembler = assembler;
    }

    @GetMapping("/materials")
    public ResponseEntity<?> all() {

        List<EntityModel<Material>> materials = materialRepository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(
                CollectionModel.of(materials, linkTo(methodOn(MaterialController.class).all()).withSelfRel()));
    }

    @GetMapping(path = "/materials/{id}")
    public ResponseEntity<?> one(@PathVariable int id) {

        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new MaterialNotFoundException(id));

        return ResponseEntity.ok().body(assembler.toModel(material));
    }

    @PostMapping("/materials")
    ResponseEntity<?> newMaterial(@RequestBody Material material){


        EntityModel<Material> entityModel = assembler.toModel(materialRepository.save(material));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

}
