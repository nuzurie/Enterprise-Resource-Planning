package com.soen390.erp.inventory.service;

import com.soen390.erp.inventory.model.SupplierOrder;
import com.soen390.erp.inventory.repository.SupplierOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SupplierOrderService {

    SupplierOrderRepository repository;

    @Autowired
    public SupplierOrderService(SupplierOrderRepository repository) {
        this.repository = repository;
    }

    public ArrayList<SupplierOrder> getAllSupplierOrders(){
        Iterable<SupplierOrder> supplierOrderList = repository.findAll();

        ArrayList<SupplierOrder> result = new ArrayList<>();
        for (SupplierOrder supplierOrder : supplierOrderList) {
            result.add(supplierOrder);
        }

        return result;
    }
}
