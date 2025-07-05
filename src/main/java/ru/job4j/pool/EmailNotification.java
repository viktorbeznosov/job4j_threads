package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        pool.submit(() -> {
            String subject = String.format("Notification %s to email %s.", user.getUsername(), user.getEmail());
            String boby = String.format("Add a new event to %s", user.getUsername());
            send(subject, boby, user.getEmail());
        });
    }

    public void close() {
        pool.shutdown();
    }

    public void send(String subject, String body, String email) {
        
    }

}
