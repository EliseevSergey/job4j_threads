package ru.job4j.concurrent.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private volatile boolean on = true;
    ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        String subject = String.format("Notification %s to email %s", user.getName(), user.getEmail());
        String body = String.format("Add a new event to %s", user.getName());
    }

    public void send(String subject, String body, String email) {

    }

    public void close() {
        on = false;
    }


}
