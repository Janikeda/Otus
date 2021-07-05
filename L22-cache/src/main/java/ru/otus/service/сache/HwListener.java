package ru.otus.service.сache;

public interface HwListener<K, V> {

    void notify(K key, V value, EventType action);
}
