package main;

import matrixmultiplier.DefaultMultiplier;
import matrixmultiplier.FoxMultiplier;

public class TesterDemo {
    public static void main(String[] args) {
        Matrix matrixA = new Matrix(new int[][]{
                {1, 5, 3},
                {2, 3, 5},
                {1, 7, 4}
        });

        Matrix matrixB = new Matrix(new int[][]{
                {1, 2, 3},
                {5, 2, 8},
                {3, 6, 1}
        });

        DefaultMultiplier defaultMultiplier = new DefaultMultiplier(matrixA, matrixB);
        Matrix matrixC = defaultMultiplier.multiply();
        matrixC.print();
        System.out.println("----");
        FoxMultiplier foxMultiplier = new FoxMultiplier(matrixA, matrixB, 2);
        matrixC = foxMultiplier.multiply();
        matrixC.print();
    }
}
