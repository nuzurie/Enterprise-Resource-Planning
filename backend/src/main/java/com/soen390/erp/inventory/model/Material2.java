package com.soen390.erp.inventory.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Material2 {
    @Id
    private int id;
    private int type;
    public double cost;
}
