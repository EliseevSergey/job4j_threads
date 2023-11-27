package ru.job4j.concurrent.forkjoinpool;

import org.junit.jupiter.api.Test;
import java.util.Comparator;
import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTest {

    @Test
    public void whenLessThanTen() {
        Integer[] array = new Integer[]{-6, 2, 3, 50, 100};
        Comparator<Integer> cmp = Comparator.naturalOrder();
        BinarySearch<Integer> binarySearch = new BinarySearch<>(array, 50, 0, array.length - 1, cmp);
        int rslIndex = binarySearch.findIndex(array, 50);
        assertEquals(3, rslIndex);
    }

    @Test
    public void whenBiggerThanTen() {
        Integer[] array = new Integer[]{-6, 2, 3, 50, 100, 101, 103, 105, 106, 107, 108, 110, 111};
        Comparator<Integer> cmp = Comparator.naturalOrder();
        BinarySearch<Integer> binarySearch = new BinarySearch<>(array, 110, 0, array.length - 1, cmp);
        int rslIndex = binarySearch.findIndex(array, 110);
        assertEquals(11, rslIndex);
    }

    @Test
    public void whenSingl() {
        Integer[] array = new Integer[]{-6};
        Comparator<Integer> cmp = Comparator.naturalOrder();
        BinarySearch<Integer> binarySearch = new BinarySearch<>(array, -6, 0, array.length - 1, cmp);
        int rslIndex = binarySearch.findIndex(array, -6);
        assertEquals(0, rslIndex);
    }

    @Test
    public void whenString() {
        String[] array = new String[]{"aa", "bb", "cc", "dd", "ee", "ff", "gg", "hh", "i", "jj", "kk", "ll", "mm"};
        Comparator<String> cmp = Comparator.naturalOrder();
        BinarySearch<String> binarySearch = new BinarySearch<>(array, "ll", 0, array.length - 1, cmp);
        int rslIndex = binarySearch.findIndex(array, "ll");
        assertEquals(11, rslIndex);
    }

    @Test
    public void whenNotFoundString() {
        String[] array = new String[]{"aa", "bb", "cc", "dd", "ee", "ff", "gg", "hh", "i", "jj", "kk", "ll", "mm"};
        Comparator<String> cmp = Comparator.naturalOrder();
        BinarySearch<String> binarySearch = new BinarySearch<>(array, "DD", 0, array.length - 1, cmp);
        int rslIndex = binarySearch.findIndex(array, "DD");
        assertEquals(-1, rslIndex);
    }
}