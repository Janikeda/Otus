package ru.otus.repo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ru.otus.exception.DataBaseOperationException;
import ru.otus.repo.mapper.EntityClassMetaData;

public class DataTemplateHelper<T> {

    private final EntityClassMetaData<T> entityMetaData;

    public DataTemplateHelper(EntityClassMetaData<T> entityMetaData) {
        this.entityMetaData = entityMetaData;
    }

    public T createNewObject(ResultSet resultSet) {
        T result;
        try {
            List<Field> allFields = entityMetaData.getAllFields();
            List<Object> args = new ArrayList<>();
            for (Field field : allFields) {
                args.add(resultSet.getObject(field.getName()));
            }
            Constructor<T> constructor = entityMetaData.getConstructor();
            result = constructor.newInstance(args.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | SQLException e) {
            throw new DataBaseOperationException("EntityDataGenerator: Ошибка при создании объекта",
                e);
        }

        return result;
    }

    public List<Object> createDbParams(T object) throws IllegalAccessException {
        List<Object> params = new ArrayList<>();
        for (Field field : entityMetaData.getAllFields()) {
            field.setAccessible(true);
            Object value = field.get(object);
            if (value != null) {
                params.add(field.get(object));
            }
        }
        return params;
    }
}
