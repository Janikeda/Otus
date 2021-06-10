package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorSwapValues11And12 implements MessageProcessor {

    @Override
    public Message process(Message message) {
        return message.toBuilder()
            .field11(message.getField12())
            .field12(message.getField11())
            .build();
    }
}