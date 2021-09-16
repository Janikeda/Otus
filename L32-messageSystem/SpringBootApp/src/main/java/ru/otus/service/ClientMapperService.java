package ru.otus.service;

import java.util.List;
import ru.otus.dto.ClientData;
import ru.otus.model.Client;

public interface ClientMapperService {

    List<ClientData> fromEntitiesToDtos(List<Client> clients);

    Client fromDtoToEntity(ClientData clientData);
}
