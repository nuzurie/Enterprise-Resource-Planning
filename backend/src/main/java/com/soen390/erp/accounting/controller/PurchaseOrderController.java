package com.soen390.erp.accounting.controller;

import com.soen390.erp.accounting.model.Account;
import com.soen390.erp.accounting.model.PurchaseOrder;
import com.soen390.erp.accounting.service.PurchaseOrderService;
import com.soen390.erp.inventory.model.SupplierOrder;
import com.soen390.erp.manufacturing.controller.BikeController;
import com.soen390.erp.manufacturing.exceptions.BikeNotFoundException;
import com.soen390.erp.manufacturing.model.Bike;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@RestController
public class PurchaseOrderController {
    private final PurchaseOrderService service;

    @GetMapping(path = "/PurchaseOrders")
    public ResponseEntity<?> all(){
        //TODO: use stream and return a mapped collection or use assembler
        return ResponseEntity.ok().body(service.getAllPurchaseOrders());
    }

    @GetMapping(path = "/PurchaseOrders/{id}")
    public ResponseEntity<?> one(@PathVariable int id) {

        Optional<PurchaseOrder> purchaseOrder = service.getPurchaseOrder(id);

        if (purchaseOrder.isPresent()){
            return ResponseEntity.ok().body(purchaseOrder.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/PurchaseOrders")
    public ResponseEntity<?> createPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder){
        //TODO: validate input
        boolean isSuccessful = service.addPurchaseOrder(purchaseOrder);
        if (isSuccessful == true){
            //TODO: debug if id has value
            return ResponseEntity.ok().body(purchaseOrder.getId());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);
        }
    }

}
