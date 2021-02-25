package com.soen390.erp;

import com.soen390.erp.manufacturing.model.Part;
import com.soen390.erp.manufacturing.repository.PartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PartTest {
    @Autowired
    protected MockMvc mockMvc;
    private static final String username = "zubair@gmail.com";
    @Autowired
    private PartRepository partRepository;

    @Test
    void testPartCreation(){
        Part part = new Part();
        assertNotNull(part);
    }

    @Test
    void shouldBeAbletoAddPartToRepository(){
        Part part = new Part();
        int count = (int)  partRepository.count();
        partRepository.save(part);
        assertEquals(count+1, (int) partRepository.count());
    }

    @Test
    void shouldBeAbleToDeletePart(){
        Part part = new Part();
        int count = (int)  partRepository.count();
        partRepository.save(part);
        assertEquals(count+1, (int) partRepository.count());
        partRepository.delete(part);
        assertEquals(count, (int) partRepository.count());
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    public void testGetPartById() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/parts/{id}", 7) .with(csrf()))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk())
                .andReturn();
    }
}
