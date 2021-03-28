package com.soen390.erp.inventory.controller;

import com.soen390.erp.inventory.exceptions.InvalidClientOrderException;
import com.soen390.erp.inventory.model.ClientOrder;
import com.soen390.erp.inventory.service.ClientOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client-orders")
public class ClientOrderController {

    private final ClientOrderService clientOrderService;

    public ClientOrderController(ClientOrderService clientOrderService) {
        this.clientOrderService = clientOrderService;
    }

    @GetMapping
    public List<ClientOrder> getAllClientOrders() {
        return clientOrderService.findAllClientOrders();
    }

    @PostMapping
    public ResponseEntity<?> addClientOrder(@RequestBody ClientOrder clientOrder) {
        try {
            clientOrderService.addClientOrder(clientOrder);

        } catch (InvalidClientOrderException e) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Created Client Order");
    }
}
