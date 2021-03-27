package com.soen390.erp.accounting.controller;

import com.soen390.erp.accounting.model.Account;
import com.soen390.erp.accounting.model.Ledger;
import com.soen390.erp.accounting.model.SaleOrder;
import com.soen390.erp.accounting.service.AccountService;
import com.soen390.erp.accounting.service.LedgerService;
import com.soen390.erp.accounting.service.SaleOrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class SaleOrderController {
    private final SaleOrderService saleOrderService;
    private final AccountService accountService;
    private final LedgerService ledgerService;


    @GetMapping(path = "/SaleOrders")
    public ResponseEntity<?> all(){
        //TODO: use stream and return a mapped collection or use assembler
        return ResponseEntity.ok().body(saleOrderService.getAllSaleOrders());
    }

    @GetMapping(path = "/SaleOrders/{id}")
    public ResponseEntity<?> one(@PathVariable int id) {

        Optional<SaleOrder> saleOrder = saleOrderService.getSaleOrder(id);

        if (saleOrder.isPresent()){
            return ResponseEntity.ok().body(saleOrder.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/SaleOrders")
    public ResponseEntity<?> createSaleOrder(@RequestBody SaleOrder saleOrder){
        //TODO: validate input

        saleOrder.setPaid(false);
        saleOrder.setShipped(false);

        boolean isSuccessful = saleOrderService.addSaleOrder(saleOrder);
        if (isSuccessful == true){
            //TODO: debug if id has value
            return ResponseEntity.ok().body(saleOrder.getId());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);
        }
    }

    @PutMapping(path = "/SaleOrders/{id}/ReceivePayment")
    public ResponseEntity<?> receivePayment(@PathVariable int id){

        //region validation
        //check if sale order exists
        Optional<SaleOrder> saleOrderOptional = saleOrderService.getSaleOrder(id);
        if (saleOrderOptional.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        SaleOrder saleOrder = saleOrderOptional.get();
        //check if transaction valid
        if(saleOrder.isPaid()){
            return ResponseEntity.badRequest().build();
        }
        //endregion










        //get amount from po
        double amount = saleOrder.getGrandTotal();


        //region accounts
        //FIXME fetch bank and inventory accounts using enum and not id.
        int bankAccountId = 12; //wrong assumption
        int accountReceivableAccountId = 10; //wrong assumption

        Account bank = accountService.getAccount(bankAccountId).get();
        Account accountReceivable = accountService.getAccount(accountReceivableAccountId).get();

        bank.setBalance(bank.getBalance() + amount);
        accountReceivable.setBalance(accountReceivable.getBalance() - amount);
        //endregion

        //region sale order
        //update status
        saleOrder.setPaid(true);
        //endregion

        //region ledger
        //TODO insert a ledger entry
        Ledger ledgerEntry = new Ledger();
        ledgerEntry.setDebitAccount(bank);
        ledgerEntry.setCreditAccount(accountReceivable);
        ledgerEntry.setDate(new Date());
        ledgerEntry.setAmount(amount);
        ledgerEntry.setSaleOrder(saleOrder);

        //save
        ledgerService.addLedger(ledgerEntry);
        //endregion

        //region return
        return ResponseEntity.status(HttpStatus.CREATED).build();
        //endregion
    }

    @PutMapping(path = "/SaleOrders/{id}/ShipBike")
    public ResponseEntity<?> shipBike(@PathVariable int id){

        //region validation
        //check if sale order exists
        Optional<SaleOrder> saleOrderOptional = saleOrderService.getSaleOrder(id);
        if (saleOrderOptional.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        SaleOrder saleOrder = saleOrderOptional.get();
        //check if transaction valid
        if(saleOrder.isShipped()){
            return ResponseEntity.badRequest().build();
        }
        //endregion










        //get amount from po
        double amount = saleOrder.getGrandTotal();


        //region accounts
        //FIXME fetch bank and inventory accounts using enum and not id.
        int inventoryId = 13; //wrong assumption
        int accountReceivableAccountId = 10; //wrong assumption

        Account inventory = accountService.getAccount(inventoryId).get();
        Account accountReceivable = accountService.getAccount(accountReceivableAccountId).get();

        inventory.setBalance(inventory.getBalance() - amount);
        accountReceivable.setBalance(accountReceivable.getBalance() + amount);
        //endregion

        //region sale order
        //update status
        saleOrder.setShipped(true);
        //endregion

        //region ledger
        //TODO insert a ledger entry
        Ledger ledgerEntry = new Ledger();
        ledgerEntry.setDebitAccount(accountReceivable);
        ledgerEntry.setCreditAccount(inventory);
        ledgerEntry.setDate(new Date());
        ledgerEntry.setAmount(amount);
        ledgerEntry.setSaleOrder(saleOrder);

        //save
        ledgerService.addLedger(ledgerEntry);
        //endregion

        //region return
        return ResponseEntity.ok().build();
        //endregion
    }
}