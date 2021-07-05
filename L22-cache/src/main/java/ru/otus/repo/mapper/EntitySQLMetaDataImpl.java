package ru.otus.repo.mapper;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData entityMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return String.format("select * from %s;", entityMetaData.getName());
    }

    @Override
    public String getSelectByIdSql() {
        return String.format("select * from %s where %s = ?;", entityMetaData.getName(),
            entityMetaData.getIdField().getName());
    }

    @Override
    public String getInsertSql() {
        String fields = entityMetaData.getFieldsWithoutId().stream().map(Field::getName).collect(
            Collectors.joining(", "));
        return String.format("insert into %s (%s) values (?);", entityMetaData.getName(), fields);
    }

    @Override
    public String getUpdateSql(long id) {
        String fields = entityMetaData.getAllFields()
            .stream()
            .map(field -> field.getName() + " = ?")
            .collect(Collectors.joining(", "));
        return String.format("update %s set %s where %s = %s;", entityMetaData.getName(),
            fields, entityMetaData.getIdField().getName(), id);
    }

    @Override
    public String getDeleteSql() {
        return String.format("delete from %s where %s = ?;", entityMetaData.getName(),
            entityMetaData.getIdField().getName());
    }
}
