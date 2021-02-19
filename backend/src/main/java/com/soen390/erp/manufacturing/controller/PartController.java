package com.soen390.erp.manufacturing.controller;

import com.soen390.erp.manufacturing.model.Part;
import com.soen390.erp.manufacturing.viewer.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
public class PartController {

        @Autowired
        private PartService partCatalogue;

        @GetMapping(path = "/parts")
        public List<Part> retrieveAllPart(){
        return partCatalogue.findAll();
    }

        @GetMapping(path = "/parts/{id}")
        public Part retrievePart (@PathVariable String name) throws Exception {
        Part part = partCatalogue.findOne(name);
        if (part == null)
            throw new Exception("part not found");
        return partCatalogue.findOne(name);
    }

        @PostMapping("/parts")
        public ResponseEntity<Object> creatPart (@RequestBody Part part){
        Part savedPart = partCatalogue.savePart(part);
        // CREATED 201
        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("{id}").
                buildAndExpand(savedPart.getName()).toUri();

        return ResponseEntity.created(location).build();
    }

}


