package com.soen390.erp.accounting.controller;

import com.soen390.erp.accounting.exceptions.ClientNotFoundException;
import com.soen390.erp.accounting.exceptions.InvalidClientException;
import com.soen390.erp.accounting.model.Client;
import com.soen390.erp.accounting.service.ClientService;
import com.soen390.erp.email.model.EmailToSend;
import com.soen390.erp.email.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;
    private final EmailService emailService;
    public ClientController(ClientService clientService, EmailService emailService) {
        this.clientService = clientService;
        this.emailService = emailService;
    }

    @GetMapping
    public List<Client> getAllClients() {
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
        EmailToSend email = EmailToSend.builder().to("sales.manager@msn.com").subject("New Customer").body("A new client has been created with id " + client.getId()).build();
        emailService.sendMail(email);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created Client with id: " + client.getId());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteClientById(@PathVariable int id) {

        boolean isRemoved = clientService.deleteClientById(id);

        if (!isRemoved) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client Not Found by ID " + id);
        }

        return ResponseEntity.status(HttpStatus.OK).body("Client with id: " + id +" has been deleted");
    }

}
