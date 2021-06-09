package ru.otus.repo;

import java.util.HashMap;
import java.util.Map;
import ru.otus.model.Message;

public class MessageRepository {

    private final Map<Long, Message> dataBase = new HashMap<>();

    public void save(Message message) {
        dataBase.put(message.getId(), message);
    }

    public Message getById(Long id) {
        return dataBase.get(id);
    }
}
