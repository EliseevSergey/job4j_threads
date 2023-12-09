package ru.job4j.concurrent.forkjoinpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {
    private final T[] in;
    private final T key;
    private final int from;
    private final int to;

    public ParallelSearch(T[] in, T key, int from, int to) {
        this.in = in;
        this.key = key;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from < 10) {
            return search();
        }
        int mid = (from + to) / 2;
        ParallelSearch<T> left = new ParallelSearch<>(in, key, from, mid);
        ParallelSearch<T> right = new ParallelSearch<>(in, key, mid + 1, to);
        left.fork();
        right.fork();
        int rightResult = left.join();
        int leftResult = right.join();
        return Math.max(rightResult, leftResult);
    }

    private int search() {
        int rsl = -1;
        for (int i = from; i <= to; i++) {
            if (in[i].equals(key)) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    public static <T> int findIndex(T[] array, T key) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSearch<>(array, key, 0, array.length - 1));
    }
}