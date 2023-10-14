package ru.job4j.concurrent.demo;

public class BlockingQueueUsage {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue bq = new BlockingQueue();
        for (int i = 0; i < 5; i++) {
            bq.put(createTask());
        }
        Thread worker = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                Runnable task;
                try {
                    task = bq.getTask();
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

            }
        }, "Worker");
        worker.start();
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getState() + Thread.currentThread().getName());
        worker.interrupt();
    }

    public static Runnable createTask() {
        return new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " Started");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println(Thread.currentThread().getName() + " Finished");
            }
        };
    }
}
