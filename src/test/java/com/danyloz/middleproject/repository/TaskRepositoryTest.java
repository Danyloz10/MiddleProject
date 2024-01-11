package com.danyloz.middleproject.repository;

import com.danyloz.middleproject.entity.Task;
import com.danyloz.middleproject.entity.User;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .username("testuser")
                .password("testpassword")
                .build();
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        taskRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findAllByUserId() {
        Task task = Task.builder()
                .description("Sample Task")
                .dueDate(LocalDateTime.now().plusDays(1))
                .checkMark(false)
                .creationDate(LocalDateTime.now())
                .userId(user.getId())
                .build();
        taskRepository.save(task);

        List<Task> tasks = taskRepository.findAllByUserId(user.getId());
        assertEquals(1, tasks.size());
        Task savedTask = tasks.get(0);
        assertEquals("Sample Task", savedTask.getDescription());
        assertEquals(user.getId(), savedTask.getUserId());
    }

    @Test
    void findAllByUserId_NoTasksFound() {
        List<Task> tasks = taskRepository.findAllByUserId(UUID.randomUUID());
        assertEquals(0, tasks.size());
    }
}