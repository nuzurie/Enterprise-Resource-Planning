package com.soen390.erp.inventory.repository;

import com.soen390.erp.inventory.model.PlantBike;
import com.soen390.erp.manufacturing.model.Bike;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface PlantBikeRepository extends PagingAndSortingRepository<PlantBike, Integer> {

    Optional<PlantBike> findByBike(Bike bike);
}
