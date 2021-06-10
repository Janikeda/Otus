package ru.otus.processor;

import java.time.LocalDateTime;
import org.apache.commons.lang3.StringUtils;
import ru.otus.exception.ProcessorCustomException;
import ru.otus.model.Memento;
import ru.otus.model.Message;

public class ProcessorThrowsExceptionEvenSec implements MessageProcessor {

    private final DateTimeProvider dateTimeProvider;
    private Memento memento;

    public ProcessorThrowsExceptionEvenSec(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        LocalDateTime date = dateTimeProvider.getDate();
        int second = defineSecond(date.toString());
        if (second % 2 == 0) {
            String mes = "Выброс ProcessorCustomException в четную секунду: " + second;
            memento = new Memento(date, mes);
            throw new ProcessorCustomException(mes);
        } else {
            memento = new Memento(date, "ProcessorCustomException не выброшено, секунда " + second);
        }
        return message;
    }

    public Memento getMemento() {
        return memento;
    }

    int defineSecond(String time) {
        String seconds = StringUtils.substringAfterLast(time, ":");
        return Integer.parseInt(StringUtils.substringBefore(seconds, "."));
    }
}
