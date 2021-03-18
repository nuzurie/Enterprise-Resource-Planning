package com.soen390.erp.inventory.controller;


import com.soen390.erp.inventory.exceptions.PlantNotFoundException;
import com.soen390.erp.inventory.model.Plant;
import com.soen390.erp.inventory.model.PlantBike;
import com.soen390.erp.inventory.model.PlantPart;
import com.soen390.erp.inventory.repository.PlantRepository;
import com.soen390.erp.inventory.service.PlantModelAssembler;
import com.soen390.erp.inventory.service.PlantService;
import com.soen390.erp.manufacturing.model.Bike;
import com.soen390.erp.manufacturing.model.Part;
import com.soen390.erp.inventory.model.PlantMaterial;
import com.soen390.erp.manufacturing.model.Material;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
// All plants created
// All information in a plant (materials, parts, bikes) or maybe have orders?

@RestController

public class PlantController {
    private final PlantRepository plantRepository;
    private final PlantModelAssembler pmAssembler;
    private final PlantService plantService;
    public PlantController(PlantRepository plantRepository, PlantModelAssembler pmAssembler, PlantService plantService){
        this.plantRepository = plantRepository;
        this.pmAssembler = pmAssembler;
        this.plantService = plantService;
    }

    @GetMapping("/plants")
    public ResponseEntity<?> all(){
        List<EntityModel<Plant>> plants = plantRepository.findAll().stream()
                .map(pmAssembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(
                CollectionModel.of(plants, linkTo(methodOn(this.getClass()).all()).withSelfRel()));
    }

    @GetMapping("/plants/{id}")
    public ResponseEntity<?> one(@PathVariable int id){
        Plant plant = plantRepository.findById(id).orElseThrow(() -> new PlantNotFoundException(id));
        return ResponseEntity.ok().body(pmAssembler.toModel(plant));
    }


    @PostMapping("/addPartToInventory")
    ResponseEntity<?> addPartToInventory(@RequestBody PlantPart plantPart) {

        int plantId = 1;
        Plant plant = plantRepository.findById(1).orElseThrow(()->new RuntimeException());
        Part part = plantPart.getPart();
        int quantity = plantPart.getQuantity();
        plantService.addPlantPart(plant, part, quantity);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ResponseBody
    @ExceptionHandler(PlantNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String partNotFoundException(PlantNotFoundException ex){
        return ex.getMessage();
    }




    @PostMapping("/addMaterialToInventory")
    ResponseEntity<?> addMaterialToInventory(@RequestBody PlantMaterial plantMaterial) {
        //TODO: get from header?
        int plantId = 1;

        Plant plant = plantRepository.findById(plantId).orElseThrow(()-> new RuntimeException());
        Material material = plantMaterial.getMaterial();
        int quantity = plantMaterial.getQuantity();
        plantService.addPlantMaterial(plant,material,quantity);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/addBikeToInventory")
    ResponseEntity<?> addBikeToInventory(@RequestBody PlantBike plantBike) {
        //TODO: get from header?
        int plantId = 1;

        Plant plant = plantRepository.findById(plantId).orElseThrow(()-> new RuntimeException());
        Bike bike = plantBike.getBike();
        int quantity = plantBike.getQuantity();
        plantService.addPlantBike(plant,bike,quantity);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

