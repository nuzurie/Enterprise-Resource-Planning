package com.soen390.erp;

import com.soen390.erp.manufacturing.model.Material;
import com.soen390.erp.manufacturing.repository.MaterialRepository;
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
public class MaterialTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    private MaterialRepository materialRepository;

    @Test
    void testMaterialCreation(){
        Material material = new Material();
        assertNotNull(material);
    }

    @Test
    void shouldBeAbletoAddMaterialToRepository(){
        Material material = new Material();
        int count = (int)  materialRepository.count();
        materialRepository.save(material);
        assertEquals(count+1, (int) materialRepository.count());
    }

    @Test
    void shouldBeAbleToDeleteMaterial(){
        Material material = new Material();
        int count = (int)  materialRepository.count();
        materialRepository.save(material);
        assertEquals(count+1, (int) materialRepository.count());
        materialRepository.delete(material);
        assertEquals(count, (int) materialRepository.count());
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    public void testGetMaterialById() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/materials/{id}", 0) .with(csrf()))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk())
                .andReturn();
    }
}
