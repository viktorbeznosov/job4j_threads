package ru.job4j.pool;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.TimeUnit;

public class Cache<T> {
    private Map<String, T> map = new ConcurrentHashMap<>();

    public void put(String key, T value, int timeToLive) {
        map.put(key, value);
        removeValueFromTimeToLive(key, timeToLive);
    }

    private void removeValueFromTimeToLive(String key, int timeToLive) {
        Executors.newFixedThreadPool(1).submit(
            () -> {
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

    public static void main(String[] args) throws InterruptedException {
        Cache<String> cache = new Cache<>();
        cache.put("1", "TTL = 10 seconds", 10000);
        cache.put("2", "TTL = 15 seconds", 15000);
        cache.put("3", "TTL = 10 seconds", 10000);
        cache.put("4", "TTL = 10 seconds", 10000);
        cache.put("5", "TTL = 10 seconds", 10000);
        cache.put("6", "TTL = 20 seconds", 20000);
        TimeUnit.MILLISECONDS.sleep(3000);
        System.out.println(cache.get("1"));
        System.out.println(cache.get("2"));
        System.out.println(cache.get("6"));
        TimeUnit.MILLISECONDS.sleep(8000);
        System.out.println(cache.get("1"));
        System.out.println(cache.get("2"));
        System.out.println(cache.get("6"));
        TimeUnit.MILLISECONDS.sleep(10000);
        System.out.println(cache.get("1"));
        System.out.println(cache.get("2"));
        System.out.println(cache.get("6"));
    }
}
