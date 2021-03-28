package com.soen390.erp.accounting.service;

import com.soen390.erp.accounting.model.Account;
import com.soen390.erp.accounting.model.Ledger;
import com.soen390.erp.accounting.model.PurchaseOrder;
import com.soen390.erp.accounting.model.PurchaseOrderItems;

import com.soen390.erp.accounting.repository.PurchaseOrderRepository;
import com.soen390.erp.inventory.model.Plant;
import com.soen390.erp.inventory.repository.PlantRepository;
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
    private final PlantRepository plantRepository;

    private final AccountService accountService;
    private final LedgerService ledgerService;

    public boolean addPurchaseOrder(PurchaseOrder purchaseOrder){
        purchaseOrder.setDate(new Date());
        Plant plant = plantRepository.findAll().get(0);
        purchaseOrder.setPlant(plant);

        double totalPrice = 0;
        for (PurchaseOrderItems purchaseOrderItem: purchaseOrder.getPurchaseOrderItems()
             ) {
            totalPrice += purchaseOrderItem.getUnitPrice()*purchaseOrderItem.getQuantity();
            materialRepository.save(purchaseOrderItem.getMaterial());
        }
        purchaseOrder.setTotalAmount(totalPrice);

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

    public void makePaymentTransactions(PurchaseOrder purchaseOrder)
    {
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
    }

    public void receiveMaterialTransactions(PurchaseOrder purchaseOrder)
    {
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
    }
}
