package com.soen390.erp.client.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Client {

    // client id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    // client name ?
}
