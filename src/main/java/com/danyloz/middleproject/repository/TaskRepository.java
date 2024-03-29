package com.danyloz.middleproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.danyloz.middleproject.entity.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findAllByUserId(UUID userID);
}
