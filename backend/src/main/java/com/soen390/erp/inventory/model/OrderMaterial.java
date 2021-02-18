package com.soen390.erp.inventory.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Optional;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class OrderMaterial {
    @Id
    private int materialId;
    private Material2 material;
    private int quantity;
    private Optional<Double> cost;


    public OrderMaterial(Material2 material) {
        cost = Optional.of(material.cost * quantity);
    }
}
