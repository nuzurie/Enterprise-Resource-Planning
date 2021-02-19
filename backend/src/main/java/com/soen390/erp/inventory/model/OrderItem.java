package com.soen390.erp.inventory.model;

import com.soen390.erp.manufacturing.model.Material;
import com.soen390.erp.manufacturing.model.Part;
import lombok.*;

import javax.persistence.*;
import java.util.Optional;
import java.util.Set;

//@Getter
//@Setter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
@Entity

public class OrderItem {
    @Id
    private int id;

    @OneToOne
    @JoinColumn(name = "material_id")
    private Material material;
    private int quantity;


    @OneToOne
    @JoinColumn(name = "order_id")
    private SupplierOrder supplier;









//    private Double cost;
//    private Double totalCost;

//    @ManyToMany(mappedBy = "order_material")
//    private Set<SupplierOrder> orders;

//    @ManyToMany(mappedBy = "order_materials")
//    private int orderId;



}
