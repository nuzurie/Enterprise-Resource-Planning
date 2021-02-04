package com.soen390.erp;

import com.soen390.erp.users.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class EnterpriseResourcePlanningApplicationTests {


    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void contextLoads() throws Exception {
    }

    @Test
    public void testTempGreetingMessage() throws Exception {
        assertThat(this.restTemplate.withBasicAuth("mznurie@msn.com","soen390")
                .getForObject("http://localhost:" + port + "/", String.class))
                .contains("Let's ace this!");
    }
}
