package com.soen390.erp.inventory.model;

import lombok.*;

import javax.persistence.*;

//@Getter
//@Setter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity

public class MaterialType {
//    @Id
    private int id;
    private String name;
    private double price;
    private double cost;
}