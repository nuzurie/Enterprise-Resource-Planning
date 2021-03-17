package com.soen390.erp.accounting.model;

import com.soen390.erp.inventory.model.Plant;
import com.soen390.erp.inventory.model.PlantBike;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SaleOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date date;

    @ManyToOne (optional = false)
    @JoinColumn(name = "plant_id")
    private Plant plant;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;


    /**
     * before discount and tax
     */
    private double totalAmount;
    /**
     * in percentage e.g. for 10% discount record 10
     */
    private double discount;
    /**
     * = totalAmount * discount
     */
    private double discountAmount;
    /**
     * in percentage e.g. for 15% tax record 15
     */
    private double tax;
    /**
     * = totalAmount * tax
     */
    private double taxAmount;
    /**
     * after discount and tax = totalAmount - discountAmount + taxAmount
     */
    private double grandTotal;

    private boolean paid;
    private boolean shipped;
    private int quantity;

    @OneToMany(mappedBy = "saleOrder")
    private Set<SaleOrderItems> saleOrderItems;

}
