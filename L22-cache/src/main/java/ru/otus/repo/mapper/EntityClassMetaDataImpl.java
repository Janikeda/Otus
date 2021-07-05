package ru.otus.repo.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import ru.otus.exception.IdFieldNotFoundException;
import ru.otus.reflection.Id;

public class EntityClassMetaDataImpl implements EntityClassMetaData {

    private final Class<?> clazz;
    private final List<Field> clazzFields;
    private final List<Method> clazzMethods;

    public EntityClassMetaDataImpl(Class<?> clazz) {
        this.clazz = clazz;
        this.clazzFields = Arrays.asList(clazz.getDeclaredFields());
        this.clazzMethods = Arrays.asList(clazz.getDeclaredMethods());
        this.clazzMethods.forEach(method -> method.setAccessible(true));
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public <T> Constructor<T> getConstructor() throws NoSuchMethodException {
//        Class<?>[] argumentTypes = new Class[clazzFields.size()];
//        clazzFields
//            .stream()
//            .map(Field::getType)
//            .collect(Collectors.toList())
//            .toArray(argumentTypes);
//        return (Constructor<T>) clazz.getDeclaredConstructor(argumentTypes);
//        return Arrays.stream(clazz.getConstructors())
//            .filter(constructor -> constructor.getParameterCount() == 0).findFirst().orElseThrow();
        return (Constructor<T>) clazz.getConstructor(null);
    }

    @Override
    public Field getIdField() {
        return clazzFields
            .stream()
            .filter(field -> field.isAnnotationPresent(Id.class))
            .findFirst().orElseThrow(() -> new IdFieldNotFoundException("ID в классе не найдено"));
    }

    @Override
    public List<Field> getAllFields() {
        return clazzFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return clazzFields
            .stream()
            .filter(field -> !field.isAnnotationPresent(Id.class))
            .collect(Collectors.toList());
    }

    @Override
    public List<Method> getAllMethods() {
        return clazzMethods;
    }
}
