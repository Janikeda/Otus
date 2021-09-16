package ru.otus.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.dto.ClientData;
import ru.otus.model.Client;
import ru.otus.repository.ClientRepository;
import ru.otus.sessionmanager.TransactionManager;

@Service
public class ClientServiceImpl implements ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;
    private final TransactionManager transactionManager;
    private final ClientMapperService mapperService;

    public ClientServiceImpl(ClientRepository clientRepository,
        TransactionManager transactionManager, ClientMapperService mapperService) {
        this.clientRepository = clientRepository;
        this.transactionManager = transactionManager;
        this.mapperService = mapperService;
    }

    @Override
    public boolean saveClient(ClientData clientData) {
        Client clientToSave = mapperService.fromDtoToEntity(clientData);
        Client result = transactionManager.doInTransaction(() -> {
            var savedClient = clientRepository.save(clientToSave);
            log.info("saved client: {}", savedClient);
            return savedClient;
        });
        return !Objects.isNull(result);
    }

    @Override
    public List<ClientData> findAll() {
        var clientList = new ArrayList<Client>();
        clientRepository.findAll().forEach(clientList::add);
        log.info("clientList:{}", clientList);
        return mapperService.fromEntitiesToDtos(clientList);
    }
}
