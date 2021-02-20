package com.soen390.erp.inventory.controller;

import com.soen390.erp.inventory.model.Supplierorder;
import com.soen390.erp.inventory.service.SupplierOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class SupplierOrderController {

    @Autowired
    private SupplierOrderService service;

    @GetMapping(path = "/SupplierOrders")
    public ArrayList<Supplierorder> getAllSupplierOrders(){
        return service.getAllSupplierOrders();
    }
}
