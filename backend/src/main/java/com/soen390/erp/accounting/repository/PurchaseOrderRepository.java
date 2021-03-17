package com.soen390.erp.accounting.repository;

import com.soen390.erp.accounting.model.PurchaseOrder;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PurchaseOrderRepository extends PagingAndSortingRepository<PurchaseOrder, Integer> {
}
