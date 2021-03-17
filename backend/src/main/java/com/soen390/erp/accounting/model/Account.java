package com.soen390.erp.accounting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.soen390.erp.manufacturing.model.Frame;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {
    enum AccountName {AccountPayable, AccountReceivable, Cash, Sale, Inventory, Material}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private AccountName name;
    private double balance;

    @OneToMany(mappedBy = "debitAccount")
    @JsonBackReference
    private Set<Ledger> debitLedgerEntries;

    @OneToMany(mappedBy = "creditAccount")
    @JsonBackReference
    private Set<Ledger> creditLedgerEntries;
}
