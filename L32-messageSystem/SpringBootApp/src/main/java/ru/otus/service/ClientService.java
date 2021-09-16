package ru.otus.service;

import java.util.List;
import ru.otus.dto.ClientData;

public interface ClientService {

    boolean saveClient(ClientData client);

    List<ClientData> findAll();
}
