package com.soen390.erp.inventory.service;

import com.soen390.erp.inventory.exceptions.NotEnoughMaterialInPlantException;
import com.soen390.erp.inventory.exceptions.NotEnoughPartsInPlantException;
import com.soen390.erp.inventory.model.Plant;
import com.soen390.erp.inventory.model.PlantBike;
import com.soen390.erp.inventory.model.PlantMaterial;
import com.soen390.erp.inventory.model.PlantPart;
import com.soen390.erp.inventory.repository.PlantBikeRepository;
import com.soen390.erp.inventory.repository.PlantMaterialRepository;
import com.soen390.erp.inventory.repository.PlantPartRepository;
import com.soen390.erp.inventory.repository.PlantRepository;
import com.soen390.erp.manufacturing.exceptions.MaterialNotFoundException;
import com.soen390.erp.manufacturing.exceptions.PartNotFoundException;
import com.soen390.erp.manufacturing.model.Bike;
import com.soen390.erp.manufacturing.model.Material;
import com.soen390.erp.manufacturing.model.Part;
import com.soen390.erp.manufacturing.repository.BikeRepository;
import com.soen390.erp.manufacturing.repository.MaterialRepository;
import com.soen390.erp.manufacturing.repository.PartRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PlantService {

    private final PlantRepository plantRepository;
    private final PlantMaterialRepository plantMaterialRepository;
    private final PlantPartRepository plantPartRepository;
    private final PlantBikeRepository plantBikeRepository;
    private final PartRepository partRepository;
    private final MaterialRepository materialRepository;
    private final BikeRepository bikeRepository;

    public PlantService(PlantRepository plantRepository, PlantMaterialRepository plantMaterialRepository, PlantPartRepository plantPartRepository, PlantBikeRepository plantBikeRepository, PartRepository partRepository, MaterialRepository materialRepository, BikeRepository bikeRepository) {
        this.plantRepository = plantRepository;
        this.plantMaterialRepository = plantMaterialRepository;
        this.plantPartRepository = plantPartRepository;
        this.plantBikeRepository = plantBikeRepository;
        this.partRepository = partRepository;
        this.materialRepository = materialRepository;
        this.bikeRepository = bikeRepository;
    }
    public void addPlantMaterial(Plant plant, Material material, int quantity) {

        materialRepository.findById(material.getId()).orElseThrow(() -> new MaterialNotFoundException(material.getId()));
        //try to find the pm from the pm repository
        PlantMaterial pm = plantMaterialRepository.findByMaterial(material)
                .orElseGet(() -> PlantMaterial.builder().material(material).build());

        //increase the quantity
        pm.setQuantity(pm.getQuantity() + quantity);
        plantMaterialRepository.save(pm);

        plant.addMaterial(pm);
        plantRepository.save(plant);
    }

    public void addPlantPart(Plant plant, Part part, int quantity) {

        partRepository.findById(part.getId()).orElseThrow(()->new PartNotFoundException(part.getId()));

        Set<PlantPart> parts = plant.getParts().orElseGet(() -> new HashSet<>());
        Set<Material> materials = part.getMaterials().orElseGet(() -> new HashSet<>());

        Stream<PlantMaterial> materialsInPlantForPart = materials.stream()
                .map(plantMaterialRepository::findByMaterial)
                .filter(Optional::isPresent)
                .map(Optional::get);


        Set<PlantMaterial> materialsInPlant = materialsInPlantForPart.collect(Collectors.toSet());
        if (materialsInPlant.size()!=materials.size())
            throw new NotEnoughMaterialInPlantException("Please ensure all the materials are present.");


        Set<PlantMaterial> pm = materialsInPlant.stream()
                .map(plantMaterial -> PlantMaterial.builder()
                        .id(plantMaterial.getId())
                        .material(plantMaterial.getMaterial())
                        .quantity(plantMaterial.getQuantity() - quantity)
                        .build())
                .collect(Collectors.toSet());

        //check if the quantity of matrerial has gone below 0
        pm.forEach(plantMaterial -> {
            if (plantMaterial.getQuantity()<0)
                throw new NotEnoughMaterialInPlantException("Not enough material with id " + plantMaterial.getMaterial().getId());
        });
        //otherwise save
        pm.forEach(plantMaterialRepository::save);

        PlantPart plantPart = plantPartRepository.findByPart(part)
                .orElseGet(()->PlantPart.builder().part(part).build());

        plantPart.setQuantity(plantPart.getQuantity()+quantity);
        parts.add(plantPart);
        plant.setParts(parts);
        plantPartRepository.save(plantPart);
        plantRepository.save(plant);
    }

    public void addPlantBike(Plant plant, Bike bike, int quantity){

        //check if the bike exists in the repo, if not add it
        bikeRepository.save(bikeRepository.findById(bike.getId()).orElse(bike));

        //get all the parts of the bikes and extract it to a set
        Set<Part> bikeParts = Set.of(bike.getFrame(), bike.getFrontwheel(), bike.getHandlebar(), bike.getRearwheel(),
                bike.getPedal(), bike.getSeat());

        Stream<PlantPart> plantPartInInventory = bikeParts.stream()
                .map(plantPartRepository::findByPart)
                .filter(Optional::isPresent)
                .map(Optional::get);

        Set<PlantPart> partsInPlant = plantPartInInventory.collect(Collectors.toSet());

        //TODO: Make this to return the specific parts not available
        if (partsInPlant.size()!=bikeParts.size())
            throw new NotEnoughPartsInPlantException("Please ensure all the parts are present.");

        Set<PlantPart> pp = partsInPlant.stream()
                .map(plantPart -> PlantPart.builder()
                        .id(plantPart.getId())
                        .part(plantPart.getPart())
                        .quantity(plantPart.getQuantity() - quantity)
                        .build())
                .collect(Collectors.toSet());

        //check if the quantity of part has gone below 0
        pp.forEach(plantPart -> {
            if (plantPart.getQuantity()<0)
                throw new NotEnoughMaterialInPlantException("Not enough parts with id " + plantPart.getPart().getId());
        });
        //otherwise save
        pp.forEach(plantPartRepository::save);

        PlantBike plantBike = plantBikeRepository.findByBike(bike)
                .orElseGet(()->PlantBike.builder().bike(bike).build());
        //increment the quantity
        plantBike.setQuantity(plantBike.getQuantity()+quantity);
        plantBikeRepository.save(plantBike);

        plant.addBike(plantBike);
        plantRepository.save(plant);

    }

}
