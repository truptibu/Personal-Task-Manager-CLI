package com.taskmanager.service;

import com.taskmanager.model.*;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.exception.*;
import com.taskmanager.util.DateValidator;
import com.taskmanager.util.TaskValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskValidator taskValidator;
    private final DateValidator dateValidator;

    public TaskService(TaskRepository taskRepository, TaskValidator taskValidator, DateValidator dateValidator) {
        this.taskRepository = taskRepository;
        this.taskValidator = taskValidator;
        this.dateValidator = dateValidator;
    }

    public void createTask(Task task) throws DuplicateTaskException, InvalidDateException, TaskValidationException {
        // Check duplicate
        if (taskRepository.exists(task.getTaskId())) {
            throw new DuplicateTaskException("Task with this ID already exists.");
        }
        // Validate
        TaskValidator.validate(task);
        // Store
        taskRepository.addTask(task);
    }

    public void updateTask(String taskId, Task updatedTask) throws TaskNotFoundException, TaskValidationException {
        // Check existence
        if (!taskRepository.exists(taskId)) {
            throw new TaskNotFoundException("Task with ID: " + taskId + " does not exist.");
        }
        // Validate
        TaskValidator.validateForUpdate(updatedTask);
        // Update
        taskRepository.updateTask(updatedTask);
    }

    public void deleteTask(String taskId) throws TaskNotFoundException {
        if (!taskRepository.exists(taskId)) {
            throw new TaskNotFoundException("Task with ID: " + taskId + " does not exist.");
        }
        taskRepository.deleteTask(taskId);
    }

    public Task getTaskById(String taskId) throws TaskNotFoundException {
        Task task = taskRepository.getTaskById(taskId);
        if (task == null) {
            throw new TaskNotFoundException("Task with ID: " + taskId + " does not exist.");
        }
        return task;
    }

    public List<Task> getTasksByStatus(Status status) {
        return taskRepository.getAllTasks().stream()
                .filter(task -> task.getStatus() == status)
                .collect(Collectors.toList());
    }

    public List<Task> getTasksByPriority(Priority priority) {
        return taskRepository.getAllTasks().stream()
                .filter(task -> task.getPriority() == priority)
                .collect(Collectors.toList());
    }

    public List<Task> getTasksByCategory(String categoryId) {
        return taskRepository.getAllTasks().stream()
                .filter(task -> Objects.nonNull(task.getCategory()))
                .filter(task -> categoryId.equals(task.getCategory().getCategoryId()))
                .collect(Collectors.toList());
    }

    public List<Task> getOverdueTasks() {
        return taskRepository.getAllTasks().stream()
                .filter(task -> task.getDueDate() != null)
                .filter(task -> DateValidator.isPastDate(task.getDueDate()))
                .filter(task -> task.getStatus() != Status.COMPLETED)
                .collect(Collectors.toList());
    }
}
