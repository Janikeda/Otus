package ru.otus.service;

public interface GsonService {

    String toJson(Object target) throws NoSuchFieldException;

    Object fromJson(String json, Class<?> clazz);
}
