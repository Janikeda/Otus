package ru.otus.model;

import java.time.LocalDateTime;

public class Memento {

    private final LocalDateTime createdAt;
    private final String message;

    public Memento(LocalDateTime createdAt, String message) {
        this.createdAt = createdAt;
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getMessage() {
        return message;
    }
}
