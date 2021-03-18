package com.soen390.erp.accounting.service;

import com.soen390.erp.accounting.model.PurchaseOrder;
import com.soen390.erp.accounting.model.PurchaseOrderItems;
import com.soen390.erp.accounting.model.SaleOrderItems;
import com.soen390.erp.accounting.repository.PurchaseOrderRepository;
import com.soen390.erp.manufacturing.repository.MaterialRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PurchaseOrderService {
    private final PurchaseOrderRepository repository;
    private final MaterialRepository materialRepository;

    public boolean addPurchaseOrder(PurchaseOrder purchaseOrder){
        purchaseOrder.setDate(new Date());

        double totalPrice = 0;
        for (PurchaseOrderItems purchaseOrderItem: purchaseOrder.getPurchaseOrderItems()
             ) {
            totalPrice += purchaseOrderItem.getUnitPrice()*purchaseOrderItem.getQuantity();
            materialRepository.save(purchaseOrderItem.getMaterial());
        }

        double tax = .15;
        purchaseOrder.setTax(tax);

        double taxAmount = totalPrice*tax;
        purchaseOrder.setTaxAmount(taxAmount);

        double grandTotal = taxAmount+totalPrice;
        purchaseOrder.setGrandTotal(grandTotal);

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
