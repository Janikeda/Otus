package ru.otus.handlers;

import java.util.Optional;
import ru.otus.dto.ClientData;
import ru.otus.dto.ClientsData;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.otus.messagesystem.message.MessageHelper;
import ru.otus.service.ClientService;

public class SaveClientRequestHandler implements RequestHandler<ClientData> {

    private final ClientService clientService;

    public SaveClientRequestHandler(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        ClientData client = MessageHelper.getPayload(msg);
        clientService.saveClient(client);
        var clients = new ClientsData(clientService.findAll());

        return Optional.of(MessageBuilder.buildReplyMessage(msg, clients));
    }
}
