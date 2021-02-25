package com.soen390.erp.inventory.controller;

import com.soen390.erp.inventory.model.SupplierOrder;
import com.soen390.erp.inventory.service.SupplierOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class SupplierOrderController {

    @Autowired
    private SupplierOrderService service;

    @GetMapping(path = "/SupplierOrders")
    public ArrayList<SupplierOrder> getAllSupplierOrders(){
        return service.getAllSupplierOrders();
    }

    @PostMapping(path = "/SupplierOrders")
    public ResponseEntity<?> createSupplierOrder(@RequestBody SupplierOrder supplierOrder){
        //TODO: validate input
        boolean isSuccessful = service.insertSupplierOrder(supplierOrder);
        if (isSuccessful == true){
            return ResponseEntity.status(HttpStatus.OK).body(supplierOrder.getId());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);
        }
    }
}
