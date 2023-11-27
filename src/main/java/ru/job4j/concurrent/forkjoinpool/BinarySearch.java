package ru.job4j.concurrent.forkjoinpool;

import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class BinarySearch<T> extends RecursiveTask<Integer> {
    private final T[] in;
    private final T key;
    private final int from;
    private final int to;
    private final Comparator<T> cmp;

    public BinarySearch(T[] in, T key, int from, int to, Comparator<T> cmp) {
        this.in = in;
        this.key = key;
        this.from = from;
        this.to = to;
        this.cmp = cmp;
    }

    @Override
    protected Integer compute() {
        if (in.length <= 10) {
            for (int i = 0; i < in.length; i++) {
                if (in[i] == key) {
                    return i;
                }
            }
        }

        if (to >= from) {
            int mid = from + (to - from) / 2;
            if (in[mid].equals(key)) {
                return mid;
            }
            if (Objects.compare(in[mid], key, cmp) > 0) {
                BinarySearch<T> binarySearchLeft = new BinarySearch<>(in, key, from, mid - 1, cmp);
                binarySearchLeft.fork();
                return binarySearchLeft.join();
            } else {
                BinarySearch<T> binarySearchRight = new BinarySearch<>(in, key, mid + 1, to, cmp);
                binarySearchRight.fork();
                return binarySearchRight.join();
            }
        }
        return -1;
    }

    public int findIndex(T[] array, T key) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new BinarySearch<>(array, key, 0, array.length - 1, cmp));
    }
}