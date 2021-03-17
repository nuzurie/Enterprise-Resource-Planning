package com.soen390.erp.accounting.service;

import com.soen390.erp.accounting.model.Ledger;
import com.soen390.erp.accounting.repository.LedgerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class LedgerService {
    private final LedgerRepository ledgerRepository;

    public void addLedger(Ledger ledgerEntry) {
        ledgerRepository.save(ledgerEntry);

    }
}
