package ru.job4j.pool;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.TimeUnit;

public class Cache<T> {
    private Map<String, T> map = new ConcurrentHashMap<>();

    CompletableFuture<Void> putInTempCache(String key, T value, int timeToLive) {
        return CompletableFuture.runAsync(
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

    public void put(String key, T value, int timeToLive) {
        putInTempCache(key, value, timeToLive);
    }

    public T get(String key) {
        return map.get(key);
    }

    public static void main(String[] args) throws InterruptedException {
        Cache<String> cache = new Cache<>();
        cache.put("first", "TTL = 5 seconds", 5000);
        TimeUnit.MILLISECONDS.sleep(3000);
        System.out.println(cache.get("first"));
        TimeUnit.MILLISECONDS.sleep(7000);
        System.out.println(cache.get("first"));
    }
}
