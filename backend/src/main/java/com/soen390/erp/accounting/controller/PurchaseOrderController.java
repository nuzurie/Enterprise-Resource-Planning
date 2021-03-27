package com.soen390.erp.accounting.controller;

import com.soen390.erp.accounting.model.Account;
import com.soen390.erp.accounting.model.Ledger;
import com.soen390.erp.accounting.model.PurchaseOrder;
import com.soen390.erp.accounting.service.AccountService;
import com.soen390.erp.accounting.service.LedgerService;
import com.soen390.erp.accounting.service.PurchaseOrderService;
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
    private final AccountService accountService;
    private final LedgerService ledgerService;

    @GetMapping(path = "/PurchaseOrders")
    public ResponseEntity<?> all(){
        //TODO: use stream and return a mapped collection or use assembler
        return ResponseEntity.ok().body(purchaseOrderService.getAllPurchaseOrders());
    }

    @GetMapping(path = "/PurchaseOrders/{id}")
    public ResponseEntity<?> one(@PathVariable int id) {

        Optional<PurchaseOrder> purchaseOrder = purchaseOrderService.getPurchaseOrder(id);

        if (purchaseOrder.isPresent()){
            return ResponseEntity.ok().body(purchaseOrder.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/PurchaseOrders")
    public ResponseEntity<?> createPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder){
        //TODO: validate input

        purchaseOrder.setPaid(false);
        purchaseOrder.setReceived(false);

        boolean isSuccessful = purchaseOrderService.addPurchaseOrder(purchaseOrder);
        if (isSuccessful == true){
            //TODO: debug if id has value
            return ResponseEntity.ok().body(purchaseOrder.getId());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);
        }
    }

    @PutMapping(path = "/PurchaseOrders/{id}/MakePayment")
    public ResponseEntity<?> makePayment(@PathVariable int id){

        //region validation
        //check if purchase order exists
        Optional<PurchaseOrder> purchaseOrderOptional = purchaseOrderService.getPurchaseOrder(id);
        if (purchaseOrderOptional.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        PurchaseOrder purchaseOrder = purchaseOrderOptional.get();
        //check if transaction valid
        if(purchaseOrder.isPaid()){
            return ResponseEntity.badRequest().build();
        }
        //TODO check if bank balance is more than grand total
        //endregion

        //get amount from po
        double amount = purchaseOrder.getGrandTotal();


        //region accounts
        //FIXME fetch bank and inventory accounts using enum and not id.
        int bankAccountId = 12; //wrong assumption
        int accountPayableAccountId = 9; //wrong assumption

        //FIXME Account gets infinite reference with Ledger
        Account bank = accountService.getAccount(bankAccountId).get();
        Account accountPayable = accountService.getAccount(accountPayableAccountId).get();

        bank.setBalance(bank.getBalance() - amount);
        accountPayable.setBalance(accountPayable.getBalance() - amount);
        //endregion

        //region purchase order
        //update status
        purchaseOrder.setPaid(true);
        //endregion

        //region ledger
        //TODO insert a ledger entry
        Ledger ledgerEntry = new Ledger();
        ledgerEntry.setDebitAccount(accountPayable);
        ledgerEntry.setCreditAccount(bank);
        ledgerEntry.setDate(new Date());
        ledgerEntry.setAmount(amount);
        ledgerEntry.setPurchaseOrder(purchaseOrder);

        //save
        ledgerService.addLedger(ledgerEntry);
        //endregion

        //region return
        return ResponseEntity.ok().build();
        //endregion
    }
    @PutMapping(path = "/PurchaseOrders/{id}/ReceiveMaterial")
    public ResponseEntity<?> receiveMaterial(@PathVariable int id){
        //region validation
        //check if purchase order exists
        Optional<PurchaseOrder> purchaseOrderOptional = purchaseOrderService.getPurchaseOrder(id);
        if (purchaseOrderOptional.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        PurchaseOrder purchaseOrder = purchaseOrderOptional.get();
        //check if transaction valid
        if(purchaseOrder.isReceived()){
            return ResponseEntity.badRequest().build();
        }
        //TODO check if inventory balance is more than grand total
        //endregion

        //get amount from po
        double amount = purchaseOrder.getGrandTotal();


        //region accounts
        //FIXME fetch inventory and inventory accounts using enum and not id.
        int inventoryAccountId = 13; //wrong assumption
        int accountPayableAccountId = 9; //wrong assumption

        //FIXME Account gets infinite reference with Ledger
        Account inventory = accountService.getAccount(inventoryAccountId).get();
        Account accountPayable = accountService.getAccount(accountPayableAccountId).get();

        inventory.setBalance(inventory.getBalance() + amount);
        accountPayable.setBalance(accountPayable.getBalance() + amount);
        //endregion

        //region purchase order
        //update status
        purchaseOrder.setReceived(true);
        //endregion

        //region ledger
        //TODO insert a ledger entry
        Ledger ledgerEntry = new Ledger();
        ledgerEntry.setDebitAccount(inventory);
        ledgerEntry.setCreditAccount(accountPayable);
        ledgerEntry.setDate(new Date());
        ledgerEntry.setAmount(amount);
        ledgerEntry.setPurchaseOrder(purchaseOrder);

        //save
        ledgerService.addLedger(ledgerEntry);
        //endregion

        //region return
        return ResponseEntity.status(HttpStatus.CREATED).build();
        //endregion
    }
}