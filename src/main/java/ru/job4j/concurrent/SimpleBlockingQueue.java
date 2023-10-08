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
                System.out.println("No space");
            }
            queue.add(value);
            System.out.println("Add has been done");
            if (queue.size() < capacity) {
                queue.notifyAll();
                System.out.println("Free space is available");
            }
        }
    }

    public T poll() throws  InterruptedException {
        T rsl;
        synchronized (queue) {
            while (queue.isEmpty()) {
                queue.wait();
                System.out.println("Nothing to poll");
            }
            rsl = queue.poll();
                queue.notifyAll();
            System.out.println("Poll has been done");
            }
        return rsl;
    }
}
