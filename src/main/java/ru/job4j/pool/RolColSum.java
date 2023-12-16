package ru.job4j.pool;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public String toString() {
            return "Sums{"
                    + "rowSum=" + rowSum
                    + ", colSum=" + colSum
                    + '}';
        }
    }

    public static Sums[] sum(int[][] matrix) {
        int rowSize = matrix.length;
        int colSize = matrix[0].length;
        int rslSize = Math.max(rowSize, colSize);
        Sums[] rsl = new Sums[rslSize];
        for (int i = 0; i < rslSize; i++) {
            rsl[i] = new Sums();
        }
        int rowSum = 0;
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                rowSum += matrix[row][col];
            }
            rsl[row].setRowSum(rowSum);
            rowSum = 0;
        }
        int colSum = 0;
        for (int col = 0; col < colSize; col++) {
            for (int row = 0; row < rowSize; row++) {
                colSum += matrix[row][col];
            }
            rsl[col].setColSum(colSum);
            colSum = 0;
        }
        return rsl;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int rowSize = matrix.length;
        int colSize = matrix[0].length;
        int rslSize = Math.max(rowSize, colSize);
        Sums[] rsl = new Sums[rslSize];
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        for (int i = 0; i < rslSize / 2; i++) {
            futures.put(i, getRowColSum(matrix, i, i));
        }
        for (int i = rslSize / 2; i < rslSize; i++) {
            futures.put(i, getRowColSum(matrix, i, i));
        }
        for (Integer key : futures.keySet()) {
            rsl[key] = futures.get(key).get();
        }
        return rsl;
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
                    } else {
                        cell.setColSum(colSum);
                    }
                    return cell;
                }
        );
    }
}
