package ru.javaops.masterjava.matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * gkislin
 * 03.07.2016
 */
public class MatrixUtil {

    // implement parallel multiplication matrixA*matrixB
    public static int[][] concurrentMultiply(int[][] matrixA, int[][] matrixB, ExecutorService executor) throws InterruptedException, ExecutionException {
        final int matrixSize = matrixA.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];
        List<Future<?>> tasks = new ArrayList<>();
        for (int j = 0; j < matrixSize; j++) {
            int n = j;
            tasks.add(executor.submit(() -> rowAndColumnMultiply(matrixA, matrixB, matrixSize, matrixC, n)));}
        for (Future<?> task : tasks) {
            task.get();
        }
        return matrixC;
    }

    // optimize by https://habrahabr.ru/post/114797/
    public static int[][] singleThreadMultiply(int[][] matrixA, int[][] matrixB) {
        final int matrixSize = matrixA.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];
        for (int j = 0; j < matrixSize; j++) {
            rowAndColumnMultiply(matrixA, matrixB, matrixSize, matrixC, j);
        }
        return matrixC;
    }

    public static int[][] create(int size) {
        int[][] matrix = new int[size][size];
        Random rn = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = rn.nextInt(10);
            }
        }
        return matrix;
    }

    public static boolean compare(int[][] matrixA, int[][] matrixB) {
        final int matrixSize = matrixA.length;
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                if (matrixA[i][j] != matrixB[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void rowAndColumnMultiply(int[][] matrixA, int[][] matrixB, int matrixSize, int[][] matrixC, int n) {
        int[] thatColumn = new int[matrixSize];
        for (int k = 0; k < matrixSize; k++) {
            thatColumn[k] = matrixB[k][n];
        }
        for (int i = 0; i < matrixSize; i++) {
            int[] thisRow = matrixA[i];
            int summand = 0;
            for (int k = 0; k < matrixSize; k++) {
                summand += thisRow[k] * thatColumn[k];
            }
            matrixC[i][n] = summand;
        }
    }
}
