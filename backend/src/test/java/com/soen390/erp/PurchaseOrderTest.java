package com.soen390.erp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soen390.erp.accounting.model.PurchaseOrder;
import com.soen390.erp.accounting.repository.PurchaseOrderRepository;
import com.soen390.erp.accounting.service.PurchaseOrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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

    @Mock
    PurchaseOrderService purchaseOrderService;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    public void getAllPurchaseOrdersTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/PurchaseOrders"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    public void getOnePurchaseOrderTest() throws Exception {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findAll().get(0);
        int purchaseOrderId = purchaseOrder.getId();
        mockMvc.perform(MockMvcRequestBuilders.get("/PurchaseOrders/" + purchaseOrderId))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    public void NewTransactionTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        String pOrder;
        PurchaseOrder purchaseOrder = new PurchaseOrder();
    }


}
