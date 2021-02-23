package com.soen390.erp;


import com.soen390.erp.manufacturing.model.*;
import com.soen390.erp.manufacturing.repository.BikeRepository;
import com.soen390.erp.manufacturing.repository.MaterialRepository;
import com.soen390.erp.manufacturing.repository.PartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Set;

@SpringBootTest
class EnterpriseResourcePlanningApplicationTests{

    private MaterialRepository materialRepository;
    private PartRepository partRepository;
    private BikeRepository bikeRepository;

    @Autowired
    public EnterpriseResourcePlanningApplicationTests(MaterialRepository materialRepository, PartRepository partRepository, BikeRepository bikeRepository) {
        this.materialRepository = materialRepository;
        this.partRepository = partRepository;
        this.bikeRepository = bikeRepository;
    }


    @Test
    void contextLoads() {
    }

    //For development testing only
    @Test
    void testParts(){
//
    Material m1 = new Material();
    m1.setName("temp");
    m1.setCost(10);
    materialRepository.save(m1);

//
    Wheel wheel = new Wheel();
    wheel.setName("frontwheel2");
    wheel.setCost(22);
    wheel.setDiameter(6);
    wheel.setGear(false);
    wheel.addMaterial(m1);
    partRepository.save(wheel);
//
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
//            .name("First_bike")
//            .frontwheel(wheel)
//            .rearwheel(rearWheel)
//            .handlebar(handlebar)
//            .seat(seat)
//            .pedal(pedal)
//        .build();
//
//    bikeRepository.save(bike);
//
//    //TODO: CHECK IF THE ADDED THE SAME PARTS TO ANOTHER BIKE ARE BEING SAVED PROPERLY (possibly not needed)
    //TODO: CHECK ACCESSORIES BEING ADDED
    }


}
