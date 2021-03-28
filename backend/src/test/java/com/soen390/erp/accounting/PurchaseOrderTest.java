package com.soen390.erp.accounting;

import com.soen390.erp.accounting.controller.PurchaseOrderController;
import com.soen390.erp.accounting.model.PurchaseOrder;
import com.soen390.erp.accounting.service.AccountService;
import com.soen390.erp.accounting.service.LedgerService;
import com.soen390.erp.accounting.service.PurchaseOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest
public class PurchaseOrderTest {

    @Autowired
    private PurchaseOrderController purchaseOrderController;

    @MockBean
    private PurchaseOrderService purchaseOrderService;
    @MockBean
    private AccountService accountService;
    @MockBean
    private LedgerService ledgerService;

    @Test
    public void getAllPurchaseOrdersTest()
    {
        PurchaseOrder p1 = new PurchaseOrder();

        List<PurchaseOrder> purchaseOrders = new ArrayList<PurchaseOrder>();
        purchaseOrders.add(p1);

        doReturn(purchaseOrders).when(purchaseOrderService).getAllPurchaseOrders();

        ResponseEntity<?> result = purchaseOrderController.all();

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void getOnePurchaseOrderTest()
    {
        int id = 1;
        PurchaseOrder p1 = new PurchaseOrder();
        Optional<PurchaseOrder> purchaseOrder = Optional.of(p1);

        doReturn(purchaseOrder).when(purchaseOrderService).getPurchaseOrder(id);

        ResponseEntity<?> result = purchaseOrderController.one(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void oneNotFoundTest()
    {
        int id = 1;

        doReturn(Optional.empty()).when(purchaseOrderService).getPurchaseOrder(id);

        ResponseEntity<?> result = purchaseOrderController.one(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void createPurchaseOrderTest() throws Exception
    {
        PurchaseOrder p1 = new PurchaseOrder();

        doReturn(true).when(purchaseOrderService).addPurchaseOrder(p1);

        ResponseEntity<?> result = purchaseOrderController.createPurchaseOrder(p1);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void createPurchaseOrderNotModifiedTest()
    {
        PurchaseOrder p1 = new PurchaseOrder();

        doReturn(false).when(purchaseOrderService).addPurchaseOrder(p1);

        ResponseEntity<?> result = purchaseOrderController.createPurchaseOrder(p1);

        assertEquals(HttpStatus.NOT_MODIFIED, result.getStatusCode());
    }

    @Test
    public void makePaymentTest()
    {
        int id = 1;
        PurchaseOrder p1 = new PurchaseOrder();
        Optional<PurchaseOrder> purchaseOrder = Optional.of(p1);

        doReturn(purchaseOrder).when(purchaseOrderService).getPurchaseOrder(id);

        ResponseEntity<?> result = purchaseOrderController.makePayment(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void makePaymentBadRequestTest()
    {
        int id = 1;

        doReturn(Optional.empty()).when(purchaseOrderService).getPurchaseOrder(id);

        ResponseEntity<?> result = purchaseOrderController.makePayment(id);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void receiveMaterialTest()
    {
        int id = 1;
        PurchaseOrder p1 = new PurchaseOrder();
        Optional<PurchaseOrder> purchaseOrder = Optional.of(p1);

        doReturn(purchaseOrder).when(purchaseOrderService).getPurchaseOrder(id);

        ResponseEntity<?> result = purchaseOrderController.receiveMaterial(id);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    public void receiveMaterialBadRequestTest()
    {
        int id = 1;

        doReturn(Optional.empty()).when(purchaseOrderService).getPurchaseOrder(id);

        ResponseEntity<?> result = purchaseOrderController.receiveMaterial(id);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }
}
