package com.soen390.erp.accounting.model;

import com.soen390.erp.manufacturing.model.Frame;

public class Account {
    enum Name {AccountPayable, AccountReceivable, Cash, Sale, Inventory, Material}

    private int id;
    private Name name;
    private double balance;

}
