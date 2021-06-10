package ru.otus.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import ru.otus.exception.ProcessorCustomException;
import ru.otus.model.Memento;
import ru.otus.model.Message;

class ProcessorThrowsExceptionEvenSecTest {

    @Test
    void process() {
        ProcessorThrowsExceptionEvenSec processor = new ProcessorThrowsExceptionEvenSec(
            LocalDateTime::now);
        Message message = new Message.Builder(1L).build();

        try {
            processor.process(message);
            Memento memento = processor.getMemento();
            int second = processor.defineSecond(memento.getCreatedAt().toString());
            assertTrue(second % 2 != 0);
        } catch (ProcessorCustomException e) {
            int second = Integer.parseInt(StringUtils.substringAfter(e.getMessage(), ": "));
            assertEquals(0, second % 2);
            assertTrue(
                e.getMessage().startsWith("Выброс ProcessorCustomException в четную секунду: "));
        }
    }
}
