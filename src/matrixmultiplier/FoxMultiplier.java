package matrixmultiplier;

import main.Matrix;

import java.util.ArrayList;

public class FoxMultiplier implements MatrixMultiplier {
    private final Matrix matrixA;
    private final Matrix matrixB;
    private final Matrix matrixC;
    private final int q;
    private final int m;

    public FoxMultiplier(Matrix matrixA, Matrix matrixB, int threadCount) {
        if (matrixA.getColumnCount() != matrixB.getRowCount()) {
            throw new IllegalArgumentException("Matrices cannot be multiplied: incompatible dimensions");
        }

        if (matrixA.getColumnCount() != matrixA.getRowCount() ||
                matrixB.getColumnCount() != matrixB.getRowCount()) {
            throw new IllegalArgumentException("Matrices cannot be multiplied: matrices must be square");
        }

        if (threadCount <= 0) {
            throw new IllegalArgumentException("Matrices cannot be multiplied: thread count must be positive");
        }

        this.matrixA = matrixA;
        this.matrixB = matrixB;

        int n = matrixA.getRowCount();

        q = (int) Math.sqrt(threadCount);
        if (n % q != 0) {
            throw new IllegalArgumentException("Matrices cannot be multiplied: processes count does not match matrices dimensions");
        }

        m = n / q;

        matrixC = new Matrix(n, n);
    }

    @Override
    public Matrix multiply() {
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < q; i++) {
            for (int j = 0; j < q; j++) {
                int processI = i;
                int processJ = j;

                threads.add(new Thread(() -> {
                    for (int k = 0; k < q; k++) {
                        multiplyBlocks(processI, processJ, (processI + k) % q);
                    }
                }));
            }
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

    private void multiplyBlocks(int pi, int pj, int currK) {
        for (int i = pi * m; i < (pi + 1) * m; i++) {
            for (int j = pj * m; j < (pj + 1) * m; j++) {
                for (int k = currK * m; k < (currK + 1) * m; k++) {
                    matrixC.setElement(i, j,
                            matrixC.getElement(i, j) + matrixA.getElement(i, k) * matrixB.getElement(k, j));
                }
            }
        }
    }
}
