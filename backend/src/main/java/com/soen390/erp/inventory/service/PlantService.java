package com.soen390.erp.inventory.service;

import com.soen390.erp.inventory.exceptions.NotEnoughMaterialInPlantException;
import com.soen390.erp.inventory.model.Plant;
import com.soen390.erp.inventory.model.PlantMaterial;
import com.soen390.erp.inventory.model.PlantPart;
import com.soen390.erp.inventory.repository.PlantMaterialRepository;
import com.soen390.erp.inventory.repository.PlantPartRepository;
import com.soen390.erp.inventory.repository.PlantRepository;
import com.soen390.erp.manufacturing.exceptions.MaterialNotFoundException;
import com.soen390.erp.manufacturing.exceptions.PartNotFoundException;
import com.soen390.erp.manufacturing.model.Material;
import com.soen390.erp.manufacturing.model.Part;
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
    private final PartRepository partRepository;
    private final MaterialRepository materialRepository;

    public PlantService(PlantRepository plantRepository, PlantMaterialRepository plantMaterialRepository, PlantPartRepository plantPartRepository, PartRepository partRepository, MaterialRepository materialRepository) {
        this.plantRepository = plantRepository;
        this.plantMaterialRepository = plantMaterialRepository;
        this.plantPartRepository = plantPartRepository;
        this.partRepository = partRepository;
        this.materialRepository = materialRepository;
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

        materialsInPlant.stream()
                .map(plantMaterial -> PlantMaterial.builder()
                        .id(plantMaterial.getId())
                        .material(plantMaterial.getMaterial())
                        .quantity(plantMaterial.getQuantity())
                        .build())
                .forEach(plantMaterialRepository::save);

        PlantPart plantPart = plantPartRepository.findByPart(part)
                .orElseGet(()->PlantPart.builder().part(part).build());

        plantPart.setQuantity(plantPart.getQuantity()+quantity);
        parts.add(plantPart);
        plant.setParts(parts);
        plantPartRepository.save(plantPart);
        plantRepository.save(plant);
    }

    public void addPlantMaterial(Plant plant, Material material, int quantity){

        materialRepository.findById(material.getId()).orElseThrow(()->new MaterialNotFoundException(material.getId()));
        //try to find the pm from the pm repository
        PlantMaterial pm = plantMaterialRepository.findByMaterial(material)
                .orElseGet(()->PlantMaterial.builder().material(material).build());

        //increase the quantity
        System.out.println(pm.getId());
        pm.setQuantity(pm.getQuantity()+quantity);
        plantMaterialRepository.save(pm);

//        Set<PlantMaterial> plantMaterials = plant.getMaterials().orElseGet(() -> new HashSet<>());
//        plantMaterials.add(pm);
//
        System.out.println(plant.getId());
        plant.addMaterial(pm);
        plantRepository.save(plant);

//        if (materials == null){
//            materials = new HashSet<>();
//        }
//        this.materials.add(plantMaterial);
    }

}
