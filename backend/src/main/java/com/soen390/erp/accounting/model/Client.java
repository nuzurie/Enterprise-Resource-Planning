package com.soen390.erp.accounting.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
public class Client  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
}
