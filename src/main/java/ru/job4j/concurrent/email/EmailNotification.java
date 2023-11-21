package ru.job4j.concurrent.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private boolean isStopped = false;
    ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public synchronized void emailTo(User user) {
        if (isStopped) {
            throw new IllegalStateException("Pool is stopped");
        }
        String subject = String.format("Notification %s to email %s", user.getName(), user.getEmail());
        String body = String.format("Add a new event to %s", user.getName());
        pool.submit(() -> {
            send(subject, body, user.getEmail());
        });
    }

    public synchronized void send(String subject, String body, String email) {
        if (isStopped) {
            throw new IllegalStateException("Pool is stopped");
        }
    }

    public synchronized void close() {
        isStopped = true;
        pool.shutdown();
    }


}
