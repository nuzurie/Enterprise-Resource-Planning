package com.soen390.erp;

import com.soen390.erp.accounting.model.Ledger;
import com.soen390.erp.accounting.repository.LedgerRepository;
import com.soen390.erp.accounting.service.LedgerModelAssembler;
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

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import javax.transaction.Transactional;
import java.util.Date;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LedgerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Mock
    LedgerModelAssembler ledgerModelAssembler;

    @Autowired
    private LedgerRepository ledgerRepository;



    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    public void getAllLedgersTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ledger"))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    @Transactional
    public void getOneLedgerTest() throws Exception {
        Ledger ledger = ledgerRepository.findAll().get(0);
        int ledgerId = ledger.getId();
        mockMvc.perform(MockMvcRequestBuilders.get("/ledger/" + ledgerId))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    public void getNewTransactionTest() throws Exception {
        String ledger = """
        {
             "id":"1",
             "amount":"200",
             "date":\"""" + new Date()+ "\"," + """
             "credit_account_id":"12",
             "debit_account_id":"13",
             "purchase_order_id":"15",
             "sale_order_id":"10"
        }""";


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/ledger")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ledger)
                .with(csrf())).
                andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnsupportedMediaType())
                .andReturn();


    }


}
