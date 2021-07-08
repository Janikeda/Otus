package ru.otus;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.config.DriverManagerDataSource;
import ru.otus.model.Client;
import ru.otus.model.Identifiable;
import ru.otus.model.Manager;
import ru.otus.repo.DataTemplateHelper;
import ru.otus.repo.DataTemplateJdbc;
import ru.otus.repo.DbExecutorImpl;
import ru.otus.repo.TransactionRunnerJdbc;
import ru.otus.repo.mapper.EntityClassMetaData;
import ru.otus.repo.mapper.EntityClassMetaDataImpl;
import ru.otus.repo.mapper.EntitySQLMetaData;
import ru.otus.repo.mapper.EntitySQLMetaDataImpl;
import ru.otus.service.DataBaseApi;
import ru.otus.service.DbServiceClientImpl;
import ru.otus.service.DbServiceManagerImpl;
import ru.otus.service.сache.EventType;
import ru.otus.service.сache.HwListener;
import ru.otus.service.сache.MyCache;

public class HomeWork {

    private static final String URL = "jdbc:postgresql://localhost:5430/demoDB";
    private static final String USER = "usr";
    private static final String PASSWORD = "pwd";

    private static final Logger log = LoggerFactory.getLogger(HomeWork.class);

    public static void main(String[] args) {
// Общая часть
        var dataSource = new DriverManagerDataSource(URL, USER, PASSWORD);
        flywayMigrations(dataSource);
        var transactionRunner = new TransactionRunnerJdbc(dataSource);
        var dbExecutor = new DbExecutorImpl();

// Работа с клиентом
        EntityClassMetaData entityClassMetaDataClient = new EntityClassMetaDataImpl(
            Client.class);
        EntitySQLMetaData entitySQLMetaDataClient = new EntitySQLMetaDataImpl(
            entityClassMetaDataClient);
        DataTemplateHelper<Client> dataGeneratorClient = new DataTemplateHelper<>(
            entityClassMetaDataClient);
        var dataTemplateClient = new DataTemplateJdbc<>(dbExecutor,
            entitySQLMetaDataClient, dataGeneratorClient); //реализация DataTemplate, универсальная

        var clientCache = new MyCache<Long, Client>(4);
        HwListener<Long, Client> listener = new HwListener<>() {
            @Override
            public void notify(Long key, Client value, EventType action) {
                if (action == EventType.CREATED) {
                    log.info("key:{}, value:{}, action: {}", key, value, action);
                }
            }
        };
        clientCache.addListener(listener);
        var dbServiceClientWithCache = new DbServiceClientImpl(transactionRunner,
            dataTemplateClient,
            clientCache);
        var dbServiceClientNoCache = new DbServiceClientImpl(transactionRunner, dataTemplateClient,
            null);

        List<Identifiable> savedClients = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            Client client = dbServiceClientWithCache.save(new Client("Client_" + i));
            savedClients.add(client);
        }

        log.info("DbServiceClientWithCache");
        funToMeasure(dbServiceClientWithCache, savedClients);
        savedClients.clear();
        clientCache.removeListener(listener);

        for (int i = 0; i <= 10; i++) {
            Client client = dbServiceClientNoCache.save(new Client("Client_" + i));
            savedClients.add(client);
        }
        log.info("DbServiceClientNoCache");
        funToMeasure(dbServiceClientNoCache, savedClients);

        log.info("------------------------------------------------------------------------------");
        EntityClassMetaData entityClassMetaDataManager = new EntityClassMetaDataImpl(
            Manager.class);
        EntitySQLMetaData entitySQLMetaDataManager = new EntitySQLMetaDataImpl(
            entityClassMetaDataManager);
        DataTemplateHelper<Manager> dataGeneratorManager = new DataTemplateHelper<>(
            entityClassMetaDataManager);
        var dataTemplateManager = new DataTemplateJdbc<>(dbExecutor,
            entitySQLMetaDataManager, dataGeneratorManager);

        var managerCache = new MyCache<Long, Manager>(7);
        var dbServiceManagerWithCache = new DbServiceManagerImpl(transactionRunner,
            dataTemplateManager,
            managerCache);
        var dbServiceManagerNoCache = new DbServiceManagerImpl(transactionRunner,
            dataTemplateManager,
            null);

        List<Identifiable> savedManagers = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            Manager manager = dbServiceManagerWithCache.save(new Manager("Manager_" + i));
            savedManagers.add(manager);
        }

        log.info("DbServiceManagerWithCache");
        funToMeasure(dbServiceManagerWithCache, savedManagers);
        savedManagers.clear();

        for (int i = 0; i <= 10; i++) {
            Manager client = dbServiceManagerNoCache.save(new Manager("Manager_" + i));
            savedManagers.add(client);
        }
        log.info("DbServiceManagerNoCache");
        funToMeasure(dbServiceManagerNoCache, savedManagers);
    }

    private static void flywayMigrations(DataSource dataSource) {
        log.info("db db started...");
        var flyway = Flyway.configure()
            .dataSource(dataSource)
            .locations("classpath:db/migration")
            .load();
        flyway.migrate();
        log.info("db db finished.");
        log.info("***");
    }

    private static <T> void funToMeasure(DataBaseApi<T> dataBaseApi, List<Identifiable> models) {
        long start = System.currentTimeMillis();
        for (Identifiable model : models) {
            dataBaseApi.getById(model.getId());
        }
        long finish = System.currentTimeMillis();
        log.info("Execution time: {}", finish - start);
    }
}
