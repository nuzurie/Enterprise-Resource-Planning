package com.soen390.erp.accounting.model;

import java.util.Date;


//TODO: should be merged with ClientOrder that Carlin did

public class SaleOrder {
    private int id;
    private Date date;
    private int plant_id;
    private int customer_id;
    private double discount;
    private double tax;
    private double total;
}
