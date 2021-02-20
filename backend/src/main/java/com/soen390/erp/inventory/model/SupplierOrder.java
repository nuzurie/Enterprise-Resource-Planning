package com.soen390.erp.inventory.model;

import com.soen390.erp.manufacturing.model.*;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Supplierorder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Date date;

    @OneToMany(mappedBy = "supplierorder")
    private Set<OrderItem> orderItems;

//    @ManyToMany(cascade = CascadeType.MERGE)
//    @JoinTable(name="order_item",
//            joinColumns=@JoinColumn(name="part_id"))
//    protected Set<Material> materials;









//    private Optional<String> title;

//    protected ArrayList<OrderItem> orderItems;
//
//    @OneToOne
//    @JoinColumn(name = "status_id")
//    public OrderStatus status;
//    //plus shipping ?
//    private double totalCost;
}