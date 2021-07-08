package ru.otus.service;

import java.util.List;
import java.util.Optional;

public interface DataBaseApi<T> {

    T save(T object);

    Optional<T> getById(long id);

    void delete(long id);

    List<T> findAll();
}
