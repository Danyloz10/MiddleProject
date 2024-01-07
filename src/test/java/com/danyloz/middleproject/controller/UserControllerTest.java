package com.danyloz.middleproject.controller;

import com.danyloz.middleproject.dto.CreateUserDTO;
import com.danyloz.middleproject.dto.UserDTO;
import com.danyloz.middleproject.entity.User;
import com.danyloz.middleproject.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @Test
    void getUser_ValidUserId_ShouldReturnUserDTO() throws Exception {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        UUID userId = UUID.randomUUID();
        UserDTO userDTO = UserDTO.builder()
                .id(userId)
                .username("testUser")
                .jwtToken(null)
                .build();

        when(userService.getUser(userId)).thenReturn(userDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(userDTO.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.jwtToken").value(userDTO.getJwtToken()));

        verify(userService, times(1)).getUser(userId);
    }

    @Test
    void createUser_ValidInput_ShouldReturnUserDTO() throws Exception {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        CreateUserDTO createUserDTO = CreateUserDTO.builder()
                .username("newUser")
                .password("password123")
                .build();

        UserDTO createdUserDTO = UserDTO.builder()
                .id(UUID.randomUUID())
                .username(createUserDTO.getUsername())
                .jwtToken("dummyToken")
                .build();

        when(userService.createUser(createUserDTO)).thenReturn(createdUserDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(createUserDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(createdUserDTO.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(createdUserDTO.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.jwtToken").value(createdUserDTO.getJwtToken()));

        verify(userService, times(1)).createUser(createUserDTO);
    }

    @Test
    void getCurrentUser_AuthenticatedUser_ShouldReturnUserDTO() throws Exception {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        UUID userId = UUID.randomUUID();
        User authenticatedUser = User.builder()
                .id(userId)
                .username("authenticatedUser")
                .password("password123")
                .jwtToken(null)
                .build();

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(authenticatedUser);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/current")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(authenticatedUser.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.jwtToken").value(authenticatedUser.getJwtToken()));

        SecurityContextHolder.getContext().setAuthentication(null);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
