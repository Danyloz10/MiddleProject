package com.danyloz.middleproject.repository;

import com.danyloz.middleproject.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        // Given
        User user = User.builder()
                .username("john.doe")
                .password("password")
                .jwtToken("token")
                .build();

        // When
        User savedUser = userRepository.save(user);

        // Then
        assertNotNull(savedUser.getId());
        assertEquals(user.getUsername(), savedUser.getUsername());
        assertEquals(user.getPassword(), savedUser.getPassword());
        assertEquals(user.getJwtToken(), savedUser.getJwtToken());
    }

    @Test
    public void testFindById() {
        // Given
        User user = User.builder()
                .username("jane.doe")
                .password("password")
                .jwtToken("token")
                .build();
        User savedUser = userRepository.save(user);

        // When
        Optional<User> foundUserOptional = userRepository.findById(savedUser.getId());

        // Then
        assertTrue(foundUserOptional.isPresent());
        User foundUser = foundUserOptional.get();
        assertEquals(savedUser.getId(), foundUser.getId());
        assertEquals(savedUser.getUsername(), foundUser.getUsername());
        assertEquals(savedUser.getPassword(), foundUser.getPassword());
        assertEquals(savedUser.getJwtToken(), foundUser.getJwtToken());
    }

    @Test
    public void testFindByUsername() {
        // Given
        User user = User.builder()
                .username("alice.smith")
                .password("password")
                .jwtToken("token")
                .build();
        userRepository.save(user);

        // When
        Optional<User> foundUserOptional = userRepository.findByUsername("alice.smith");

        // Then
        assertTrue(foundUserOptional.isPresent());
        User foundUser = foundUserOptional.get();
        assertEquals(user.getUsername(), foundUser.getUsername());
        assertEquals(user.getPassword(), foundUser.getPassword());
        assertEquals(user.getJwtToken(), foundUser.getJwtToken());
    }

    @Test
    public void testFindByNonExistingUsername() {
        // When
        Optional<User> foundUserOptional = userRepository.findByUsername("nonexistent");

        // Then
        assertTrue(foundUserOptional.isEmpty());
    }

    @Test
    @DirtiesContext
    public void testDeleteUser() {
        // Given
        User user = User.builder()
                .username("bob.johnson")
                .password("password")
                .jwtToken("token")
                .build();
        User savedUser = userRepository.save(user);

        // When
        userRepository.deleteById(savedUser.getId());

        // Then
        Optional<User> foundUserOptional = userRepository.findById(savedUser.getId());
        assertTrue(foundUserOptional.isEmpty());
    }
}

