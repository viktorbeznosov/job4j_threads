package ru.job4j.pool;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.TimeUnit;

public class Cache<T> {
    private Map<String, T> map = new ConcurrentHashMap<>();

    public Cache(int timeToLive) {
        Executors.newFixedThreadPool(1).submit(
                () -> {
                    try {
                        TimeUnit.MILLISECONDS.sleep(timeToLive);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    map.keySet()
                        .forEach(key -> map.remove(key));
                }
        );
    }

    public void put(String key, T value) {
        map.put(key, value);
    }

    public T get(String key) {
        return map.get(key);
    }
}
