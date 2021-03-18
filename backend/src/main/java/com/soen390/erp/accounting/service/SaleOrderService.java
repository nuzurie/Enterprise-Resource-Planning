package com.soen390.erp.accounting.service;

import com.soen390.erp.accounting.model.SaleOrder;
import com.soen390.erp.accounting.model.SaleOrderItems;
import com.soen390.erp.accounting.repository.SaleOrderRepository;
import com.soen390.erp.inventory.model.Plant;
import com.soen390.erp.inventory.repository.PlantRepository;
import com.soen390.erp.manufacturing.repository.BikeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SaleOrderService {
    private final SaleOrderRepository repository;
    private final PlantRepository plantRepository;
    private final BikeRepository bikeRepository;

    public boolean addSaleOrder(SaleOrder saleOrder){
        // set the plant
        Plant plant = plantRepository.findById(1).get();
        saleOrder.setPlant(plant);

        // set date
        saleOrder.setDate(new Date());
        //calculate the total price
        double totalPrice = 0;
        for(SaleOrderItems saleOrderItem : saleOrder.getSaleOrderItems()){
            totalPrice += saleOrderItem.getQuantity()*saleOrderItem.getUnitPrice();
            bikeRepository.save(saleOrderItem.getBike());
        }
        saleOrder.setTotalAmount(totalPrice);

        //tax price
        double tax = .15;
        saleOrder.setTax(tax);
        double taxAmount = totalPrice * tax;
        saleOrder.setTaxAmount(taxAmount);

        //grand total
        double grandTotal = taxAmount+totalPrice;
        saleOrder.setGrandTotal(grandTotal);

        saleOrder = repository.save(saleOrder);
        if (saleOrder.getId() != 0){
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<SaleOrder> getAllSaleOrders()
    {
        return repository.findAll();
    }

    public Optional<SaleOrder>  getSaleOrder(int id) {
        Optional<SaleOrder> saleOrder = repository.findById(id);
        return saleOrder;
    }
}
