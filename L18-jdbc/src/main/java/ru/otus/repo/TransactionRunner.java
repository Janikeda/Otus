package ru.otus.repo;

public interface TransactionRunner {
    <T> T doInTransaction(TransactionAction<T> action);
}
