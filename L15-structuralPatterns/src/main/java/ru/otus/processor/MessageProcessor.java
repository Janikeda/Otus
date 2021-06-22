package ru.otus.processor;

import ru.otus.model.Message;

public interface MessageProcessor {

    Message process(Message message);
}
