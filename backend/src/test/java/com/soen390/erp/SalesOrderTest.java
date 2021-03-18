package com.soen390.erp;

import com.soen390.erp.accounting.controller.SaleOrderController;
import com.soen390.erp.accounting.model.SaleOrder;
import com.soen390.erp.accounting.service.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SalesOrderTest {
    @InjectMocks
    private SaleOrderController saleOrderController;


    @Mock
    private SaleOrderService saleOrderService;
    @Mock
    private AccountService accountService;
    @Mock
    private LedgerService ledgerService;

    @Before
    public void setup() {
        saleOrderController = new SaleOrderController(saleOrderService,
                                                                accountService,
                                                                ledgerService);
    }

    @Test
    public void getAllSuppliersTest()
    {
        SaleOrder s1 = new SaleOrder();

        List<SaleOrder> saleOrders = new ArrayList<SaleOrder>();
        saleOrders.add(s1);

        doReturn(saleOrders).when(saleOrderService).getAllSaleOrders();

        ResponseEntity<?> result = saleOrderController.all();

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }


    @Test
    public void oneTest()
    {
        int id = 1;
        SaleOrder s1 = new SaleOrder();
        Optional<SaleOrder> os1 = Optional.of(s1);

        doReturn(os1).when(saleOrderService).getSaleOrder(id);

        ResponseEntity<?> result = saleOrderController.one(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void createSaleOrderTest()
    {
        SaleOrder s1 = new SaleOrder();

        doReturn(true).when(saleOrderService).addSaleOrder(s1);

        ResponseEntity<?> result = saleOrderController.createSaleOrder(s1);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }



}



