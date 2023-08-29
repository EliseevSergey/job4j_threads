package ru.job4j.concurrent.demo;

public class CurrentThreadDemo {
    public static void main(String[] args) {
    Thread t = Thread.currentThread();
        System.out.println("Current thread is" + t);
        t.setName("Best Thread");
        System.out.println("Current thread is" + t);

        try {
            for (int i = 5; i > 0; i--) {
                System.out.println(i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("main thread is прерван");
        }
    }
}
