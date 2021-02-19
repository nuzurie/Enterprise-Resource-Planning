package com.soen390.erp.manufacturing.viewer;

import com.soen390.erp.manufacturing.model.Material;
import com.soen390.erp.manufacturing.repository.MaterialRepository;
import com.soen390.erp.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MaterialService {

    private static List<Material> materiallist = new ArrayList<>();
    private static int materialId = 0;


    MaterialRepository materialRepository;

    @Autowired
    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    // Find All available Materials
    public List<Material> findAll(){
        return materiallist;
    }

    // Save a material into the material list
    public Material saveMaterial(Material material){
        materialRepository.save(material);
        //TODO: verify object is inserted successfully. maybe by checking if it got a new id
        return material;
    }

    // Find one specific material
    public Material findOne(String name) {
        for(Material material : materiallist) {
            if(material.getName() == name)
                return material;
        }
        return null;
    }



}
