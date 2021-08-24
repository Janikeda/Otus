package ru.otus.service;

import java.util.List;
import ru.otus.dto.ClientDto;

public interface ClientService {

    boolean saveClient(ClientDto client);

    ClientDto getClient(long id);

    List<ClientDto> findAll();
}
