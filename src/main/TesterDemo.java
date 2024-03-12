package main;

import matrixmultiplier.DefaultMultiplier;
import matrixmultiplier.FoxMultiplier;
import matrixmultiplier.StripeMultiplier;

public class TesterDemo {
    public static void main(String[] args) {
        int matrixSize = 4;
        int threadCount = 4;
        int minValue = -10;
        int maxValue = 10;

        Matrix matrixA = new Matrix(Tester.generateRandomMatrix(matrixSize, matrixSize, minValue, maxValue));
        Matrix matrixB = new Matrix(Tester.generateRandomMatrix(matrixSize, matrixSize, minValue, maxValue));

        System.out.println("Matrix A:");
        matrixA.print();
        System.out.println("\nMatrix B:");
        matrixB.print();

        DefaultMultiplier defaultMultiplier = new DefaultMultiplier(matrixA, matrixB);
        Matrix defaultMatrixC = defaultMultiplier.multiply();
        System.out.println("\nMatrix C (C = A x B), sequential:");
        defaultMatrixC.print();

        StripeMultiplier stripeMultiplier = new StripeMultiplier(matrixA, matrixB, threadCount);
        Matrix stripeMatrixC = stripeMultiplier.multiply();
        System.out.println("\nMatrix C (C = A x B), stripe:");
        stripeMatrixC.print();

        FoxMultiplier foxMultiplier = new FoxMultiplier(matrixA, matrixB, threadCount);
        Matrix foxMatrixC = foxMultiplier.multiply();
        System.out.println("\nMatrix C (C = A x B), fox:");
        foxMatrixC.print();

        if (defaultMatrixC.isEqual(foxMatrixC) && defaultMatrixC.isEqual(stripeMatrixC)) {
            System.out.println("\nResults are valid.");
        }
        else {
            System.out.println("\nError!");
        }
    }
}
