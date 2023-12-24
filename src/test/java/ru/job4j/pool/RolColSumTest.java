package ru.job4j.pool;

import org.junit.jupiter.api.Test;
import ru.job4j.concurrent.completablefuture.Sums;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class RolColSumTest {
    @Test
    public void whenOk() {
        int[][] in = {{1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}};
        Sums[] rsl = RolColSum.sum(in);
        Sums s0 = new Sums(4, 4);
        Sums s1 = new Sums(4, 4);
        Sums s2 = new Sums(4, 4);
        Sums s3 = new Sums(4, 4);
        Sums[] exp = {s0, s1, s2, s3};
        assertArrayEquals(exp, rsl);
    }

    @Test
    public void whenSmall() throws ExecutionException, InterruptedException {
        int[][] in = {{1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}};
        Sums s0 = new Sums(4, 4);
        Sums s1 = new Sums(4, 4);
        Sums s2 = new Sums(4, 4);
        Sums s3 = new Sums(4, 4);
        Sums[] exp = {s0, s1, s2, s3};
        Sums[] rsl = RolColSum.asyncSum(in);
        assertArrayEquals(exp, rsl);
    }
}