package ru.job4j.concurrent;

public class CountBarrier {
    private final Object monitor = this;
    private final int total;
    private int count = 0;

    public CountBarrier(int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }


    public static void main(String[] args) {
        CountBarrier countBarrier = new CountBarrier(6);
        Thread countCaller = new Thread(() -> {
            for (int i = 0; i < 12; i++) {
                countBarrier.count();
                System.out.printf("Total=%s. Count=%s. i=%s. %s. %s  %n",
                        countBarrier.total,
                        countBarrier.count,
                        i,
                        Thread.currentThread().getName(),
                        Thread.currentThread().getState());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "countCallerThread");

        Thread afterCount = new Thread(() -> {
            countBarrier.await();
            System.out.printf("Total=%s. Count=%s.    %s. %s  %n",
                    countBarrier.total,
                    countBarrier.count,
                    Thread.currentThread().getName(),
                    Thread.currentThread().getState());
        }, "afterCountThread");

        countCaller.start();
        afterCount.start();
    }
}
