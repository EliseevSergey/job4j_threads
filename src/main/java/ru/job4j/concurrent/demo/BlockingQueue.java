package ru.job4j.concurrent.demo;

import java.util.ArrayList;

public class BlockingQueue {
    ArrayList<Runnable> tasks = new ArrayList<>();

    public Runnable getTask() throws InterruptedException {
        synchronized (this) {
            while (tasks.isEmpty()) {
                wait();
            }
            Runnable task = tasks.get(0);
            tasks.remove(task);
            return task;
        }
    }

    public void put(Runnable in) {
        synchronized (this) {
            tasks.add(in);
            notify();
        }
    }
}