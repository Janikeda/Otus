package ru.otus.processor;

import ru.otus.exception.ProcessorCustomException;
import ru.otus.model.Message;

public class ProcessorThrowsExceptionEvenSec implements MessageProcessor {

    private final DateTimeProvider dateTimeProvider;

    public ProcessorThrowsExceptionEvenSec(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        int second = dateTimeProvider.getDate().getSecond();
        if (second % 2 == 0) {
            throw new ProcessorCustomException(
                "Выброс ProcessorCustomException в четную секунду: " + second);
        } else {
            System.out.println("ProcessorCustomException не выброшено, секунда " + second);
        }
        return message;
    }
}
