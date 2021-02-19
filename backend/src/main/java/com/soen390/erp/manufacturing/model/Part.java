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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="product_type",
        discriminatorType = DiscriminatorType.STRING)
public abstract class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;
    protected String name;
    protected double cost;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name="parts_materials",
            joinColumns=@JoinColumn(name="part_id"),
            inverseJoinColumns=@JoinColumn(name = "material_id"))
    protected Set<Material> materials;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Set<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(Set<Material> materials) {
        this.materials = materials;
    }

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

    @Override
    public String toString() {
        return "Part{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                ", materials=" + materials +
                '}';
    }
}
