package com.soen390.erp.manufacturing.model;

import com.soen390.erp.inventory.model.OrderItem;
import lombok.*;

import javax.persistence.*;

import java.util.Optional;
import java.util.Set;

//@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    private String name;
    private double cost;
    @ManyToMany(mappedBy = "materials")
    private Set<Part> parts;

    @OneToMany(mappedBy = "material")
    private Set<OrderItem> orderItems;

    public Optional<Set<Part>> getParts() {
        return Optional.ofNullable(parts);
    }

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

    public void setParts(Set<Part> parts) {
        this.parts = parts;
    }

    @Override
    public String toString() {
        return "Material{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                ", parts=" + parts +
                '}';
    }
}
