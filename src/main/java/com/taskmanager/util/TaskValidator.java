package com.taskmanager.util;

import com.taskmanager.model.Task;
import com.taskmanager.exception.TaskValidationException;

import java.time.LocalDate;


 //Utility class for validating Task business rules.

public class TaskValidator {

    // Validates all necessary business rules for a task before creation/update
    public static void validate(Task task) throws TaskValidationException {
        if (task == null) {
            throw new TaskValidationException("Task cannot be null.");
        }
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new TaskValidationException("Task title cannot be empty.");
        }
        if (task.getDueDate() == null) {
            throw new TaskValidationException("Task due date cannot be null.");
        }
        if (task.getPriority() == null) {
            throw new TaskValidationException("Task priority must be specified.");
        }
        if (task.getCategory() == null) {
            throw new TaskValidationException("Task category must be specified.");
        }
        // Business rule: due date cannot be in the past (unless explicitly allowed)
        if (task.getDueDate().isBefore(LocalDate.now())) {
            throw new TaskValidationException("Task due date cannot be in the past.");
        }
        // Add other rules as required (e.g., description length limits)
    }

    // Overloaded version for update logic, if business rules differ
    public static void validateForUpdate(Task task) throws TaskValidationException {
        // You can add update-specific validation here if needed
        validate(task); // Default to same validation as for create
    }
}
