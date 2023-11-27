package ru.job4j.concurrent.forkjoinpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelMergeSort extends RecursiveTask<int[]> {
    private final int[] in;
    private final int from;
    private final int to;

    public ParallelMergeSort(int[] in, int from, int to) {
        this.in = in;
        this.from = from;
        this.to = to;
    }

    @Override
    protected int[] compute() {
        if (from == to) {
            return new int[] {in[from]};
        }
        int mid = (from + to) / 2;

        ParallelMergeSort leftSort = new ParallelMergeSort(in, from, mid);
        ParallelMergeSort rightSort = new ParallelMergeSort(in, mid + 1, to);

        leftSort.fork();
        rightSort.fork();

        int[] left = leftSort.join();
        int[] right = rightSort.join();
        return MergeSorter.merge(left, right);
    }

    public static int[] sort(int[] array) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelMergeSort(array, 0, array.length - 1));
    }
}
