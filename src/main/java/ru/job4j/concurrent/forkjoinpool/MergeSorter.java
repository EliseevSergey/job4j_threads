package ru.job4j.concurrent.forkjoinpool;

import java.util.Arrays;

public class MergeSorter {
    public int[] sort(int[] in) {
        int[] rsl;
        if (in == null) {
            throw new IllegalArgumentException("Array is null");
        }
        int length = in.length;
        if (length == 0) {
            throw new IllegalArgumentException("Array length is 0");
        }
        if (length == 1) {
            rsl = in;
        } else {
            int middle = length / 2;
            int[] leftAr = Arrays.copyOfRange(in, 0, middle);
            int[] rightAr = Arrays.copyOfRange(in, middle, length);
            leftAr = sort(leftAr);
            rightAr = sort(rightAr);
            rsl = merge(leftAr, rightAr);
        }
        return rsl;
    }

    public static int[] merge(int[] left, int[] right) {
        int[] rsl = new int[left.length + right.length];
        int l = 0;
        int r = 0;
        int rslIndex = 0;
        while (l < left.length && r < right.length) {
            if (left[l] < right[r]) {
                rsl[rslIndex] = left[l];
                rslIndex++;
                l++;
            } else {
                rsl[rslIndex] = right[r];
                rslIndex++;
                r++;
            }
        }
        while (l < left.length) {
            rsl[rslIndex] = left[l];
            rslIndex++;
            l++;
        }
        while (r < right.length) {
            rsl[rslIndex] = right[r];
            rslIndex++;
            r++;
        }
        return rsl;
    }
}
