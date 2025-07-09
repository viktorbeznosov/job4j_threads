package ru.job4j.pool;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class Cache<T> {
    private ExecutorService pool;

    private HashMap<String, T> map = new HashMap<>();

    public Cache(long minutes) {
        this.pool = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors(),
                minutes,
                TimeUnit.MINUTES
        );
    }

    public void put(String key, T value) {
        pool.submit(() -> {
            map.put(key, value);
        });
    }

    public T get(String key) {
        if (!pool.isTerminated()) {
            return map.get(key);
        }
        return null;
    }
}
