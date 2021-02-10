package com.soen390.erp.manufacturing.repository;

import com.soen390.erp.manufacturing.model.Material;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MaterialRepository extends PagingAndSortingRepository<Material, String> {
}
