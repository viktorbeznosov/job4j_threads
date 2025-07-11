package ru.job4j.pool;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        if (matrix.length != matrix[0].length) {
            throw new IllegalArgumentException("The matrix must be square");
        }
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = new Sums();
            sums[i].setRowSum(0);
            sums[i].setColSum(0);
            for (int j = 0; j < matrix.length; j++) {
                sums[i].setColSum(sums[i].getColSum() + matrix[j][i]);
                sums[i].setRowSum(sums[i].getRowSum() + matrix[i][j]);
            }
        }
        return sums;
    }

    private static CompletableFuture<Sums> getSums(int[][] matrix, int step) {
        return CompletableFuture.supplyAsync(
            () -> {
                Sums sums = new Sums();
                sums.setRowSum(0);
                sums.setColSum(0);
                for (int j = 0; j < matrix.length; j++) {
                    sums.setColSum(sums.getColSum() + matrix[j][step]);
                    sums.setRowSum(sums.getRowSum() + matrix[step][j]);
                }
                return sums;
            }
        );
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        if (matrix.length != matrix[0].length) {
            throw new IllegalArgumentException("The matrix must be square");
        }
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = getSums(matrix, i).get();
        }
        return sums;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2}, {3, 4}};
        Sums[] sums = RolColSum.asyncSum(matrix);
        System.out.println(sums[1].getRowSum());
        System.out.println(sums[1].getColSum());
    }
}
