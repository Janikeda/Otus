package ru.otus.repo.mapper;

public interface EntitySQLMetaData {

    String getSelectAllSql();

    String getSelectByIdSql();

    String getInsertSql();

    String getUpdateSql(long id);

    String getDeleteSql();
}
