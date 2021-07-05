package ru.otus.repo.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import ru.otus.exception.IdFieldNotFoundException;
import ru.otus.reflection.Id;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> clazz;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() throws NoSuchMethodException {
        List<Field> allFields = getAllFields();
        Class<?>[] argumentTypes = new Class[allFields.size()];
        allFields
            .stream()
            .map(Field::getType)
            .collect(Collectors.toList())
            .toArray(argumentTypes);
        return clazz.getDeclaredConstructor(argumentTypes);
    }

    @Override
    public Field getIdField() {
        return getAllFields()
            .stream()
            .filter(field -> field.isAnnotationPresent(Id.class))
            .findFirst().orElseThrow(() -> new IdFieldNotFoundException("ID в классе не найдено"));
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.asList(clazz.getDeclaredFields());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return getAllFields()
            .stream()
            .filter(field -> !field.isAnnotationPresent(Id.class))
            .collect(Collectors.toList());
    }
}
