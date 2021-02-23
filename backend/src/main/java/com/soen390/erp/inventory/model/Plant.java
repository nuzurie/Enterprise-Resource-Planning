package com.soen390.erp.inventory.model;

import com.soen390.erp.manufacturing.model.Bike;
import com.soen390.erp.manufacturing.model.Material;
import com.soen390.erp.manufacturing.model.Part;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
//@NoArgsConstructor
@Entity
//@Data

public class Plant {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String address;

    @OneToMany(mappedBy = "plant")
    private List<Material> materials;

    @OneToMany(mappedBy = "plant")
    private List<Part> parts;

    @OneToMany(mappedBy = "plant")
    private List<Bike> bikes;
}
