package ru.job4j.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.TimeUnit;

public class Cache<T> {
    private ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private Map<String, T> map = new ConcurrentHashMap<>();

    public void put(String key, T value, int timeToLive) {
        pool.submit(
            () -> {
                map.put(key, value);
                try {
                    TimeUnit.MILLISECONDS.sleep(timeToLive);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                map.remove(key);
            }
        );
    }

    public T get(String key) {
        return map.get(key);
    }

    public void clear() {
        pool.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        Cache<String> cache = new Cache<>();
        cache.put("first", "TTL = 5 seconds", 5000);
        TimeUnit.MILLISECONDS.sleep(3000);
        System.out.println(cache.get("first"));
        TimeUnit.MILLISECONDS.sleep(7000);
        System.out.println(cache.get("first"));
        cache.clear();
    }
}
