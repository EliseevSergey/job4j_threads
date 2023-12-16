package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class RolColSumTest {
    @Test
    public void whenOk() {
        int[][] in = {{1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}};
        RolColSum.Sums[] rsl = RolColSum.sum(in);
        for (RolColSum.Sums cell : rsl) {
            System.out.println(cell);
        }
        assertEquals(3, rsl[1].getColSum());
        assertEquals(4, rsl[2].getRowSum());
    }

    @Test
    public void whenSmall() throws ExecutionException, InterruptedException {
        int[][] in = {{1, 1, 1}, {2, 3, 4}, {5, 6, 7}};
        RolColSum.Sums[] rsl = RolColSum.asyncSum(in);
        assertEquals(3, rsl[0].getRowSum());
        assertEquals(9, rsl[1].getRowSum());
        assertEquals(18, rsl[2].getRowSum());
        assertEquals(8, rsl[0].getColSum());
        assertEquals(10, rsl[1].getColSum());
        assertEquals(12, rsl[2].getColSum());
    }
}