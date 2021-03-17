package com.soen390.erp.accounting.service;

import com.soen390.erp.accounting.model.PurchaseOrder;
import com.soen390.erp.accounting.repository.PurchaseOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PurchaseOrderService {
    private final PurchaseOrderRepository repository;

    public boolean addPurchaseOrder(PurchaseOrder purchaseOrder){
        repository.save(purchaseOrder);
        if (purchaseOrder.getId() != 0){
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<PurchaseOrder> getAllPurchaseOrders() {

        Iterable<PurchaseOrder> purchaseOrders = repository.findAll();

        ArrayList<PurchaseOrder> result = new ArrayList<>();
        for (PurchaseOrder purchaseOrder : purchaseOrders) {
            result.add(purchaseOrder);
        }

        return result;
    }

    public Optional<PurchaseOrder>  getPurchaseOrder(int id) {
        Optional<PurchaseOrder> purchaseOrder = repository.findById(id);
        return purchaseOrder;
    }
}
