package ru.job4j.concurrent.demo;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
    public static void main(String[] args) throws InterruptedException {
        Semaphore sem = new Semaphore(0);
        Runnable task = () -> {
            try {
            sem.acquire();
            System.out.println("Нить выполнила задачу");
            sem.release();
        } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        new Thread(task).start();
        Thread.sleep(3000);
        sem.release(1);
    }
}
