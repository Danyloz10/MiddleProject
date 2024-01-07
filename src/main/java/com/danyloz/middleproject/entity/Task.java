package com.danyloz.middleproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "check_mark", nullable = false)
    private Boolean checkMark = false;

    @Column(name = "completion_date")
    private LocalDateTime completionDate;

    @Column(name = "user_id")
    private UUID userId;

    @ManyToOne
    @JoinColumn(name="user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "creation_date")
    private LocalDateTime creationDate = LocalDateTime.now();
}
