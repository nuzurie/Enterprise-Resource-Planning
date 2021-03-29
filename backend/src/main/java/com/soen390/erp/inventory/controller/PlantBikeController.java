package com.soen390.erp.inventory.controller;

import com.soen390.erp.inventory.exceptions.InvalidPlantBikeException;
import com.soen390.erp.inventory.model.PlantBike;
import com.soen390.erp.inventory.service.PlantBikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plant-bikes")
public class PlantBikeController {

    private final PlantBikeService plantBikeService;

    public PlantBikeController(PlantBikeService plantBikeService) {
        this.plantBikeService = plantBikeService;
    }

    @GetMapping
    public List<PlantBike> getAllPlantBikes() {
        return plantBikeService.findAllPlantBikes();
    }

    @PostMapping
    public ResponseEntity<?> addPlantBike(@RequestBody PlantBike plantBike) {
        try {
            plantBikeService.addPlantBike(plantBike);

        } catch (InvalidPlantBikeException e) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("The Plant Bike was successfully added with id " + plantBike.getId());
    }
}
