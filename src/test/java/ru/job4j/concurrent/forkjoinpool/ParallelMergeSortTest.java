package ru.job4j.concurrent.forkjoinpool;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParallelMergeSortTest {
    @Test
    public void whenOK() {
        int[] rsl = ParallelMergeSort.sort(new int[]{-6, 3, 2, 50, -100});
        int[] exp = new int[]{-100, -6, 2, 3, 50};
        assertArrayEquals(exp, rsl);
    }
}