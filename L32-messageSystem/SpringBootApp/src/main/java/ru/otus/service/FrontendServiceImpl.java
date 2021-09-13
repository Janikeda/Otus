package ru.otus.service;

import ru.otus.dto.ClientData;
import ru.otus.dto.ClientsData;
import ru.otus.messagesystem.client.MessageCallback;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageType;

public class FrontendServiceImpl implements FrontendService {

    private final MsClient msClient;
    private final String databaseServiceClientName;

    public FrontendServiceImpl(MsClient msClient, String databaseServiceClientName) {
        this.msClient = msClient;
        this.databaseServiceClientName = databaseServiceClientName;
    }

    @Override
    public void getAll(MessageCallback<ClientsData> dataConsumer) {
        Message message = msClient.produceMessage(databaseServiceClientName, null,
            MessageType.GET_ALL, dataConsumer);
        msClient.sendMessage(message);
    }

    @Override
    public void saveClient(ClientData client, MessageCallback<ClientsData> dataConsumer) {
        Message message = msClient.produceMessage(databaseServiceClientName, client,
            MessageType.SAVE, dataConsumer);
        msClient.sendMessage(message);
    }
}
