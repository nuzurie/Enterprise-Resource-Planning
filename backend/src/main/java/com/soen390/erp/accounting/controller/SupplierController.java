package com.soen390.erp.accounting.controller;

import com.soen390.erp.accounting.exceptions.SupplierNotFoundException;
import com.soen390.erp.accounting.exceptions.InvalidSupplierException;
import com.soen390.erp.accounting.model.Supplier;
import com.soen390.erp.accounting.service.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public List<Supplier> getAllSuppliers() {

        return supplierService.findAllSuppliers();
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<?> findSupplierById(@PathVariable int id) {

        Supplier c;
        try {
            c = supplierService.findSupplierById(id);

        } catch (SupplierNotFoundException e) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        return ResponseEntity.ok().body(c);
    }

    @PostMapping
    public ResponseEntity<?> addSupplier(@RequestBody Supplier supplier) {
        try {
            supplierService.saveSupplier(supplier);

        } catch (InvalidSupplierException e) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Created Supplier");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteSupplierById(@PathVariable int id) {

        boolean isRemoved = supplierService.deleteSupplierById(id);

        if (!isRemoved) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supplier Not Found by ID " + id);
        }

        return ResponseEntity.status(HttpStatus.OK).body("Supplier Deleted");
    }

}
