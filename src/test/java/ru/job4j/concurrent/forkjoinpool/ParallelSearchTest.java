package ru.job4j.concurrent.forkjoinpool;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParallelSearchTest {

    @Test
    public void whenLessThanTen() {
        Integer[] array = new Integer[]{-6, 2, 3, 50, 100};
        int rslIndex = ParallelSearch.findIndex(array, 50);
        assertEquals(3, rslIndex);
    }

    @Test
    public void whenBiggerThanTen() {
        Integer[] array = new Integer[]{-6, 2, 3, 50, 100, 101, 103, 105, 106, 107, 108, 110, 111};
        int rslIndex = ParallelSearch.findIndex(array, 110);
        assertEquals(11, rslIndex);
    }

    @Test
    public void whenSingl() {
        Integer[] array = new Integer[]{-6};
        int rslIndex = ParallelSearch.findIndex(array, -6);
        assertEquals(0, rslIndex);
    }

    @Test
    public void whenString() {
        String[] array = new String[]{"aa", "bb", "cc", "dd", "ee", "ff", "gg", "hh", "i", "jj", "kk", "ll", "mm"};
        int rslIndex = ParallelSearch.findIndex(array, "ll");
        assertEquals(11, rslIndex);
    }

    @Test
    public void whenNotFoundString() {
        String[] array = new String[]{"aa", "bb", "cc", "dd", "ee", "ff", "gg", "hh", "i", "jj", "kk", "ll", "mm"};
        int rslIndex = ParallelSearch.findIndex(array, "DD");
        assertEquals(-1, rslIndex);
    }
}