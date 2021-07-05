package ru.otus.repo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import ru.otus.exception.DataTemplateException;
import ru.otus.model.Identifiable;
import ru.otus.repo.mapper.EntitySQLMetaData;

public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final DataTemplateHelper<T> dataGenerator;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData,
        DataTemplateHelper<T> dataGenerator) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.dataGenerator = dataGenerator;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor
            .executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
                try {
                    if (rs.next()) {
                        return dataGenerator.createNewObject(rs);
                    }
                    return null;
                } catch (SQLException e) {
                    throw new DataTemplateException(e);
                }
            });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor
            .executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(),
                rs -> {
                    var resultList = new ArrayList<T>();
                    try {
                        while (rs.next()) {
                            resultList.add(dataGenerator.createNewObject(rs));
                        }
                        return resultList;
                    } catch (SQLException e) {
                        throw new DataTemplateException(e);
                    }
                }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T object) {
        try {
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(),
                dataGenerator.createDbParams(object));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T object) {
        try {
            long id = ((Identifiable) object).getId();
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(id),
                dataGenerator.createDbParams(object));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void delete(Connection connection, long id) {
        try {
            dbExecutor.executeStatement(connection, entitySQLMetaData.getDeleteSql(), List.of(id));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
