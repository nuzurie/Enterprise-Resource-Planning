package com.soen390.erp.inventory.service;

import com.soen390.erp.inventory.exceptions.PlantBikeNotFoundException;
import com.soen390.erp.inventory.model.PlantBike;
import com.soen390.erp.inventory.repository.PlantBikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantBikeService {

    private final PlantBikeRepository plantBikeRepository;

    public PlantBikeService(PlantBikeRepository plantBikeRepository) {
        this.plantBikeRepository = plantBikeRepository;
    }

    public PlantBike findPlantBikeById(int id) throws PlantBikeNotFoundException {

        if (!plantBikeRepository.existsById(id))
            throw new PlantBikeNotFoundException(id);

        return plantBikeRepository.findById(id);
    }


    public List<PlantBike> findAllPlantBikes() {
        return plantBikeRepository.findAll();
    }

    public PlantBike addPlantBike(PlantBike plantBike) {
        return plantBikeRepository.save(plantBike);
    }

    public boolean deletePlantBikeById(int id) {

        if (!plantBikeRepository.existsById(id))
            return false;

        plantBikeRepository.deleteById(id);

        return true;
    }
}
