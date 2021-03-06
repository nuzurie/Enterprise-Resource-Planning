package com.soen390.erp.accounting.model;

import com.soen390.erp.manufacturing.model.Material;

/**
 * this get created for each item in the
 * @see com.soen390.erp.accounting.model.PurchaseOrder
 *
 */
public class PrchaseOrderItems {
    private int id;
    private int purchaseOrderId;
    private Material itemId;
    private int quantity;
    private double unitPrice;
}
