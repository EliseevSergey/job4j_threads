package ru.job4j.pool;

public class Mainpool {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool();
        for (int i = 0; i < 10; i++) {
            int taskNo = i;
            Thread thread = new Thread(() -> {
                System.out.printf("Thread name: %s. Task Number: %s %n",
                        Thread.currentThread().getName(), taskNo);
            });
            try {
                threadPool.work(thread);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
