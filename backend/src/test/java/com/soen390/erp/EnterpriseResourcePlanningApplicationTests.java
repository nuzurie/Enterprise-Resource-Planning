package com.soen390.erp;


import com.soen390.erp.inventory.model.Plant;
import com.soen390.erp.inventory.model.PlantMaterial;
import com.soen390.erp.inventory.repository.PlantRepository;
import com.soen390.erp.manufacturing.model.*;
import com.soen390.erp.manufacturing.repository.BikeRepository;
import com.soen390.erp.manufacturing.repository.MaterialRepository;
import com.soen390.erp.manufacturing.repository.PartRepository;
import com.soen390.erp.inventory.repository.PlantMaterialRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EnterpriseResourcePlanningApplicationTests{

    @Autowired
    private MaterialRepository materialRepository;
    private PartRepository partRepository;
    private BikeRepository bikeRepository;
    @Autowired
    private PlantRepository plantRepository;
    @Autowired
    PlantMaterialRepository plantMaterialRepository;

//    @Autowired
//    public EnterpriseResourcePlanningApplicationTests(MaterialRepository materialRepository, PartRepository partRepository, BikeRepository bikeRepository, PlantRepository plantRepository) {
//        this.materialRepository = materialRepository;
//        this.partRepository = partRepository;
//        this.bikeRepository = bikeRepository;
//        this.plantRepository = plantRepository;
//    }


    @Test
    void contextLoads() {
    }

    @Test
    void testPlant(){
//        Material m1 = materialRepository.findAll().get(1);

        Material m1 = materialRepository.findById(4).orElseGet(()->Material.builder().name("didn't find").cost(10).build());
//        m1.setName("test");
//        m1.setCost(21);
//        materialRepository.save(m1);
//        PlantMaterial pm = PlantMaterial.builder().material(m1).quantity(12).build();
        PlantMaterial pm = plantMaterialRepository.findByMaterial(m1).orElseGet(()->PlantMaterial.builder().material(m1).build());
        pm.setQuantity(28);
        plantMaterialRepository.save(pm);
        Plant plant = plantRepository.findById(7).orElse(new Plant());
//        plant.setName("Plant1");
//        plant.setAddress("123 Street");

        plant.addPlantMaterial(pm);
        plantRepository.save(plant);

    }

    //For development testing only
//    @Test
//    void testParts(){
////
//    Material m1 = new Material();
//    m1.setName("temp");
//    m1.setCost(10);
//    materialRepository.save(m1);
//
////
//    Wheel wheel = new Wheel();
//    wheel.setName("frontwheel121");
//    wheel.setCost(32);
//    wheel.setDiameter(2);
//    wheel.setGear(false);
//    wheel.addMaterial(m1);
//    partRepository.save(wheel);
////
//    Handlebar handlebar = Handlebar.builder().build();
//    Wheel rearWheel = Wheel.builder().build();
//    Seat seat = Seat.builder().build();
//    Pedal pedal = Pedal.builder().build();
//    Frame frame = Frame.builder().build();
//
//    partRepository.saveAll(Set.of(handlebar, rearWheel, seat, pedal, frame));
//
//
//    Bike bike = Bike.builder()
//            .name("Second_bike")
//            .frontwheel(wheel)
//            .rearwheel(rearWheel)
//            .handlebar(handlebar)
//            .seat(seat)
//            .frame(frame)
//            .pedal(pedal)
//        .build();
//
//    bikeRepository.save(bike);
////
////    //TODO: CHECK IF THE ADDED THE SAME PARTS TO ANOTHER BIKE ARE BEING SAVED PROPERLY (possibly not needed)
//    //TODO: CHECK ACCESSORIES BEING ADDED
//    }


}
