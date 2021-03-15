package com.soen390.erp.accounting.controller;

import com.soen390.erp.accounting.model.PurchaseOrder;
import com.soen390.erp.accounting.service.PurchaseOrderService;
import com.soen390.erp.inventory.model.SupplierOrder;
import com.soen390.erp.manufacturing.controller.BikeController;
import com.soen390.erp.manufacturing.exceptions.BikeNotFoundException;
import com.soen390.erp.manufacturing.model.Bike;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@RestController
public class PurchaseOrderController {
}
