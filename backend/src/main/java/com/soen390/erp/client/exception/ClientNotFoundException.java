package com.soen390.erp.client.exception;

public class ClientNotFoundException extends RuntimeException{

    public ClientNotFoundException(int id) {
        super("Could not find client " + id);
    }
}