package ru.otus.exception;

public class DataBaseOperationException extends RuntimeException {

    public DataBaseOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
