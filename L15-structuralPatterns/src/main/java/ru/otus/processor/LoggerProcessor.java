package ru.otus.processor;

import ru.otus.model.Message;

public class LoggerProcessor implements MessageProcessor {

    private final MessageProcessor processor;

    public LoggerProcessor(MessageProcessor processor) {
        this.processor = processor;
    }

    @Override
    public Message process(Message message) {
        System.out.println("log processing message:" + message);
        return processor.process(message);
    }
}
