package ru.job4j.concurrent.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public synchronized void emailTo(User user) {
        String subject = String.format("Notification %s to email %s", user.getName(), user.getEmail());
        String body = String.format("Add a new event to %s", user.getName());
        pool.submit(() -> {
            send(subject, body, user.getEmail());
        });
    }

    public synchronized void send(String subject, String body, String email) {
    }

    public synchronized void close() {
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        pool.shutdown();
    }
}
