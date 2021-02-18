package com.soen390.erp.inventory.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class OrderStatus {
    @Id
    public int id;
    public String name;
}