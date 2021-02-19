package com.soen390.erp.manufacturing.repository;

import com.soen390.erp.manufacturing.model.Bike;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BikeRepository extends PagingAndSortingRepository<Bike, Integer> {
}
