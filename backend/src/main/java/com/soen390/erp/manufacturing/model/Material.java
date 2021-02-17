package com.soen390.erp.manufacturing.model;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;
    private String name;
    private double cost;
    @ManyToMany(mappedBy = "materials")
    private Set<Part> parts;

    public Optional<Set<Part>> getParts() {
        return Optional.ofNullable(parts);
    }
}
