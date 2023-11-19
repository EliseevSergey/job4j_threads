package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;

    public SimpleBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public void offer(T value) throws InterruptedException {
        synchronized (queue) {
            while (queue.size() == capacity) {
                queue.wait();
            }
            queue.add(value);
            queue.notifyAll();
        }
    }

    public T poll() throws InterruptedException {
        T rsl;
        synchronized (queue) {
            while (queue.isEmpty()) {
                queue.wait();
            }
            rsl = queue.poll();
            queue.notifyAll();
        }
        return rsl;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}