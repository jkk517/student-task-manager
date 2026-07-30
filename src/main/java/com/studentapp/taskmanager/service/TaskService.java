package com.studentapp.taskmanager.service;

import com.studentapp.taskmanager.dto.TaskRequest;
import com.studentapp.taskmanager.dto.TaskResponse;
import com.studentapp.taskmanager.entity.Task;
import com.studentapp.taskmanager.exception.ResourceNotFoundException;
import com.studentapp.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskResponse createTask(Long userId, TaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setUserId(userId);

        if (request.getPriority() != null) {
            task.setPriority(Task.Priority.valueOf(request.getPriority().toUpperCase()));
        }
        if (request.getStatus() != null) {
            task.setStatus(Task.TaskStatus.valueOf(request.getStatus().toUpperCase()));
        }
        task.setDueDate(request.getDueDate());

        Task saved = taskRepository.save(task);
        return toResponse(saved);
    }

    public List<TaskResponse> getAllTasks(Long userId) {
        return taskRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public TaskResponse getTaskById(Long userId, Long taskId) {
        Task task = findOwnedTask(userId, taskId);
        return toResponse(task);
    }

    public TaskResponse updateTask(Long userId, Long taskId, TaskRequest request) {
        Task task = findOwnedTask(userId, taskId);

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());

        if (request.getPriority() != null) {
            task.setPriority(Task.Priority.valueOf(request.getPriority().toUpperCase()));
        }
        if (request.getStatus() != null) {
            task.setStatus(Task.TaskStatus.valueOf(request.getStatus().toUpperCase()));
        }

        Task updated = taskRepository.save(task);
        return toResponse(updated);
    }

    public void deleteTask(Long userId, Long taskId) {
        Task task = findOwnedTask(userId, taskId);
        taskRepository.delete(task);
    }

    public TaskResponse markAsCompleted(Long userId, Long taskId) {
        Task task = findOwnedTask(userId, taskId);
        task.setStatus(Task.TaskStatus.COMPLETED);
        Task updated = taskRepository.save(task);
        return toResponse(updated);
    }

    public List<TaskResponse> searchTasksByTitle(Long userId, String title) {
        return taskRepository.findByUserIdAndTitleContainingIgnoreCase(userId, title)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    
    private Task findOwnedTask(Long userId, Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

        if (!task.getUserId().equals(userId)) {
            throw new ResourceNotFoundException("Task not found with id: " + taskId);
        }
        return task;
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority().name(),
                task.getDueDate(),
                task.getStatus().name(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.getUserId()
        );
    }
}
