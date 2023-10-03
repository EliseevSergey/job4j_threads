package ru.job4j.concurrent.demo;

public class TestThreads {
    public static void main(String[] args) {
        Thread first = new Thread(new Mythread());
        Thread second = new Thread(new Mythread());
        first.start();
        second.start();
        System.out.println("Main is done");
    }

    public static class Mythread implements Runnable {
        @Override
        public void run() {
            for (int i = 1000; i > 0; i--) {
                System.out.printf("Thread name %s. i = %s %n", Thread.currentThread().getName(), i);
            }
        }
    }
}
