package com.soen390.erp.inventory.model;

import com.soen390.erp.manufacturing.model.*;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class SupplierOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Date date;

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