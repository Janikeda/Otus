package ru.otus.processor;

import java.time.Instant;
import org.apache.commons.lang3.StringUtils;
import ru.otus.exception.ProcessorCustomException;

public class ProcessorThrowsExceptionEvenSec {

    public void process(Instant currentTime) throws ProcessorCustomException {
        int second = defineSecond(currentTime.toString());
        if (second % 2 == 0) {
            String mes = "Выброс ProcessorCustomException в четную секунду: " + second;
            System.out.println(mes);
            throw new ProcessorCustomException(mes);
        } else {
            System.out.println("ProcessorCustomException не выброшено, секунда " + second);
        }
    }

    int defineSecond(String time) {
        String seconds = StringUtils.substringAfterLast(time, ":");
        return Integer.parseInt(StringUtils.substringBefore(seconds, "."));
    }
}
