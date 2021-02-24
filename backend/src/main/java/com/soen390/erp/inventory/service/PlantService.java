package com.soen390.erp.inventory.service;

import com.soen390.erp.inventory.model.Plant;
import com.soen390.erp.inventory.model.PlantMaterial;
import com.soen390.erp.inventory.model.PlantPart;
import com.soen390.erp.inventory.repository.PlantMaterialRepository;
import com.soen390.erp.inventory.repository.PlantPartRepository;
import com.soen390.erp.inventory.repository.PlantRepository;
import com.soen390.erp.manufacturing.model.Material;
import com.soen390.erp.manufacturing.model.Part;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PlantService {

    private final PlantRepository plantRepository;
    private final PlantMaterialRepository plantMaterialRepository;
    private final PlantPartRepository plantPartRepository;

    public PlantService(PlantRepository plantRepository, PlantMaterialRepository plantMaterialRepository, PlantPartRepository plantPartRepository) {
        this.plantRepository = plantRepository;
        this.plantMaterialRepository = plantMaterialRepository;
        this.plantPartRepository = plantPartRepository;
    }

    public void addPlantPart(Plant plant, Part part, int quantity) {
        Set<PlantPart> parts = plant.getParts().orElseGet(() -> new HashSet<>());

//        Part part = plantPart.getPart();
        Set<Material> materials = part.getMaterials().orElseGet(() -> new HashSet<>());

        materials.stream()
                .map(plantMaterialRepository::findByMaterial)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(plantMaterial -> PlantMaterial.builder()
                        .id(plantMaterial.getId())
                        .material(plantMaterial.getMaterial())
                        .quantity(plantMaterial.getQuantity() - 1)
                        .build())
                .forEach(plantMaterialRepository::save);

        PlantPart plantPart = plantPartRepository.findByPart(part)
                .orElseGet(()->PlantPart.builder().part(part).quantity(quantity).build());
        parts.add(plantPart);
        plant.setParts(parts);
        plantPartRepository.save(plantPart);
        plantRepository.save(plant);
    }
}
