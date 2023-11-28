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
            return search(in, key);
        } else {
            if (from == in.length) {
                return -1;
            } else {
                ParallelSearch<T> parallelSearch = new ParallelSearch<>(in, key, from + 1, to);
                parallelSearch.fork();
                return parallelSearch.join();
            }
        }
    }

    private int search(T[] array, T key) {
        int rsl = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == key) {
                rsl = i;
            }
        }
        return rsl;
    }

    public static <T> int findIndex(T[] array, T key) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSearch<>(array, key, 0, array.length - 1));
    }
}