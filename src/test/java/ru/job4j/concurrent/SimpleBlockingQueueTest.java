package ru.job4j.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;
import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {
    @Test
    public void whenFetchAndGetAll() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(2);
        Thread producer = new Thread(
                () -> {
                    IntStream.range(0, 5).forEach(
                            sbq::offer
                    );
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!sbq.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(sbq.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).containsExactly(0, 1, 2, 3, 4);
    }
}