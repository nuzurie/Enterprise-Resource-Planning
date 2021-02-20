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
    @JoinColumn(name = "supplier_order_id")
    SupplierOrder supplierOrder;

    private int quantity;

//    @OneToOne
//    @JoinColumn(name = "material_id")
//    private Material material;
//    @OneToOne
//    @JoinColumn(name = "order_id")
//    private SupplierOrder supplier;









//    private Double cost;
//    private Double totalCost;

//    @ManyToMany(mappedBy = "order_material")
//    private Set<SupplierOrder> orders;

//    @ManyToMany(mappedBy = "order_materials")
//    private int orderId;



}
