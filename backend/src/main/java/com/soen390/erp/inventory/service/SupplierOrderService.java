package com.soen390.erp.inventory.service;

import com.soen390.erp.inventory.model.Supplierorder;
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

    public ArrayList<Supplierorder> getAllSupplierOrders(){
        Iterable<Supplierorder> supplierOrderList = repository.findAll();

        ArrayList<Supplierorder> result = new ArrayList<>();
        for (Supplierorder supplierorder : supplierOrderList) {
            result.add(supplierorder);
        }

        return result;
    }
}
