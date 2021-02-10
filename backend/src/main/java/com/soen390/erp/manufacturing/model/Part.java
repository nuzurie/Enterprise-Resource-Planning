package com.soen390.erp.manufacturing.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Data
@Getter
@Setter
public class Part {
    @Id
    private String name;
    private double cost;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name="parts_materials",
            joinColumns=@JoinColumn(name="part_name"),
            inverseJoinColumns=@JoinColumn(name = "material_name"))
    private Set<Material> materials;

    public void addMaterial(Material material){
        if (materials==null)
            materials = new HashSet<>();
        materials.add(material);
        //it's possible that the set hasn't been initialized
        Optional<Set<Part>> partsNullable = material.getParts();
        //get a new hashset if doesn't already exist
        Set<Part> parts = partsNullable.orElseGet(()-> new HashSet<Part>());
        //add the current part to the materials part set
        parts.add(this);
        material.setParts(parts);
    }
}
