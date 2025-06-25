package ru.job4j.concurrent;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int dec = count.incrementAndGet();
        do {
            count.set(dec);
        } while (!count.compareAndSet(dec, count.get()));
    }

    public int get() {
        int result;
        do {
            result = count.get();
        } while (!count.compareAndSet(result, count.get()));
        return result;
    }

    public static void main(String[] args) {
        CASCount casCount = new CASCount();

        casCount.increment();
        casCount.increment();
        System.out.println(casCount.get());
    }
}
