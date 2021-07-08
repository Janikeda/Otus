package ru.otus.service.сache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {

    private final Map<K, V> CACHE;
    private final int cacheSize;
    private final List<HwListener<K, V>> listeners = new ArrayList<>();

    public MyCache(int cacheSize) {
        this.cacheSize = cacheSize;
        this.CACHE = new WeakHashMap<>(cacheSize);
    }

    @Override
    public void put(K key, V value) {
        if (CACHE.size() < cacheSize) {
            CACHE.put(key, value);
            notify(key, value, EventType.CREATED);
        }
    }

    @Override
    public void remove(K key) {
        notify(key, null, EventType.UPDATED);
        CACHE.remove(key);
    }

    @Override
    public V get(K key) {
        notify(key, null, EventType.SELECT);
        return CACHE.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    private void notify(K key, V value, EventType action) {
        listeners.forEach(listener -> {
            try {
                listener.notify(key, value, action);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

}
