package com.example.FinalProject.exception;

public class TaskNotInProgressException extends RuntimeException {
    public TaskNotInProgressException(String message) {
        super(message);
    }
}