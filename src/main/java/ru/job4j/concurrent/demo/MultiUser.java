package ru.job4j.concurrent.demo;

public class MultiUser {
    public static void main(String[] args) {
        Barrier barrier = new Barrier();
        Thread master = new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " STARTED");
                barrier.on();
                },
                "Master");

        Thread slave = new Thread(() -> {
                barrier.check();
                System.out.println(Thread.currentThread().getName() + " STARTED");
                },
                "Slave");
        master.start();
        slave.start();
    }
}
