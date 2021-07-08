package ru.otus.service;


import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.Client;
import ru.otus.repo.DataTemplate;
import ru.otus.repo.TransactionRunner;
import ru.otus.service.сache.HwCache;

public class DbServiceClientImpl implements DataBaseApi<Client> {

    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionRunner transactionRunner;
    private final HwCache<Long, Client> clientCache;


    public DbServiceClientImpl(TransactionRunner transactionRunner,
        DataTemplate<Client> clientDataTemplate, HwCache<Long, Client> clientCache) {
        this.transactionRunner = transactionRunner;
        this.clientDataTemplate = clientDataTemplate;
        this.clientCache = clientCache;
    }

    @Override
    public Client save(Client client) {
        Client result = transactionRunner.doInTransaction(connection -> {
            if (client.getId() == null) {
                var clientId = clientDataTemplate.insert(connection, client);
                var createdClient = new Client(clientId, client.getName());
                //log.info("created client: {}", createdClient);
                return createdClient;
            }
            clientDataTemplate.update(connection, client);
            log.info("updated client: {}", client);

            return client;
        });
        cacheOperation(cache -> cache.put(result.getId(), result));
        return result;
    }

    @Override
    public Optional<Client> getById(long id) {
        Client client = cacheOperationWithResult(cache -> cache.get(id));
        if (client != null) {
            return Optional.of(client);
        }

        return transactionRunner.doInTransaction(connection -> {
            var clientOptional = clientDataTemplate.findById(connection, id);
            //log.info("client: {}", clientOptional);
            return clientOptional;
        });
    }

    @Override
    public void delete(long id) {
        transactionRunner.doInTransaction(connection -> {
            clientDataTemplate.delete(connection, id);
            return id;
        });
        cacheOperation(cache -> cache.remove(id));
    }

    @Override
    public List<Client> findAll() {
        return transactionRunner.doInTransaction(connection -> {
            var clientList = clientDataTemplate.findAll(connection);
            log.info("clientList:{}", clientList);
            return clientList;
        });
    }

    private void cacheOperation(Consumer<HwCache<Long, Client>> action) {
        if (clientCache != null) {
            action.accept(clientCache);
        }
    }

    private Client cacheOperationWithResult(Function<HwCache<Long, Client>, Client> action) {
        Client result = null;
        if (clientCache != null) {
            result = action.apply(clientCache);
        }
        return result;
    }
}
