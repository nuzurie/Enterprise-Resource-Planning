package com.soen390.erp;

import com.soen390.erp.accounting.controller.SupplierController;
import com.soen390.erp.accounting.model.Supplier;
import com.soen390.erp.accounting.service.SupplierService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SupplierTest {

    @InjectMocks
    private SupplierController supplierController;

    @Mock
    SupplierService supplierService;

    @Before
    public void setup()
    {
        supplierController = new SupplierController(supplierService);
    }


    @Test
    public void getAllSuppliersTest()
    {
        Supplier s1 = new Supplier();
        Supplier s2 = new Supplier();

        List<Supplier> suppliers = new ArrayList<>();
        suppliers.add(s1);
        suppliers.add(s2);

        when(supplierService.findAllSuppliers()).thenReturn(suppliers);

        List<Supplier> result = supplierController.getAllSuppliers();

        assertThat(result.size()).isEqualTo(2);
    }


    @Test
    public void findSupplierByIdTest()
    {
        int id = 1;
        Supplier s1 = new Supplier();
        doReturn(s1).when(supplierService).findSupplierById(id);

        ResponseEntity<?> result = supplierController.findSupplierById(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void addSupplierTest()
    {
        Supplier s1 = new Supplier();
        doReturn(s1).when(supplierService).saveSupplier(s1);

        ResponseEntity<?> result = supplierController.addSupplier(s1);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    public void deleteSupplierByIdTest()
    {
        int id = 1;
        doReturn(true).when(supplierService).deleteSupplierById(id);

        ResponseEntity<?> result = supplierController.deleteSupplierById(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }


}
