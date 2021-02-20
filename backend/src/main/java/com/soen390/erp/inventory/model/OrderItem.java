package com.soen390.erp.inventory.model;

import com.soen390.erp.manufacturing.model.Material;

import javax.persistence.*;

//@Getter
//@Setter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
@Entity
@IdClass(OrderItemId.class)
public class OrderItem {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "material_id")
    Material material;

    @ManyToOne
    @JoinColumn(name = "supplierorder_id")
    Supplierorder supplierorder;

    private int quantity;

//    @OneToOne
//    @JoinColumn(name = "material_id")
//    private Material material;
//    @OneToOne
//    @JoinColumn(name = "order_id")
//    private Supplierorder supplier;









//    private Double cost;
//    private Double totalCost;

//    @ManyToMany(mappedBy = "order_material")
//    private Set<Supplierorder> orders;

//    @ManyToMany(mappedBy = "order_materials")
//    private int orderId;



}
