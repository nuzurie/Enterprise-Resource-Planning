package com.soen390.erp.accounting.controller;

import com.soen390.erp.accounting.model.PurchaseOrder;
import com.soen390.erp.accounting.service.PurchaseOrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @PutMapping(path = "/PurchaseOrders/{id}/makePayment")
    public ResponseEntity<?> makePayment(@PathVariable int id){

        //region validation
        //check if purchase order exists
        Optional<PurchaseOrder> purchaseOrder = service.getPurchaseOrder(id);
        if (purchaseOrder.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        //TODO check if transaction valid
        //TODO check if bank balance is more than grand total
        //TODO check if new status is valid
        //endregion

        //region accounts
        //Bank and inventory
        //TODO deduct from bank
        //TODO add to inventory
        //endregion

        //region ledger
        //TODO insert a ledger entry
        //endregion

        //region purchase order
        //update status
        purchaseOrder.get().setPaid(true);
        //endregion

        //region return
        return ResponseEntity.ok().build();
        //endregion
    }
}