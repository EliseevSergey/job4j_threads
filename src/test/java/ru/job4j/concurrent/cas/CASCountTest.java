package ru.job4j.concurrent.cas;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CASCountTest {
    private final CASCount cc = new CASCount();

    @Test
    public void whenIncrement() throws InterruptedException {
        Thread first = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                cc.increment();
            }
        }
        );
        Thread second = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                cc.increment();
            }
        }
        );
    first.start();
    second.start();
    first.join();
    second.join();
    assertEquals(100, cc.get());
    }
}