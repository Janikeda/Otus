package ru.otus.dto;

import java.util.List;
import ru.otus.messagesystem.client.ResultDataType;

public class ClientsData extends ResultDataType {

    private List<ClientData> clients;

    public ClientsData(List<ClientData> clients) {
        this.clients = clients;
    }

    public List<ClientData> getClients() {
        return clients;
    }

    public void setClients(List<ClientData> clients) {
        this.clients = clients;
    }
}
