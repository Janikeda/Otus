package ru.otus.processor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import org.junit.jupiter.api.Test;
import ru.otus.exception.ProcessorCustomException;

class ProcessorThrowsExceptionEvenSecTest {

    @Test
    void process() {
        ProcessorThrowsExceptionEvenSec processor = new ProcessorThrowsExceptionEvenSec();
        Instant now = Instant.now();
        int second = processor.defineSecond(now.toString());
        if (second % 2 == 0) {
            assertThrows(ProcessorCustomException.class, () -> processor.process(now));
        } else {
            assertDoesNotThrow(() -> processor.process(now));
        }
    }
}
