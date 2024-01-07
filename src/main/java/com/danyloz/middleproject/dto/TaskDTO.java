package com.danyloz.middleproject.dto;

import com.danyloz.middleproject.entity.Task;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class TaskDTO {
    private UUID id;
    private String description;
    private LocalDateTime dueDate;
    private Boolean checkMark = false;
    private LocalDateTime completionDate;
    private UUID userId;

    public TaskDTO(Task task) {
        this.id = task.getId();
        this.description = task.getDescription();
        this.dueDate = task.getDueDate();
        this.checkMark = task.getCheckMark();
        this.completionDate = task.getCompletionDate();
        this.userId = task.getUserId();
    }
}
