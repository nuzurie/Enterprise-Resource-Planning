package com.soen390.erp.configuration.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String message;
    String user;

    public Log(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        this.user = authentication.getName();
    }

    public Log(String message){
        this();
        this.message = message;
    }
}
