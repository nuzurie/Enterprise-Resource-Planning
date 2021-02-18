package com.soen390.erp.inventory.model;

import java.util.Optional;

public class OrderMaterial {
    private int materialId;
    private Material2 material;
    private int quantity;
    private Optional<Double> cost;


    public OrderMaterial(Material2 material) {
        cost = Optional.of(material.cost * quantity);
    }
}
