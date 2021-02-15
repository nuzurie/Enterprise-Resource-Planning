package com.soen390.erp.manufacturing.controller;

import com.soen390.erp.manufacturing.model.Material;
import com.soen390.erp.manufacturing.viewer.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class MaterialController {

    @Autowired
    private MaterialService materialCatalogue;

    @GetMapping(path = "/materials")
    public List<Material> retrieveAllMaterial(){
        return materialCatalogue.findAll();
    }

    @GetMapping(path = "/materials/{id}")
    public Material retrieveMaterial(@PathVariable String name) throws Exception {
        Material material = materialCatalogue.findOne(name);
        if( material == null )
            throw new Exception("material not found");
        return materialCatalogue.findOne(name);
    }

    @PostMapping("/materials")
    public ResponseEntity<Object> creatMaterial(@RequestBody Material material) {
        Material savedMaterial = materialCatalogue.saveMaterial(material);
        // CREATED 201
        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("{id}").
                buildAndExpand(savedMaterial.getName()).toUri();

        return ResponseEntity.created(location).build();
    }


}
