package com.soen390.erp.manufacturing.controller;

import com.soen390.erp.manufacturing.exceptions.BikeNotFoundException;
import com.soen390.erp.manufacturing.model.Bike;
import com.soen390.erp.manufacturing.repository.BikeRepository;
import com.soen390.erp.manufacturing.service.BikeModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BikeController {

    private final BikeRepository bikeRepository;
    private final BikeModelAssembler assembler;

    public BikeController(BikeRepository bikeRepository, BikeModelAssembler assembler) {
        this.bikeRepository = bikeRepository;
        this.assembler = assembler;
    }

    @GetMapping("/bikes")
    public ResponseEntity<?> all() {

        List<EntityModel<Bike>> bikes = bikeRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(
                CollectionModel.of(bikes, linkTo(methodOn(BikeController.class).all()).withSelfRel()));
    }

    @GetMapping(path = "/bikes/{id}")
    public ResponseEntity<?> one(@PathVariable int id) {

        Bike bike = bikeRepository.findById(id)
                .orElseThrow(() -> new BikeNotFoundException(id));

        return ResponseEntity.ok().body(assembler.toModel(bike));
    }

    @PostMapping("/bikes")
    ResponseEntity<?> newBike(@RequestBody Bike bike){

        EntityModel<Bike> entityModel = assembler.toModel(bikeRepository.save(bike));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
}
