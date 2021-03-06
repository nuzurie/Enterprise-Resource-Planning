package com.soen390.erp.accounting.model;

import java.util.Date;

public class SaleOrder {
    private int id;
    private Date date;
    private int plant_id;
    private int customer_id;
    private double discount;
    private double tax;
    private double total;
}
