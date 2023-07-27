package ru.job4j.pool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {


    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = new Sums();
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                sums[i].setRowSum(matrix[i][j]);
                sums[j].setColSum(matrix[i][j]);
            }
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = new Sums();
        }

        for (int i = 0; i < matrix.length; i++) {
            CompletableFuture<Integer> future = setRow(matrix, i);
            CompletableFuture<Integer> future1 = setCol(matrix, i);
            sums[i].setRowSum(future.get());
            sums[i].setColSum(future1.get());
        }
        return sums;
    }

    private static CompletableFuture<Integer> setRow(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            for (int i = 0; i < matrix.length; i++) {
                sum += matrix[index][i];
            }
            return sum;
        });
    }

    private static CompletableFuture<Integer> setCol(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            for (int i = 0; i < matrix.length; i++) {
                sum += matrix[i][index];
            }
            return sum;
        });
    }

    public static class Sums {

        private int rowSum = 0;
        private int colSum = 0;


        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum += rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum += colSum;
        }
    }
}
