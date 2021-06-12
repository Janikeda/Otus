package ru.otus.processor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.exception.ProcessorCustomException;
import ru.otus.model.Message;

class ProcessorThrowsExceptionEvenSecTest {

    Message message;

    @BeforeEach
    void setUp() {
        message = new Message.Builder(1L).build();
    }

    @Test
    void process_Even() {
        ProcessorThrowsExceptionEvenSec processor = new ProcessorThrowsExceptionEvenSec(
            () -> LocalDateTime.of(2021, 6, 12, 12, 12, 2));

        assertThrows(ProcessorCustomException.class, () -> processor.process(message));
    }

    @Test
    void process_Odd() {
        ProcessorThrowsExceptionEvenSec processor = new ProcessorThrowsExceptionEvenSec(
            () -> LocalDateTime.of(2021, 6, 12, 12, 12, 3));
        message = new Message.Builder(1L).build();

        assertDoesNotThrow(() -> processor.process(message));
    }
}
