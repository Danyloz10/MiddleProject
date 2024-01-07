package com.danyloz.middleproject.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class UpdateTaskDTO {
    private String description;
    private LocalDateTime dueDate;
    private Boolean checkMark = false;
    private LocalDateTime completionDate;
}
