package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorUpperField10 implements MessageProcessor {

    @Override
    public Message process(Message message) {
        return message.toBuilder().field4(message.getField10().toUpperCase()).build();
    }
}
