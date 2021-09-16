package ru.otus.service;

import ru.otus.dto.ClientData;
import ru.otus.dto.ClientsData;
import ru.otus.messagesystem.client.MessageCallback;

public interface FrontendService {

    void getAll(MessageCallback<ClientsData> dataConsumer);

    void saveClient(ClientData client, MessageCallback<ClientsData> dataConsumer);
}
