package ru.job4j.pool;

import ru.job4j.concurrent.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final int size = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(() -> tasks.poll().run());
            thread.start();
            threads.add(thread);
        }
    }

    public void work(Runnable job) {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }
}