package com.soen390.erp.inventory.repository;

import com.soen390.erp.inventory.model.SupplierOrder;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SupplierOrderRepository extends PagingAndSortingRepository<SupplierOrder, Integer> {
}
