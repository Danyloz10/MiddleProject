package com.danyloz.middleproject.service;

import com.danyloz.middleproject.dto.CreateTaskDTO;
import com.danyloz.middleproject.dto.TaskDTO;
import com.danyloz.middleproject.dto.UpdateTaskDTO;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    List<TaskDTO> getTasksForUser(UUID userId);
    TaskDTO createTask(CreateTaskDTO createTaskDTO);
    TaskDTO updateTask(UUID taskId, UpdateTaskDTO updateTaskDTO);
    void deleteTask(UUID taskId);
}
