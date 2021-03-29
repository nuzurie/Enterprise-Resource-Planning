package com.soen390.erp.manufacturing.controller;

import com.soen390.erp.email.model.EmailToSend;
import com.soen390.erp.email.service.EmailService;
import com.soen390.erp.manufacturing.exceptions.PartNotFoundException;
import com.soen390.erp.manufacturing.model.Material;
import com.soen390.erp.manufacturing.model.Part;
import com.soen390.erp.manufacturing.repository.MaterialRepository;
import com.soen390.erp.manufacturing.repository.PartRepository;
import com.soen390.erp.manufacturing.service.PartModelAssembler;
import com.soen390.erp.configuration.ResponseEntityWrapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class PartController {

    private final PartRepository partRepository;
    private final MaterialRepository materialRepository;
    private final PartModelAssembler assembler;
    private final EmailService emailService;


    public PartController(PartRepository partRepository,
                          PartModelAssembler assembler,
                          MaterialRepository materialRepository,
                          EmailService emailService)
    {
        this.partRepository = partRepository;
        this.assembler = assembler;
        this.materialRepository = materialRepository;
        this.emailService = emailService;
    }

    @GetMapping("/parts")
    public ResponseEntity<?> all() {

        List<EntityModel<Part>> parts = assembler.assembleToModel();

        return ResponseEntity.ok().body(
                CollectionModel.of(parts, linkTo(methodOn(PartController.class)
                        .all()).withSelfRel()));
    }

    @GetMapping(path = "/parts/{id}")
    public ResponseEntity<?> one(@PathVariable int id) {

        Part part = partRepository.findById(id)
                .orElseThrow(() -> new PartNotFoundException(id));

        return ResponseEntity.ok().body(assembler.toModel(part));
    }

    @PostMapping("/parts")
    public ResponseEntityWrapper newPart(@RequestBody Part part){

        Set<Material> materials = part.getMaterials()
                .orElseGet(() -> new HashSet<>());
        materials
                .forEach(materialRepository::save);
        materials
                .forEach(part::addMaterial);
        EntityModel<Part> entityModel = assembler.toModel(partRepository
                .save(part));

        EmailToSend email = EmailToSend.builder().to("part.manager@msn.com").subject("Added Part").body("A new part has been added with id " + part.getId()).build();
        emailService.sendMail(email);
        return new ResponseEntityWrapper(ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel)
                , "The material was successfully created with id " + part.getId());

    }

    @ResponseBody
    @ExceptionHandler(PartNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String partNotFoundException(PartNotFoundException ex){
        return ex.getMessage();
    }

}
