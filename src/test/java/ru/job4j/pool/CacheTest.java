package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.concurrent.TimeUnit;

class CacheTest {
    @Test
    public void whenGetBeforeTtlIsExpiredNotNull() {
        Cache cache = new Cache(1000);
        cache.put("one", 10);
        assertThat(cache.get("one")).isEqualTo(10);
    }

    @Test
    public void whenGetAfterTtlIsExpiredIsNull() {
        Cache cache = new Cache(1000);
        cache.put("one", 10);
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(cache.get("one")).isNull();
    }
}