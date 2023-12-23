package ru.job4j.pool;

import ru.job4j.concurrent.completablefuture.Sums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static Sums[] sum(int[][] matrix) {
        ArrayList<Sums> rsl = new ArrayList<>();
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (rsl.size() <= row || rsl.size() <= col) {
                    rsl.add(new Sums());
                }
                int val = matrix[row][col];
                int curRow = rsl.get(row).getRowSum();
                int curCol = rsl.get(col).getColSum();
                rsl.get(row).setRowSum(curRow + val);
                rsl.get(col).setColSum(curCol + val);
            }
        }
        return rsl.toArray(Sums[]::new);
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int rslSize = Math.max(matrix.length, matrix[0].length);
        ArrayList<Sums> rsl = new ArrayList<>();
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        for (int i = 0; i < rslSize; i++) {
            futures.put(i, getRowColSum(matrix, i, i));
        }
        for (Integer key : futures.keySet()) {
            rsl.add(futures.get(key).get());
        }
        return rsl.toArray(Sums[]::new);
    }

    public static CompletableFuture<Sums> getRowColSum(int[][] data, int row, int column) {
        return CompletableFuture.supplyAsync(() -> {
                    Sums cell = new Sums();
                    if (row < data.length) {
                        cell.setRowSum(Arrays.stream(data[row]).sum());
                    } else {
                        cell.setRowSum(0);
                    }
                    int colSum = 0;
                    if (column < data[0].length) {
                        for (int r = 0; r < data.length; r++) {
                            colSum += data[r][column];
                        }
                        cell.setColSum(colSum);
                    }
                    return cell;
                }
        );
    }
}
