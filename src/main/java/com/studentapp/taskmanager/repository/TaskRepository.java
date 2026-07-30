package com.studentapp.taskmanager.repository;

import com.studentapp.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    
    List<Task> findByUserId(Long userId);

    
    List<Task> findByUserIdAndTitleContainingIgnoreCase(Long userId, String title);
}
