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
public class SupplierOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date date;
    @OneToMany(mappedBy = "supplierOrder")
    private Set<OrderItem> orderItems;

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }










//    private Optional<String> title;
//    @OneToOne
//    @JoinColumn(name = "status_id")
//    public OrderStatus status;
//    //plus shipping ?
//    private double totalCost;
}