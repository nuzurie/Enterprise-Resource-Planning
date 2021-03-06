package com.soen390.erp.accounting.model;

import java.util.Date;

/**
 * this gets created and one record gets inserted everytime we order material from a supplier
 */
public class PurchaseOrder {
    private int id;
    private Date date;
    private int plant_id;
    /**
     * supplier id
     */
    private int vendor_id;
    /**
     * before discount and tax
     */
    private double totalAmount;
    /**
     * in percentage e.g. for 10% discount record 10
     */
    private double discount;
    /**
     * = totalAmount * discount
     */
    private double discountAmount;
    /**
     * in percentage e.g. for 15% tax record 15
     */
    private double tax;
    /**
     * = totalAmount * tax
     */
    private double taxAmount;
    /**
     * after discount and tax = totalAmount - discountAmount + taxAmount
     */
    private double grandTotal;
}
