package com.taskmanager.exception;

 //Exception thrown when a date is invalid or in the wrong format.

public class InvalidDateException extends Exception {
    public InvalidDateException(String message) {
        super(message);
    }

    public InvalidDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
