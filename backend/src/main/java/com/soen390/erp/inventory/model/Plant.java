package com.soen390.erp.inventory.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String address;

    @OneToMany( fetch = FetchType.EAGER)
    @JoinColumn(name = "plantmaterial_id")
    private Set<PlantMaterial> materials;

    @OneToMany( fetch = FetchType.EAGER)
    @JoinColumn(name = "plantpart_id")
    private Set<PlantPart> parts;

    @OneToMany(mappedBy = "plant")
    private List<SupplierOrder> supplierOrders;

    public Optional<Set<PlantPart>> getParts() {
        return Optional.ofNullable(parts);
    }

    public Optional<Set<PlantMaterial>> getMaterials() {
        return Optional.ofNullable(materials);
    }

    public void addMaterial(PlantMaterial plantMaterial){
        if (materials==null)
            materials = new HashSet<>();
        materials.add(plantMaterial);
    }

}
