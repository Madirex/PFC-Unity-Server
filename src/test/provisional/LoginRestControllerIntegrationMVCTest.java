package com.madirex.gameserver.controllers.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.madirex.gameserver.config.APIConfig;
import com.madirex.gameserver.controllers.UserController;
import com.madirex.gameserver.dto.login.LoginDTO;
import com.madirex.gameserver.model.Login;
import com.madirex.gameserver.model.User;
import org.junit.Before;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginRestControllerIntegrationMVCTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;
    private final User user = User.builder()
            .id("76b2071a-3f97-4666-bd76-3d3d38ca677d")
            .username("jbatista49")
            .email("aocrigane0@slideshare.net")
            .password("t6YsKgG")
            .build();

    Login login = Login.builder()
            .id("233149e4-b6f3-4692-ac71-2e8123bc24b2")
            .user(user)
            .token("123213412")
            .build();
    @Autowired
    private JacksonTester<LoginDTO> jsonCreateDTO;
    @Autowired
    private JacksonTester<LoginDTO> jsonLoginDTO;

    @Test
    @Order(1)
    void findAllTest() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                        get("/rest/logins/")
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", APIConfig.TEST_TOKEN))
                .andReturn().getResponse();

        ObjectMapper mapper = new ObjectMapper();
        List<LoginDTO> myObjects = Arrays.asList(mapper.readValue(response.getContentAsString(), LoginDTO[].class));

        assertAll(
                () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
                () -> assertEquals(myObjects.get(0).getId(), login.getId()),
                () -> assertEquals(myObjects.get(0).getUser().getUsername(), login.getUser().getUsername()),
                () -> assertEquals(myObjects.get(0).getInstant(), login.getInstant()),
                () -> assertEquals(myObjects.get(0).getToken(), login.getToken())
        );
    }

    @Test
    @Order(2)
    void findByIdTest() throws Exception {

        var response = mockMvc.perform(
                        get("/rest/logins/" + login.getId())
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", APIConfig.TEST_TOKEN))
                .andReturn().getResponse();

        var res = jsonLoginDTO.parseObject(response.getContentAsString());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
                () -> assertEquals(res.getId(), login.getId()),
                () -> assertEquals(res.getUser().getUsername(), login.getUser().getUsername()),
                () -> assertEquals(res.getInstant(), login.getInstant()),
                () -> assertEquals(res.getToken(), login.getToken())
        );
    }

    @Test
    @Order(3)
    void deleteTest() throws Exception {

        var response = mockMvc.perform(MockMvcRequestBuilders.delete("/rest/logins/" + login.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", APIConfig.TEST_TOKEN)).andReturn().getResponse();

        var res = jsonLoginDTO.parseObject(response.getContentAsString());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),

                () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
                () -> assertEquals(res.getUser().getUsername(), login.getUser().getUsername()),
                () -> assertEquals(res.getInstant(), login.getInstant()),
                () -> assertEquals(res.getToken(), login.getToken())
        );
    }
}