package com.soen390.erp.manufacturing.controller;

import com.soen390.erp.email.model.EmailToSend;
import com.soen390.erp.email.service.EmailService;
import com.soen390.erp.manufacturing.exceptions.MaterialNotFoundException;
import com.soen390.erp.manufacturing.model.Material;
import com.soen390.erp.manufacturing.repository.MaterialRepository;
import com.soen390.erp.manufacturing.service.MaterialModelAssembler;
import com.soen390.erp.configuration.ResponseEntityWrapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

@RestController
public class MaterialController {

    private final MaterialRepository materialRepository;
    private final MaterialModelAssembler assembler;
    private final EmailService emailService;

    public MaterialController(MaterialRepository materialRepository,
                              MaterialModelAssembler assembler,
                              EmailService emailService)
    {
        this.materialRepository = materialRepository;
        this.assembler = assembler;
        this.emailService = emailService;
    }

    @GetMapping("/materials")
    public ResponseEntity<?> all() {

        List<EntityModel<Material>> materials = assembler.assembleToModel();

        return ResponseEntity.ok().body(
                CollectionModel.of(materials, linkTo(methodOn(MaterialController
                        .class).all()).withSelfRel()));
    }

    @GetMapping(path = "/materials/{id}")
    public ResponseEntity<?> one(@PathVariable int id) {

        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new MaterialNotFoundException(id));

        return ResponseEntity.ok().body(assembler.toModel(material));
    }

    @PostMapping("/materials")
    public ResponseEntityWrapper newMaterial(@RequestBody Material material){

        EntityModel<Material> entityModel = assembler
                .toModel(materialRepository.save(material));

        EmailToSend email = EmailToSend.builder().to("material.manager@msn.com").subject("Added Material").body("A new material has been added with id " + material.getId()).build();
        emailService.sendMail(email);

        return new ResponseEntityWrapper(ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel)
                , "The material was successfully added with id " + material.getId());


    }

    @ResponseBody
    @ExceptionHandler(MaterialNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String partNotFoundException(MaterialNotFoundException ex){
        return ex.getMessage();
    }

}
