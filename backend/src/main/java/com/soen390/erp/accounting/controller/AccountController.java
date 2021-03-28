package com.soen390.erp.accounting.controller;

import com.soen390.erp.accounting.exceptions.AccountNotFoundException;
import com.soen390.erp.accounting.model.Account;
import com.soen390.erp.accounting.repository.AccountRepository;
import com.soen390.erp.accounting.service.AccountModelAssembler;
import com.soen390.erp.accounting.service.AccountService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class AccountController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final AccountModelAssembler assembler;

    public AccountController(AccountRepository accountRepository,
                             AccountModelAssembler assembler,
                             AccountService accountService)
    {
        this.accountRepository = accountRepository;
        this.assembler = assembler;
        this.accountService = accountService;
    }

    @GetMapping("/account")
    public ResponseEntity<?> all() {

        List<EntityModel<Account>> account = accountService.assembleToModel();

        return ResponseEntity.ok().body(
                CollectionModel.of(account, linkTo(methodOn(AccountController.class).all()).withSelfRel()));
    }

    @GetMapping(path = "/account/{id}")
    public ResponseEntity<?> one(@PathVariable int id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));

        return ResponseEntity.ok().body(assembler.toModel(account));
    }

    @PostMapping("/account")
    public ResponseEntity<?> newTransaction(@RequestBody Account account){


        EntityModel<Account> entityModel = assembler.toModel(accountRepository.save(account));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @ResponseBody
    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String partNotFoundException(AccountNotFoundException ex){
        return ex.getMessage();
    }


}
