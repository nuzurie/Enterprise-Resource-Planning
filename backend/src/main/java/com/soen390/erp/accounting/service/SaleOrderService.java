package com.soen390.erp.accounting.service;

import com.soen390.erp.accounting.model.SaleOrder;
import com.soen390.erp.accounting.repository.SaleOrderRepository;
import com.soen390.erp.inventory.model.Plant;
import com.soen390.erp.inventory.repository.PlantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SaleOrderService {
    private final SaleOrderRepository repository;
    private final PlantRepository plantRepository;

    public boolean addSaleOrder(SaleOrder saleOrder){
        Plant plant = plantRepository.findById(1).get();
        saleOrder.setPlant(plant);
        repository.save(saleOrder);
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
