package com.studentapp.taskmanager.controller;

import com.studentapp.taskmanager.dto.TaskRequest;
import com.studentapp.taskmanager.dto.TaskResponse;
import com.studentapp.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // the JwtAuthFilter puts the userId in as the authentication principal
    private Long getUserId(Authentication authentication) {
        return (Long) authentication.getPrincipal();
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(Authentication authentication,
                                                     @Valid @RequestBody TaskRequest request) {
        TaskResponse response = taskService.createTask(getUserId(authentication), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(Authentication authentication) {
        List<TaskResponse> tasks = taskService.getAllTasks(getUserId(authentication));
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(Authentication authentication, @PathVariable Long id) {
        TaskResponse task = taskService.getTaskById(getUserId(authentication), id);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(Authentication authentication,
                                                     @PathVariable Long id,
                                                     @Valid @RequestBody TaskRequest request) {
        TaskResponse response = taskService.updateTask(getUserId(authentication), id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(Authentication authentication, @PathVariable Long id) {
        taskService.deleteTask(getUserId(authentication), id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<TaskResponse> markAsCompleted(Authentication authentication, @PathVariable Long id) {
        TaskResponse response = taskService.markAsCompleted(getUserId(authentication), id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TaskResponse>> searchTasks(Authentication authentication,
                                                            @RequestParam String title) {
        List<TaskResponse> tasks = taskService.searchTasksByTitle(getUserId(authentication), title);
        return ResponseEntity.ok(tasks);
    }
}
