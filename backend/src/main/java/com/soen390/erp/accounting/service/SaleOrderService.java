package com.soen390.erp.accounting.service;

import com.soen390.erp.accounting.model.Account;
import com.soen390.erp.accounting.model.Ledger;
import com.soen390.erp.accounting.model.SaleOrder;
import com.soen390.erp.accounting.repository.SaleOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SaleOrderService {
    private final SaleOrderRepository repository;
    private final AccountService accountService;
    private final LedgerService ledgerService;

    public boolean addSaleOrder(SaleOrder saleOrder){
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

    public void receivePaymentTransactions( Optional<SaleOrder> saleOrderOptional)
    {
        SaleOrder saleOrder = saleOrderOptional.get();
        //TODO check if transaction valid
        //TODO check if bank balance is more than grand total
        //TODO check if new status is valid
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
    }

    public void shipBikeTransactions(Optional<SaleOrder> saleOrderOptional) {

        SaleOrder saleOrder = saleOrderOptional.get();
        //TODO check if transaction valid
        //TODO check if bank balance is more than grand total
        //TODO check if new status is valid
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

    }
}
