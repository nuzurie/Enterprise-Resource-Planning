package com.soen390.erp.inventory.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Material2 {
    @Id
    private int id;
    private String name;
    private double cost;
}
