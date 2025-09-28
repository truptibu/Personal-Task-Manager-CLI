package com.taskmanager.exception;

//Exception thrown when a task fails business rule validation.

public class TaskValidationException extends Exception {
    public TaskValidationException(String message) {
        super(message);
    }

    public TaskValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
