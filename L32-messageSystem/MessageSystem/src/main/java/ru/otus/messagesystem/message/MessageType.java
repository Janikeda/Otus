package ru.otus.messagesystem.message;

import java.util.Arrays;

public enum MessageType {
    GET_ALL("GetAll"),
    SAVE("Save");

    private final String name;

    MessageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static MessageType getTypeByName(String name) {
        return Arrays.stream(values()).filter(type -> type.name.equals(name)).findFirst()
            .orElseThrow(RuntimeException::new);
    }
}
