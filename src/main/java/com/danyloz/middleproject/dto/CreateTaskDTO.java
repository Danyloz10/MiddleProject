package com.danyloz.middleproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CreateTaskDTO {
    private String description;
    private LocalDateTime dueDate;
    private Boolean checkMark = false;
    private LocalDateTime completionDate;
    private UUID userId;
}
