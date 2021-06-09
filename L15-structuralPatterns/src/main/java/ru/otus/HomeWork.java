package ru.otus;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.HistoryListener;
import ru.otus.model.Info;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.LoggerProcessor;
import ru.otus.processor.MessageProcessor;
import ru.otus.processor.ProcessorSwapValues11And12;
import ru.otus.repo.MessageRepository;

public class HomeWork {

    public static void main(String[] args) {
        List<MessageProcessor> processors = Collections
            .singletonList(new LoggerProcessor(new ProcessorSwapValues11And12()));

        var complexProcessor = new ComplexProcessor(processors, ex -> {
        });
        var historyListener = new HistoryListener(new MessageRepository());
        complexProcessor.addListener(historyListener);

        ObjectForMessage objectForMessage = new ObjectForMessage();
        objectForMessage.setData(List.of("data"));
        var message = new Message.Builder(1L)
            .field1("field1")
            .field2("field2")
            .field11("field11")
            .field12("field12")
            .field13(objectForMessage)
            .field14(new Info())
            .build();

        complexProcessor.handle(message);
        Optional<Message> result = historyListener.findMessageById(message.getId());

        result
            .ifPresent(value -> System.out.println("result:" + value));

        complexProcessor.removeListener(historyListener);
    }
}
