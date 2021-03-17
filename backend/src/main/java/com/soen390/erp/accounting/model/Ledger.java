package com.soen390.erp.accounting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * in the ledger you record accounting entries
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ledger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date date;
    private double amount;
    /**
     * the account where we lost something from (e.g. we lost money because we paid for material)
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "debit_account_id")
    private Account debitAccount;

    /**
     * the account where we gained something in (e.g. we gained material because we received it in the inventory)
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "credit_account_id")
    private Account creditAccount;
    /**
     * the purchase order that is linked to this ledger entry
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "purchase_order_id")
    @JsonBackReference
    private PurchaseOrder purchaseOrder;
    /**
     * the client order that is linked to this ledger entry
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "sale_order_id")
    private SaleOrder saleOrder;
}
