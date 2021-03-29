package com.soen390.erp.accounting.service;

import com.soen390.erp.accounting.model.Account;
import com.soen390.erp.accounting.model.IReport;
import com.soen390.erp.accounting.model.Ledger;
import com.soen390.erp.accounting.report.IReportGenerator;
import com.soen390.erp.accounting.repository.AccountRepository;
import com.soen390.erp.accounting.repository.LedgerRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class LedgerService implements IReport {
    private final LedgerRepository ledgerRepository;
    private final LedgerModelAssembler assembler;

    public void addLedger(Ledger ledgerEntry) {
        ledgerRepository.save(ledgerEntry);
    }

    public List<Ledger> findAllLedgers() {

        return ledgerRepository.findAll();
    }

    @Override
    public void accept(IReportGenerator reportGenerator) throws IOException
    {
        reportGenerator.generateLedgerReport(this);
    }

    public List<EntityModel<Ledger>> assembleToModel()
    {
        return ledgerRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }
}