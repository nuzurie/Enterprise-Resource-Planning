package com.soen390.erp.users.controller;

import com.soen390.erp.users.model.User;
import com.soen390.erp.users.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    @Autowired
    private UserController controller;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;
    @LocalServerPort
    private int port;


    private final String testUserName = "test@email.com";

    @Before
    public void setUp() {
    }

    @Test
    public void controllerNotNull() {
        assertThat(controller).isNotNull();
    }

    @Test
    void createUser() throws JSONException {
        String url = "http://localhost:" + port + "/api/create-user";
        String firstNamePropertyName = "firstname";
        String testFirstName = "testFirstName";
        String lastNamePropertyName = "lastname";
        String testLastName = "testLastName";
        String userNamePropertyName = "username";
        String passwordPropertyName = "password";
        String testPassword = "testPassword";
        String rolePropertyName = "role";
        String testRole = "ROLE_ADMIN";
        String activePropertyName = "active";
        String testActive = "true";

        restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject userJsonObject = new JSONObject();
        userJsonObject.put(firstNamePropertyName, testFirstName);
        userJsonObject.put(lastNamePropertyName, testLastName);
        userJsonObject.put(userNamePropertyName, testUserName);
        userJsonObject.put(passwordPropertyName, testPassword);
        userJsonObject.put(rolePropertyName, testRole);
        userJsonObject.put(activePropertyName, testActive);

        HttpEntity<String> request = new HttpEntity<>(userJsonObject.toString(), headers);

        restTemplate.postForObject(url, request, String.class);

        Optional<User> userFromDb = userRepository.findByUsername(testUserName);
        assertThat(userFromDb).isEmpty();
    }


    @After
    public void tearDown() {
        userRepository.findByUsername(testUserName).ifPresent(userRepository::delete);
    }
}