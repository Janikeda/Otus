package ru.otus.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.Manager;
import ru.otus.repo.DataTemplate;
import ru.otus.repo.TransactionRunner;
import ru.otus.service.сache.HwCache;

public class DbServiceManagerImpl implements DataBaseApi<Manager> {

    private static final Logger log = LoggerFactory.getLogger(DbServiceManagerImpl.class);

    private final DataTemplate<Manager> managerDataTemplate;
    private final TransactionRunner transactionRunner;
    private final HwCache<Long, Manager> managerCache;

    public DbServiceManagerImpl(TransactionRunner transactionRunner,
        DataTemplate<Manager> managerDataTemplate,
        HwCache<Long, Manager> managerCache) {
        this.transactionRunner = transactionRunner;
        this.managerDataTemplate = managerDataTemplate;
        this.managerCache = managerCache;
    }

    @Override
    public Manager save(Manager manager) {
        Manager result = transactionRunner.doInTransaction(connection -> {
            if (manager.getNo() == null) {
                var managerNo = managerDataTemplate.insert(connection, manager);
                var createdManager = new Manager(managerNo, manager.getLabel());
                //log.info("created manager: {}", createdManager);
                return createdManager;
            }
            managerDataTemplate.update(connection, manager);
            log.info("updated manager: {}", manager);
            return manager;
        });
        cacheOperation(cache -> cache.put(result.getId(), result));
        return result;
    }

    @Override
    public Optional<Manager> getById(long no) {
        Manager manager = cacheOperationWithResult(cache -> cache.get(no));
        if (manager != null) {
            return Optional.of(manager);
        }
        return transactionRunner.doInTransaction(connection -> {
            var managerOptional = managerDataTemplate.findById(connection, no);
            //log.info("manager: {}", managerOptional);
            return managerOptional;
        });
    }

    @Override
    public List<Manager> findAll() {
        return transactionRunner.doInTransaction(connection -> {
            var managerList = managerDataTemplate.findAll(connection);
            log.info("managerList:{}", managerList);
            return managerList;
        });
    }

    @Override
    public void delete(long id) {
        transactionRunner.doInTransaction(connection -> {
            managerDataTemplate.delete(connection, id);
            return id;
        });
        cacheOperation(cache -> cache.remove(id));
    }

    private void cacheOperation(Consumer<HwCache<Long, Manager>> action) {
        if (managerCache != null) {
            action.accept(managerCache);
        }
    }

    private Manager cacheOperationWithResult(Function<HwCache<Long, Manager>, Manager> action) {
        Manager result = null;
        if (managerCache != null) {
            result = action.apply(managerCache);
        }
        return result;
    }
}
