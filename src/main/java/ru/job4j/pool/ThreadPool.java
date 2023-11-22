package ru.job4j.pool;

import ru.job4j.concurrent.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(10);
    private final int threadCapacity = Runtime.getRuntime().availableProcessors();

    public ThreadPool() {
        for (int i = 0; i <= threadCapacity; i++) {
            Thread thread = new Thread(() -> {
                try {
                    tasks.poll().run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            );
            threads.add(thread);
        }
        threads.stream().forEach(Thread::start);
    }

    public synchronized void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public synchronized void shoutdown() {
        threads.stream().forEach((t) -> t.interrupt());
    }
}