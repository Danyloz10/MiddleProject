package com.danyloz.middleproject.service.implementation;


import com.danyloz.middleproject.dto.CreateTaskDTO;
import com.danyloz.middleproject.dto.TaskDTO;
import com.danyloz.middleproject.dto.UpdateTaskDTO;
import com.danyloz.middleproject.entity.Task;
import com.danyloz.middleproject.repository.TaskRepository;
import com.danyloz.middleproject.service.TaskService;
import com.danyloz.middleproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;

    @Override
    public List<TaskDTO> getTasksForUser(UUID userId) {
        return taskRepository.findAllByUserId(userId).stream()
                .map(TaskDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO createTask(CreateTaskDTO createTaskDTO) {
        return new TaskDTO(taskRepository.save(Task.builder()
                .description(createTaskDTO.getDescription())
                .dueDate(createTaskDTO.getDueDate())
                .checkMark(createTaskDTO.getCheckMark())
                .completionDate(createTaskDTO.getCompletionDate())
                .userId(createTaskDTO.getUserId())
                .creationDate(LocalDateTime.now())
                .build()));
    }

    @Override
    public TaskDTO updateTask(UUID taskId, UpdateTaskDTO updateTaskDTO) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NoSuchElementException("Task not found"));
        task.setDescription(updateTaskDTO.getDescription());
        task.setDueDate(updateTaskDTO.getDueDate());
        task.setCheckMark(updateTaskDTO.getCheckMark());
        task.setCompletionDate(updateTaskDTO.getCompletionDate());
        task.setCreationDate(LocalDateTime.now());
        return new TaskDTO(taskRepository.save(task));
    }

    @Override
    public void deleteTask(UUID taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NoSuchElementException("Task not found"));
        taskRepository.delete(task);
    }
}
