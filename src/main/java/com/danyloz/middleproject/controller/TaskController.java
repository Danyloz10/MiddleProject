package com.danyloz.middleproject.controller;

import com.danyloz.middleproject.dto.CreateTaskDTO;
import com.danyloz.middleproject.dto.TaskDTO;
import com.danyloz.middleproject.dto.UpdateTaskDTO;
import com.danyloz.middleproject.service.TaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/tasks")
@SecurityRequirement(name = "BearerAuth")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping(value = "/{userId}")
    public List<TaskDTO> getTasksForUser(@PathVariable(name = "userId") UUID userId) {
        return taskService.getTasksForUser(userId);
    }
    @PostMapping()
    public TaskDTO createTask(@RequestBody CreateTaskDTO createTaskDTO) {
        return taskService.createTask(createTaskDTO);
    }

    @PutMapping(value = "/{taskId}")
    public TaskDTO updateTask(@PathVariable(name = "taskId") UUID taskId, @RequestBody UpdateTaskDTO updateTaskDTO) {
        return taskService.updateTask(taskId, updateTaskDTO);
    }

    @DeleteMapping(value = "/{taskId}")
    public void deleteTask(@PathVariable(name = "taskId") UUID taskId) {
        taskService.deleteTask(taskId);
    }
}
