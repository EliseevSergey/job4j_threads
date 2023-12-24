package ru.job4j.concurrent.demo;

public class TwoDim {
    private static int sum(int[][] in) {
        int rowSum = 0;
        for (int row = 0; row < in.length; row++) {
            for (int col = 0; col < in[row].length; col++) {
                rowSum = rowSum + in[row][col];
            }
        }
            return rowSum;
    }

    private static int sumVert(int[][] in) {
        int rowSize = in.length;
        int colSize = in[0].length;

        int colSum = 0;
        for (int col = 0; col < colSize; col++) {
            for (int row = 0; row < rowSize; row++) {
                colSum = colSum + in[row][col];
            }
        }
        return colSum;
    }


    public static void main(String[] args) {
        int[][] array2 = {{5, 7, 3, 17}, {1, 2, 4, 5}, {6, 7, 8, 9}};
        int[][] array3 = {{1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}};
        System.out.println(array2.length);
        System.out.println(array2[0][3]);
        System.out.println(array2[0].length);
        System.out.println(sum(array3));
        System.out.println(sumVert(array3));
    }

}
