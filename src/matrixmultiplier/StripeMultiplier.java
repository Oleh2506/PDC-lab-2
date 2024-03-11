package matrixmultiplier;

import main.Matrix;

import java.util.ArrayList;

public class StripeMultiplier implements MatrixMultiplier {
    private final Matrix matrixA;
    private final Matrix matrixB;
    private final Matrix matrixC;
    private final int threadCount;
    private final int rowsPerThread;

    public StripeMultiplier(Matrix matrixA, Matrix matrixB, int threadCount) {
        if (matrixA.getColumnCount() != matrixB.getRowCount()) {
            throw new IllegalArgumentException("Matrices cannot be multiplied: incompatible dimensions");
        }

        if (threadCount <= 0) {
            throw new IllegalArgumentException("Matrices cannot be multiplied: thread count must be positive");
        }

        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.threadCount = threadCount;
        this.matrixC = new Matrix(this.matrixA.getRowCount(), this.matrixB.getColumnCount());
        this.rowsPerThread = (int) Math.ceil((double) matrixA.getRowCount() / threadCount);
    }
    @Override
    public Matrix multiply() {
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            int threadNumber = i;
            threads.add(new Thread(() -> processThread(threadNumber)));
        }

        for (Thread t : threads) {
            t.start();
        }

        try {
            for (Thread t : threads) {
                t.join();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        return matrixC;
    }

    private void processThread(int threadNumber) {
        for (int i = threadNumber * rowsPerThread; i < (threadNumber + 1) * rowsPerThread && i < matrixA.getRowCount(); i++) {
            for (int j = 0; j < matrixB.getColumnCount(); j++) {
                for (int k = 0; k < matrixB.getRowCount(); k++) {
                    matrixC.setElement(i, j,
                            matrixC.getElement(i, j) + matrixA.getElement(i, k) * matrixB.getElement(k, j));
                }
            }
        }
    }
}
