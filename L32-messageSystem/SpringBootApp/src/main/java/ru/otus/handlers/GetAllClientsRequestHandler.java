package ru.otus.handlers;

import java.util.Optional;
import ru.otus.dto.ClientsData;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.otus.service.ClientService;

public class GetAllClientsRequestHandler implements RequestHandler<ClientsData> {

    private final ClientService clientService;

    public GetAllClientsRequestHandler(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        var clients = new ClientsData(clientService.findAll());
        return Optional.of(MessageBuilder.buildReplyMessage(msg, clients));
    }
}
