package ru.otus.exception;

public class IdFieldNotFoundException extends RuntimeException {

    public IdFieldNotFoundException(String message) {
        super(message);
    }
}
