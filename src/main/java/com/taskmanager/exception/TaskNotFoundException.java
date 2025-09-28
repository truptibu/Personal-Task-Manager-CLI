package com.taskmanager.exception;

//Exception thrown when a task with a given ID does not exist.

public class TaskNotFoundException extends Exception {
    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
