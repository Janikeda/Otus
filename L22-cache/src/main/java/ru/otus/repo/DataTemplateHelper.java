package ru.otus.repo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import ru.otus.exception.DataBaseOperationException;
import ru.otus.repo.mapper.EntityClassMetaData;

public class DataTemplateHelper<T> {

    private final EntityClassMetaData entityMetaData;

    public DataTemplateHelper(EntityClassMetaData entityMetaData) {
        this.entityMetaData = entityMetaData;
    }

    public T createNewObject(ResultSet resultSet) {
        T result;
        try {
            Constructor<T> constructor = entityMetaData.getConstructor();
            result = constructor.newInstance();

            List<Method> allMethods = entityMetaData.getAllMethods()
                .stream()
                .filter(method -> method.getName().startsWith("set"))
                .collect(Collectors.toList());

            for (Method method : allMethods) {
                String field = method.getName().replaceFirst("^set", "");
                Object object = resultSet.getObject(field);
                method.invoke(result, object);
            }

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
