package com.soen390.erp.manufacturing.viewer;

import com.soen390.erp.manufacturing.model.Material;
import com.soen390.erp.manufacturing.model.Part;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
    public class PartService {

        private static List<Part> partlist = new ArrayList<>();
        private static int materialId = 0;

        // this is for me to understand
        private static final Set<Material> handleBarSet = new HashSet<Material>();
        private static final Set<Material> saddleSet = new HashSet<Material>();
        private static final Set<Material> rimSet = new HashSet<Material>();
        private static final Set<Material> tireSet = new HashSet<Material>();



    static {
            partlist.add(new Part("HandleBar" , 100, handleBarSet));
            partlist.add(new Part("Saddle",50,saddleSet));
            partlist.add(new Part("Rim",50,rimSet));
            partlist.add(new Part("Tire",50, tireSet));
        }

        // Find All available parts
        public List<Part> findAll(){
            return partlist;
        }

        // Save a part into the material list
        public Part savePart( Part part){
            if(part.getName()== null) {
                partlist.add(part);
            }
            return part;
        }

        // Find one specific part
        public Part findOne(String name) {
            for(Part part : partlist) {
                if(part.getName() == name)
                    return part;
            }
            return null;
        }





}
