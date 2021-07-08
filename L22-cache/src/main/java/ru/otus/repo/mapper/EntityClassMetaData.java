package ru.otus.repo.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public interface EntityClassMetaData {

    String getName();

    <T> Constructor<T> getConstructor() throws NoSuchMethodException;

    //Поле Id должно определять по наличию аннотации Id
    //Аннотацию @Id надо сделать самостоятельно
    Field getIdField();

    List<Field> getAllFields();

    List<Field> getFieldsWithoutId();

    List<Method> getAllMethods();
}
