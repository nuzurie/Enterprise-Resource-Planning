package com.soen390.erp.manufacturing.repository;

import com.soen390.erp.manufacturing.model.Part;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PartRepository extends PagingAndSortingRepository<Part, Integer> {
}
