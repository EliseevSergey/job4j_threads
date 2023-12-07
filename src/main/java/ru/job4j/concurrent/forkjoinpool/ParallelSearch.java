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
            return search(in, key, from, to);
        } else {
            if (from == in.length) {
                return -1;
            }
            int mid = (from + to) / 2;
            ParallelSearch<T> parallelSearchLeft = new ParallelSearch<>(in, key, from, mid);
            ParallelSearch<T> parallelSearchRight = new ParallelSearch<>(in, key, mid + 1, to);
            parallelSearchLeft.fork();
            int rightResult = parallelSearchRight.compute();
            int leftResult = parallelSearchLeft.join();
            if (leftResult != -1) {
                return leftResult;
            } else {
                return rightResult;
            }
        }
    }

    private int search(T[] array, T key, int from, int to) {
        int rsl = -1;
        for (int i = from; i <= to; i++) {
            if (array[i].equals(key)) {
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