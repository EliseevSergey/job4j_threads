package ru.job4j.concurrent;

import org.junit.jupiter.api.Test;


class SimpleBlockingQueueTest {
    SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<Integer>(2);

    @Test
    public void whenOffer() {
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<Integer>(2);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 50; i++) {
                    sbq.offer(i);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    sbq.poll();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        producer.start();
        consumer.start();
    }
}