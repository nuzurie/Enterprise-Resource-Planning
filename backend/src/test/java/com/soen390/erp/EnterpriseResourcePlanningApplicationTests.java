package com.soen390.erp;


import com.soen390.erp.manufacturing.repository.MaterialRepository;
import com.soen390.erp.manufacturing.repository.PartRepository;
import com.soen390.erp.manufacturing.model.Material;
import com.soen390.erp.manufacturing.model.Part;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EnterpriseResourcePlanningApplicationTests{

    @Autowired
    private PartRepository partRepository;

    @Test
    void contextLoads() {
    }

    //For development testing only
//    @Test
//    void testParts(){
//        Material material = new Material();
//        material.setName("First_material");
//        material.setCost(11);
//
//        Part part = new Part();
//        part.setName("First_part");
//        part.setCost(40.2);
//        part.addMaterial(material);
//
//        partRepository.save(part);
//    }
}
