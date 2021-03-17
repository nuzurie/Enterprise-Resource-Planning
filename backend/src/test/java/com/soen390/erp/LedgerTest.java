package com.soen390.erp;

import com.soen390.erp.accounting.model.Ledger;
import com.soen390.erp.accounting.repository.LedgerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LedgerTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    private LedgerRepository ledgerRepository;

    @Test
    void testLedgercreation(){
        Ledger ledger = new Ledger();
        assertNotNull( ledger , "should create an object of type ledger");
    }

    @Test
    void shouldBeAbletoAddaledgerToRepository(){
        Ledger ledger = new Ledger();
        int count = (int)  ledgerRepository.count();
        ledgerRepository.save(ledger);
        assertEquals(count+1, (int) ledgerRepository.count(),
                "should be able to create a ledger");
    }

    @Test
    void shouldBeAbleToDeleteLedger(){
        Ledger ledger = new Ledger();
        int count = (int)  ledgerRepository.count();
        ledgerRepository.save(ledger);
        assertEquals(count+1, (int) ledgerRepository.count(),
                "should be able to create a ledger");
        ledgerRepository.delete(ledger);
        assertEquals(count, (int) ledgerRepository.count(),
                "should be able to delete a ledger");
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    public void testGetledgerById() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/ledger/{id}", 0) .with(csrf()))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk())
                .andReturn();
    }




}
