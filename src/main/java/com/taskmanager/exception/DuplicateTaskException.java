package com.taskmanager.exception;

 //Exception thrown when adding a task with a duplicate ID or properties.

public class DuplicateTaskException extends Exception {
    public DuplicateTaskException(String message) {
        super(message);
    }

    public DuplicateTaskException(String message, Throwable cause) {
        super(message, cause);
    }
}
