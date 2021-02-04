package com.soen390.erp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soen390.erp.users.controller.UserController;
import com.soen390.erp.users.model.User;
import com.soen390.erp.users.repository.UserRepository;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Optional;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class EnterpriseResourcePlanningApplicationTests {


    @Autowired
    private UserController controller;
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void testTempGreetingMessage() throws Exception {
        assertThat(this.restTemplate.withBasicAuth("mznurie@msn.com","soen390")
                .getForObject("http://localhost:" + port + "/", String.class))
                .contains("Let's ace this!");
    }
    @Test
    void userExists() throws JSONException, JsonProcessingException {
        String url = "http://localhost:" + port + "/api/create-user";

        restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject userJsonObject = new JSONObject();
        userJsonObject.put("firstname", "testFirstName");
        userJsonObject.put("lastname", "testLastName");
        userJsonObject.put("username", "test@email.com");
        userJsonObject.put("password", "testPassword");
        userJsonObject.put("role", "ROLE_ADMIN");
        userJsonObject.put("active", "true");

        HttpEntity<String> request = new HttpEntity<String>(userJsonObject.toString(), headers);

        String personResultAsJsonStr = restTemplate.postForObject(url, request, String.class);

        Optional<User> me = userRepository.findByUsername("test@email.com");
        assertThat(me).isNotNull();
    }

    @After
    void cleanDatabase(){
        userRepository.findByUsername("test@email.com")
                .ifPresent(userRepository::delete);
    }

}
