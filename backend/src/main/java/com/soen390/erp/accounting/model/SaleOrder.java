package com.soen390.erp.accounting.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;


//TODO: should be merged with ClientOrder that Carlin did

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
    private int plant_id;
    private int customer_id;
    private double discount;
    private double tax;
    private double total;
}
