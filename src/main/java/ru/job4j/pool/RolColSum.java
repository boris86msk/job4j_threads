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
                sums[i].setRowSum(sums[i].getRowSum() + matrix[i][j]);
                sums[j].setColSum(sums[j].getColSum() + matrix[i][j]);
            }
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            CompletableFuture<Sums> future = setValues(matrix, i);
            sums[i] = future.get();
        }
        return sums;
    }

    private static CompletableFuture<Sums> setValues(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(() -> {
            Sums sums = new Sums();
            for (int i = 0; i < matrix.length; i++) {
                sums.setRowSum(sums.getRowSum() + matrix[index][i]);
                sums.setColSum(sums.getColSum() + matrix[i][index]);
            }
            return sums;
        });
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
