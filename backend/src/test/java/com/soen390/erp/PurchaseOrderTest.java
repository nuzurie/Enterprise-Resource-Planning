package com.soen390.erp;

import com.soen390.erp.accounting.model.Account;
import com.soen390.erp.accounting.model.Ledger;
import com.soen390.erp.accounting.model.PurchaseOrder;
import com.soen390.erp.accounting.repository.AccountRepository;
import com.soen390.erp.accounting.repository.LedgerRepository;
import com.soen390.erp.accounting.repository.PurchaseOrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PurchaseOrderTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Test
    void testPurchaseOrdercreation(){
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        assertNotNull( purchaseOrder , "should create an object of type PurchaseOrder");
    }

    @Test
    void shouldBeAbletoAddanPurchaseOrderToRepository(){
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        int count = (int)  purchaseOrderRepository.count();
        purchaseOrderRepository.save(purchaseOrder);
        assertEquals(count+1, (int) purchaseOrderRepository.count(),
                "should be able to create a purchaseOrder");
    }

    @Test
    void shouldBeAbleToDeletePurchaseOrder(){
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        int count = (int)  purchaseOrderRepository.count();
        purchaseOrderRepository.save(purchaseOrder);
        assertEquals(count+1, (int) purchaseOrderRepository.count(),
                "should be able to create a purchaseOrder");
        purchaseOrderRepository.delete(purchaseOrder);
        assertEquals(count, (int) purchaseOrderRepository.count(),
                "should be able to delete an purchaseOrder");
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    public void testGetPurchaseOrderById() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/purchaseOrder/{id}", 0) .with(csrf()))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk())
                .andReturn();
    }




}
