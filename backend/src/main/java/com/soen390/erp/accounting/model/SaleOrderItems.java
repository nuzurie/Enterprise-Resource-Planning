package com.soen390.erp.accounting.model;

import com.soen390.erp.manufacturing.model.Bike;
import com.soen390.erp.manufacturing.model.Material;

// TODO: to be deleted if we are ordering one type of bike per Client Order(e.g. the customer wants 2 pink bikes and 1 blue bike => 2 Client Orders)
// @see con.soen390.erp.inventory.model.ClientOrder

public class SaleOrderItems {
    private int id;

    private int saleOrderId;
    private Bike itemId;
    private int quantity;
    private double unitPrice;
}
