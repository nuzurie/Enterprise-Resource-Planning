package com.soen390.erp.inventory.service;

import com.soen390.erp.inventory.model.Plant;
import com.soen390.erp.inventory.repository.PlantRepository;
import com.soen390.erp.manufacturing.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlantService {
    PlantRepository plantRepository;

}

