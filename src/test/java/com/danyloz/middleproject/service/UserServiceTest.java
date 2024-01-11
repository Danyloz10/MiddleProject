package com.danyloz.middleproject.service;

import com.danyloz.middleproject.dto.CreateUserDTO;
import com.danyloz.middleproject.dto.UserDTO;
import com.danyloz.middleproject.entity.User;
import com.danyloz.middleproject.repository.UserRepository;
import com.danyloz.middleproject.service.implementation.UserServiceImpl;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUser() {
        UUID userId = UUID.randomUUID();
        User user = User.builder().id(userId).username("testUser").password("password").build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserDTO result = userService.getUser(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals(user.getUsername(), result.getUsername());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testCreateUser() {
        CreateUserDTO createUserDTO = new CreateUserDTO("testUser", "password");
        User savedUser = User.builder().id(UUID.randomUUID()).username(createUserDTO.getUsername()).password("encodedPassword").build();

        when(passwordEncoder.encode(createUserDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserDTO result = userService.createUser(createUserDTO);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(createUserDTO.getUsername(), result.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(createUserDTO.getPassword());
    }
}
