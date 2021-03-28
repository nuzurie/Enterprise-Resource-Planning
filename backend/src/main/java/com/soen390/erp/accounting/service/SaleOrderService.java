package com.soen390.erp.accounting.service;

import com.soen390.erp.accounting.model.Account;
import com.soen390.erp.accounting.model.Ledger;
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
import java.util.Optional;

@AllArgsConstructor
@Service
public class SaleOrderService {
    private final SaleOrderRepository repository;
    private final PlantRepository plantRepository;
    private final BikeRepository bikeRepository;
    private final AccountService accountService;
    private final LedgerService ledgerService;

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

    public void receivePaymentTransactions( SaleOrder saleOrder)
    {
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
    }

    public void shipBikeTransactions(SaleOrder saleOrder) {











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

    }
}
