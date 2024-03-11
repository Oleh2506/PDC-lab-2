package main;

import matrixmultiplier.DefaultMultiplier;
import matrixmultiplier.FoxMultiplier;
import matrixmultiplier.StripeMultiplier;

import java.util.Random;

public class Tester {
    public static void main(String[] args) {
        int matrixSize = 600;
        int threadCount = 36;
        int testsPerExperiment = 3;
        int minValue = -10;
        int maxValue = 10;

        Matrix matrixA = new Matrix(generateRandomMatrix(matrixSize, matrixSize, minValue, maxValue));
        Matrix matrixB = new Matrix(generateRandomMatrix(matrixSize, matrixSize, minValue, maxValue));

//        Matrix defaultMultiplierMatrixC = null;
//        long defaultExecutionTime = 0;
//        for (int i = 0; i < testsPerExperiment; i++) {
//            DefaultMultiplier defaultMultiplier = new DefaultMultiplier(matrixA, matrixB);
//
//            long startTime = System.nanoTime();
//            defaultMultiplierMatrixC = defaultMultiplier.multiply();
//            long endTime = System.nanoTime();
//
//            defaultExecutionTime += (endTime - startTime) / 1000000;
//        }
//        defaultExecutionTime /= testsPerExperiment;
//        System.out.printf("Default Matrix Multiplier (matrix size is %d):\n", matrixSize);
//        System.out.printf("Time: %d ms.\n\n", defaultExecutionTime);

        Matrix stripeMultiplierMatrixC = null;
        long stripeExecutionTime = 0;
        for (int i = 0; i < testsPerExperiment; i++) {
            StripeMultiplier stripeMultiplier = new StripeMultiplier(matrixA, matrixB, threadCount);

            long startTime = System.nanoTime();
            stripeMultiplierMatrixC = stripeMultiplier.multiply();
            long endTime = System.nanoTime();

            stripeExecutionTime += (endTime - startTime) / 1000000;
        }
        stripeExecutionTime /= testsPerExperiment;
        System.out.printf("Stripe Matrix Multiplier (matrix size is %d; thread count is %d):\n", matrixSize, threadCount);
        System.out.printf("Time: %d ms.\n\n", stripeExecutionTime);

        Matrix foxMultiplierMatrixC = null;
        long foxExecutionTime = 0;
        for (int i = 0; i < testsPerExperiment; i++) {
            FoxMultiplier foxMultiplier = new FoxMultiplier(matrixA, matrixB, threadCount);

            long startTime = System.nanoTime();
            foxMultiplierMatrixC = foxMultiplier.multiply();
            long endTime = System.nanoTime();

            foxExecutionTime += (endTime - startTime) / 1000000;
        }
        foxExecutionTime /= testsPerExperiment;
        System.out.printf("Fox Matrix Multiplier (matrix size is %d; thread count is %d):\n", matrixSize, threadCount);
        System.out.printf("Time: %d ms.\n\n", foxExecutionTime);

//        if (defaultMultiplierMatrixC != null && foxMultiplierMatrixC != null && stripeMultiplierMatrixC != null &&
//                defaultMultiplierMatrixC.isEqual(foxMultiplierMatrixC) && defaultMultiplierMatrixC.isEqual(stripeMultiplierMatrixC)) {
//            System.out.println("Results are valid.");
//        }
//        else {
//            System.out.println("Error!");
//        }
    }

    public static int[][] generateRandomMatrix(int rowCount, int columnCount, int minValue, int maxValue) {
        int[][] matrix = new int[rowCount][columnCount];
        Random rand = new Random();

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                matrix[i][j] = rand.nextInt((maxValue - minValue) + 1) + minValue;
            }
        }

        return matrix;
    }
}


