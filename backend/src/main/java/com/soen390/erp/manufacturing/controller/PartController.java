package com.soen390.erp.manufacturing.controller;

import com.soen390.erp.manufacturing.exceptions.PartNotFoundException;
import com.soen390.erp.manufacturing.model.Part;
import com.soen390.erp.manufacturing.repository.MaterialRepository;
import com.soen390.erp.manufacturing.repository.PartRepository;
import com.soen390.erp.manufacturing.service.PartModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PartController {

    private final PartRepository partRepository;
    private final MaterialRepository materialRepository;
    private final PartModelAssembler assembler;

    public PartController(PartRepository partRepository, PartModelAssembler assembler, MaterialRepository materialRepository) {
        this.partRepository = partRepository;
        this.assembler = assembler;
        this.materialRepository = materialRepository;
    }

    @GetMapping("/parts")
    public ResponseEntity<?> all() {

        List<EntityModel<Part>> parts = partRepository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(
                CollectionModel.of(parts, linkTo(methodOn(PartController.class).all()).withSelfRel()));
    }

    @GetMapping(path = "/parts/{id}")
    public ResponseEntity<?> one(@PathVariable int id) {

        Part part = partRepository.findById(id)
                .orElseThrow(() -> new PartNotFoundException(id));

        return ResponseEntity.ok().body(assembler.toModel(part));
    }

    @PostMapping("/parts")
    ResponseEntity<?> newPart(@RequestBody Part part){

        part.getMaterials().forEach(materialRepository::save);
        EntityModel<Part> entityModel = assembler.toModel(partRepository.save(part));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

}
