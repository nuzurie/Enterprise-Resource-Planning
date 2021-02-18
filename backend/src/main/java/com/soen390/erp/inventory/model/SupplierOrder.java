package com.soen390.erp.inventory.model;

import com.soen390.erp.manufacturing.model.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

public class SupplierOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Optional<String> title;
    private ArrayList<OrderMaterial> items;
    private int statusId;
    public OrderStatus status;
    //plus shipping ?
    private double totalCost;
}