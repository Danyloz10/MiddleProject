package com.danyloz.middleproject.controller;

import com.danyloz.middleproject.dto.UserLoginDTO;
import com.danyloz.middleproject.dto.UserDTO;
import com.danyloz.middleproject.exception.Unauthorized401Exception;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class AuthControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthController authService;

    @BeforeEach
    void printApplicationContext() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Arrays.stream(webApplicationContext.getBeanDefinitionNames())
                .map(name -> webApplicationContext.getBean(name).getClass().getName())
                .sorted()
                .forEach(System.out::println);
    }

    @Test
    void login_ValidCredentials_ShouldReturnUserDTOWithToken() throws Exception {
        UserLoginDTO validCredentials = UserLoginDTO.builder()
                .username("testUser")
                .password("password")
                .build();
        UserDTO userDTO = new UserDTO();
        when(authService.login(validCredentials)).thenReturn(userDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCredentials)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwtToken").value(userDTO.getJwtToken()));
    }

    @Test
    void login_InvalidCredentials_ShouldReturnUnauthorized() throws Exception {
        UserLoginDTO invalidCredentials = UserLoginDTO.builder()
                .username("testUser")
                .password("wrongPassword")
                .build();
        when(authService.login(invalidCredentials)).thenThrow(Unauthorized401Exception.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidCredentials)))
                .andExpect(status().isUnauthorized());
    }
}