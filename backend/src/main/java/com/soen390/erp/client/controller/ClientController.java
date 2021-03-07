package com.soen390.erp.client.controller;

import com.soen390.erp.client.exception.ClientNotFoundException;
import com.soen390.erp.client.exception.InvalidClientException;
import com.soen390.erp.client.model.Client;
import com.soen390.erp.client.service.ClientService;
import com.soen390.erp.inventory.exceptions.InvalidClientOrderException;
import com.soen390.erp.inventory.model.ClientOrder;
import com.soen390.erp.inventory.service.ClientOrderService;
import com.soen390.erp.manufacturing.exceptions.BikeNotFoundException;
import com.soen390.erp.manufacturing.model.Bike;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    List<Client> getAllClients() {
        return clientService.findAllClients();
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<?> findClientById(@PathVariable int id) {

        Client c;
        try {
            c = clientService.findClientById(id);

        } catch (ClientNotFoundException e) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        return ResponseEntity.ok().body(c);
    }

    @PostMapping
    public ResponseEntity<?> addClient(@RequestBody Client client) {
        try {
            clientService.saveClient(client);

        } catch (InvalidClientException e) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Created Client");
    }

}
