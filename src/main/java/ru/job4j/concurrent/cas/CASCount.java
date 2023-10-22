package ru.job4j.concurrent.cas;

import java.util.concurrent.atomic.AtomicInteger;

public class CASCount {
    private final AtomicInteger count = new AtomicInteger(0);

    public void increment() {
        Integer base;
        do {
            base = count.get();
        } while (!count.compareAndSet(base, base + 1));
    }

    public int get() {
        return count.get();
    }
}
