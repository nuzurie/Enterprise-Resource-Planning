package com.soen390.erp.manufacturing.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import java.util.Optional;
import java.util.Set;

//@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Material {
    @Id
    private String name;
    private double cost;
    @ManyToMany(mappedBy = "materials")
    private Set<Part> parts;

    public Optional<Set<Part>> getParts() {
        return Optional.ofNullable(parts);
    }
}
