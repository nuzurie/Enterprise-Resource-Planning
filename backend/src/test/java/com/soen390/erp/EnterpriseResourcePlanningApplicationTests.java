package com.soen390.erp;

//import com.soen390.erp.users.service.UserCreationService;
import com.soen390.erp.users.service.UserCreationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EnterpriseResourcePlanningApplicationTests {

    @Autowired
    UserCreationService userCreationService;

    @Test
    void contextLoads() {
    }

    @Test
    void createUser(){
        userCreationService.createUser("Zubair", "Nurie", "mznurie@msn.com", "soen390", "ROLE_ADMIN");
    }

}
