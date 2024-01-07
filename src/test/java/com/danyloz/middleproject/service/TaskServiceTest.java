package com.danyloz.middleproject.service;

import com.danyloz.middleproject.dto.CreateTaskDTO;
import com.danyloz.middleproject.dto.TaskDTO;
import com.danyloz.middleproject.dto.UpdateTaskDTO;
import com.danyloz.middleproject.entity.Task;
import com.danyloz.middleproject.repository.TaskRepository;
import com.danyloz.middleproject.service.implementation.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTasksForUser() {
        UUID userId = UUID.randomUUID();
        Task task = Task.builder().id(UUID.randomUUID()).description("Test task").userId(userId).build();

        when(taskRepository.findAllByUserId(userId)).thenReturn(Collections.singletonList(task));
        List<TaskDTO> result = taskService.getTasksForUser(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(task.getId(), result.get(0).getId());
        assertEquals(task.getDescription(), result.get(0).getDescription());
        verify(taskRepository, times(1)).findAllByUserId(userId);
    }

    @Test
    public void testCreateTask() {
        CreateTaskDTO createTaskDTO = new CreateTaskDTO("Test task", LocalDateTime.now(), false, null, UUID.randomUUID());
        Task savedTask = Task.builder().id(UUID.randomUUID()).description(createTaskDTO.getDescription()).userId(createTaskDTO.getUserId()).build();

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        TaskDTO result = taskService.createTask(createTaskDTO);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(createTaskDTO.getDescription(), result.getDescription());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    public void testUpdateTask() {
        UUID taskId = UUID.randomUUID();
        UpdateTaskDTO updateTaskDTO = new UpdateTaskDTO("Updated task", LocalDateTime.now(), true, LocalDateTime.now().plusHours(1));
        Task existingTask = Task.builder().id(taskId).description("Original task").build();

        when(taskRepository.findById(taskId)).thenReturn(java.util.Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskDTO result = taskService.updateTask(taskId, updateTaskDTO);
        assertNotNull(result);
        assertEquals(taskId, result.getId());
        assertEquals(updateTaskDTO.getDescription(), result.getDescription());
        assertTrue(result.getCheckMark());
        assertNotNull(result.getCompletionDate());
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testUpdateTask_WhenTaskFound_ShouldUpdateAndReturnTaskDTO() {
        // Arrange
        UUID taskId = UUID.randomUUID();
        UpdateTaskDTO updateTaskDTO = new UpdateTaskDTO();
        updateTaskDTO.setDescription("Updated Description");
        updateTaskDTO.setDueDate(LocalDateTime.now().plusDays(1));
        updateTaskDTO.setCheckMark(true);
        updateTaskDTO.setCompletionDate(LocalDateTime.now());

        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setDescription("Original Description");
        existingTask.setDueDate(LocalDateTime.now());
        existingTask.setCheckMark(false);
        existingTask.setCompletionDate(null);
        existingTask.setCreationDate(LocalDateTime.now());

        Mockito.when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        TaskDTO updatedTaskDTO = taskService.updateTask(taskId, updateTaskDTO);

        // Assert
        assertEquals(updateTaskDTO.getDescription(), existingTask.getDescription());
        assertEquals(updateTaskDTO.getDueDate(), existingTask.getDueDate());
        assertEquals(updateTaskDTO.getCheckMark(), existingTask.getCheckMark());
        assertEquals(updateTaskDTO.getCompletionDate(), existingTask.getCompletionDate());
    }

    @Test
    void testUpdateTask_WhenTaskNotFound_ShouldThrowNoSuchElementException() {
        // Arrange
        UUID taskId = UUID.randomUUID();
        UpdateTaskDTO updateTaskDTO = new UpdateTaskDTO();

        Mockito.when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoSuchElementException.class, () -> taskService.updateTask(taskId, updateTaskDTO));
    }

    @Test
    public void testDeleteTask() {
        UUID taskId = UUID.randomUUID();
        Task existingTask = Task.builder().id(taskId).description("Test task").build();

        when(taskRepository.findById(taskId)).thenReturn(java.util.Optional.of(existingTask));
        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).delete(existingTask);
    }

    @Test
    void testDeleteTask_WhenTaskFound_ShouldDeleteTask() {
        // Arrange
        UUID taskId = UUID.randomUUID();
        Task existingTask = new Task();
        existingTask.setId(taskId);

        Mockito.when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));

        // Act
        taskService.deleteTask(taskId);

        // Assert
        Mockito.verify(taskRepository, Mockito.times(1)).delete(existingTask);
    }

    @Test
    void testDeleteTask_WhenTaskNotFound_ShouldThrowNoSuchElementException() {
        // Arrange
        UUID taskId = UUID.randomUUID();

        Mockito.when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoSuchElementException.class, () -> taskService.deleteTask(taskId));
    }
}

