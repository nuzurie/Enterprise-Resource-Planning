package com.soen390.erp.inventory.model;

import com.soen390.erp.manufacturing.model.Material;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PlantMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "material_id")
    private Material material;

    private int quantity;
}
