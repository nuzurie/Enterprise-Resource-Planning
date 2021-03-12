package com.soen390.erp.accounting.controller;


import com.soen390.erp.accounting.exceptions.LedgerNotFoundException;
import com.soen390.erp.accounting.model.Ledger;
import com.soen390.erp.accounting.repository.LedgeRepository;
import com.soen390.erp.accounting.service.LedgerModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class LedgerController {

    private final LedgeRepository ledgerRepository;
    private final LedgerModelAssembler assembler;

    public LedgerController(LedgeRepository ledgerRepository, LedgerModelAssembler assembler) {
        this.ledgerRepository=ledgerRepository;
        this.assembler=assembler;
    }

    @GetMapping("/ledger")
    public ResponseEntity<?> all() {

        List<EntityModel<Ledger>> ledger = ledgerRepository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(
                CollectionModel.of(ledger, linkTo(methodOn(LedgerController.class).all()).withSelfRel()));
    }

    @GetMapping(path = "/ledger/{id}")
    public ResponseEntity<?> one(@PathVariable int id) {

        Ledger ledger = ledgerRepository.findById(id)
                .orElseThrow(() -> new LedgerNotFoundException(id));

        return ResponseEntity.ok().body(assembler.toModel(ledger));
    }

    @PostMapping("/ledger")
    ResponseEntity<?> newTransaction(@RequestBody Ledger ledger){

        // Todo add account transaction / add inventory updates
        EntityModel<Ledger> entityModel = assembler.toModel(ledgerRepository.save(ledger));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @ResponseBody
    @ExceptionHandler(LedgerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String partNotFoundException(LedgerNotFoundException ex){
        return ex.getMessage();
    }
}
