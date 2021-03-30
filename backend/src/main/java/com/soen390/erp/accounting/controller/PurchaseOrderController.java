package com.soen390.erp.accounting.controller;

import com.soen390.erp.accounting.exceptions.PurchaseNotFoundException;
import com.soen390.erp.accounting.model.Account;
import com.soen390.erp.accounting.model.Ledger;
import com.soen390.erp.accounting.model.PurchaseOrder;
import com.soen390.erp.accounting.service.AccountService;
import com.soen390.erp.accounting.service.LedgerService;
import com.soen390.erp.accounting.service.PurchaseOrderService;
import com.soen390.erp.configuration.ResponseEntityWrapper;
import com.soen390.erp.configuration.service.LogService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class PurchaseOrderController {
    private final PurchaseOrderService purchaseOrderService;
    private final LogService logService;

    @GetMapping(path = "/PurchaseOrders")
    public ResponseEntity<?> all(){

        logService.addLog("Retrieved all purchase orders.");
        //TODO: use stream and return a mapped collection or use assembler
        return ResponseEntity.ok().body(purchaseOrderService.getAllPurchaseOrders());
    }

    @GetMapping(path = "/PurchaseOrders/{id}")
    public ResponseEntity<?> one(@PathVariable int id) {

        PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrder(id)
                .orElseThrow(() -> new PurchaseNotFoundException("No order with id " + id));

        logService.addLog("Retrieved purchase order with id "+id+".");
        return ResponseEntity.ok().body(purchaseOrder);

    }

    @PostMapping(path = "/PurchaseOrders")
    public ResponseEntityWrapper createPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder){
        //TODO: validate input

        purchaseOrder.setPaid(false);
        purchaseOrder.setReceived(false);

        boolean isSuccessful = purchaseOrderService.addPurchaseOrder(purchaseOrder);
        if (isSuccessful == true){
            logService.addLog("Created purchase order.");
            //TODO: debug if id has value
            return new ResponseEntityWrapper(ResponseEntity.ok().body(purchaseOrder.getId()), "The Purchase Order has been created with id " + purchaseOrder.getId());
        }else{
            return new ResponseEntityWrapper(ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null), "Purchase order could not be created.");
        }
    }

    @PutMapping(path = "/PurchaseOrders/{id}/MakePayment")
    public ResponseEntityWrapper makePayment(@PathVariable int id){

        //region validation
        //check if purchase order exists
        Optional<PurchaseOrder> purchaseOrderOptional = purchaseOrderService.getPurchaseOrder(id);
        if (purchaseOrderOptional.isEmpty()){
            logService.addLog("Failed to make payment for purchase order with id "+id+". No such purchase order exists.");
            return new ResponseEntityWrapper(ResponseEntity.badRequest().build(), "Could not find Purchase Order with id: " + id + ".");
        }
        PurchaseOrder purchaseOrder = purchaseOrderOptional.get();
        //check if transaction valid
        if(purchaseOrder.isPaid()){
            logService.addLog("Failed to make payment for purchase order with id "+id+". Already paid for.");
            return new ResponseEntityWrapper(ResponseEntity.badRequest().build(), "Purchase Order has already been paid.");
        }
        //TODO check if bank balance is more than grand total
        //endregion

        purchaseOrderService.makePaymentTransactions(purchaseOrder);
        logService.addLog("Paid for purchase order with id "+id+".");
        //region return
        return new ResponseEntityWrapper(ResponseEntity.ok().build(), "Purchase Order with id " + id + " has been paid.");
        //endregion
    }
    @PutMapping(path = "/PurchaseOrders/{id}/ReceiveMaterial")
    public ResponseEntityWrapper receiveMaterial(@PathVariable int id){
        //region validation
        //check if purchase order exists
        Optional<PurchaseOrder> purchaseOrderOptional = purchaseOrderService.getPurchaseOrder(id);
        if (purchaseOrderOptional.isEmpty()){
            logService.addLog("Failed to receive materials for purchase order with id "+id+". No such purchase order exists.");
            return new ResponseEntityWrapper(ResponseEntity.badRequest().build(), "Could not find Purchase Order with id: " + id + ".");
        }
        PurchaseOrder purchaseOrder = purchaseOrderOptional.get();
        //check if transaction valid
        if(purchaseOrder.isReceived()){

            logService.addLog("Failed to receive material for purchase order with id "+id+". Already received.");
            return new ResponseEntityWrapper(ResponseEntity.badRequest().build(), "Purchase Order has already been received.");
        }
        //TODO check if inventory balance is more than grand total
        //endregion

        purchaseOrderService.receiveMaterialTransactions(purchaseOrder);
        logService.addLog("Successfully received material for purchase order with id "+id+".");
        //region return

        return new ResponseEntityWrapper(ResponseEntity.status(HttpStatus.CREATED).build(), "Receiving materials from Purchase Order with id" + id + ".");
        //endregion
    }
}