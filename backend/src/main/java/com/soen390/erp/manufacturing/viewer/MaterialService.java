package com.soen390.erp.manufacturing.viewer;

import com.soen390.erp.manufacturing.model.Material;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MaterialService {

    private static List<Material> materiallist = new ArrayList<>();
    private static int materialId = 0;

    // this is for me to understand
    static {
        materiallist.add(new Material("Rubber" , 10, null));
        materiallist.add(new Material("Paint",5,null));
        materiallist.add(new Material("Metal",25,null));
    }

    // Find All available Materials
    public List<Material> findAll(){
        return materiallist;
    }

    // Save a material into the material list
    public Material saveMaterial( Material material){
        if(material.getName()== null) {
            materiallist.add(material);
        }
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
