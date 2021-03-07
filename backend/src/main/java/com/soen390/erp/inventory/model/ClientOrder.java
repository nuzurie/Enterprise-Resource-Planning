package com.soen390.erp.inventory.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ClientOrder {

    // order id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // client id
    @OneToOne (optional = false)
    @JoinColumn(name = "client_id")
    private int clientId;

    // cost
    private double cost;

    // quantity
    private int quantity;

    // bike id
    @OneToOne (optional = false)
    @JoinColumn(name = "plantBike_id")
    private PlantBike plantBike;
}
