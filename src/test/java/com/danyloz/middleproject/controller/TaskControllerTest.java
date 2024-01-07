package com.danyloz.middleproject.controller;

import com.danyloz.middleproject.dto.CreateTaskDTO;
import com.danyloz.middleproject.dto.TaskDTO;
import com.danyloz.middleproject.dto.UpdateTaskDTO;
import com.danyloz.middleproject.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @Test
    void testGetTasksForUser() {
        // Arrange
        UUID userId = UUID.randomUUID();
        List<TaskDTO> expectedTasks = Arrays.asList(new TaskDTO(), new TaskDTO());

        Mockito.when(taskService.getTasksForUser(userId)).thenReturn(expectedTasks);

        // Act
        List<TaskDTO> result = taskController.getTasksForUser(userId);

        // Assert
        assertEquals(expectedTasks, result);
    }

    @Test
    void testCreateTask() {
        // Arrange
        CreateTaskDTO createTaskDTO = new CreateTaskDTO();
        TaskDTO expectedTask = new TaskDTO();

        Mockito.when(taskService.createTask(createTaskDTO)).thenReturn(expectedTask);

        // Act
        TaskDTO result = taskController.createTask(createTaskDTO);

        // Assert
        assertEquals(expectedTask, result);
    }

    @Test
    void testUpdateTask() {
        // Arrange
        UUID taskId = UUID.randomUUID();
        UpdateTaskDTO updateTaskDTO = new UpdateTaskDTO();
        TaskDTO expectedTask = new TaskDTO();

        Mockito.when(taskService.updateTask(taskId, updateTaskDTO)).thenReturn(expectedTask);

        // Act
        TaskDTO result = taskController.updateTask(taskId, updateTaskDTO);

        // Assert
        assertEquals(expectedTask, result);
    }

    @Test
    void testDeleteTask() {
        // Arrange
        UUID taskId = UUID.randomUUID();

        // Act
        taskController.deleteTask(taskId);

        // Assert
        Mockito.verify(taskService, Mockito.times(1)).deleteTask(taskId);
    }
}