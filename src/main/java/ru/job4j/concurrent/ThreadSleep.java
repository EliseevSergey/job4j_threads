package ru.job4j.concurrent;

public class ThreadSleep {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        System.out.println("Start loading ..");
                        Thread.sleep(3000);
                        System.out.println("Loaded after  3 sec sleep.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );

        thread.start();
        System.out.printf("Thread name %s. Thread state %s %n", Thread.currentThread().getName(), Thread.currentThread().getState());
        System.out.println("Main");
        System.out.println(thread.getState());
    }
}
