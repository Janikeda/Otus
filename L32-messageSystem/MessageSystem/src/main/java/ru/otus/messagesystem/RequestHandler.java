package ru.otus.messagesystem;


import java.util.Optional;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.Message;


public interface RequestHandler<T extends ResultDataType> {

    Optional<Message> handle(Message msg);
}
