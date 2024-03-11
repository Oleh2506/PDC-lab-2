package matrixmultiplier;

import main.Matrix;

public class DefaultMultiplier implements MatrixMultiplier {
    private final Matrix matrixA;
    private final Matrix matrixB;
    private final Matrix matrixC;

    public DefaultMultiplier(Matrix matrixA, Matrix matrixB) {
        if (matrixA.getColumnCount() != matrixB.getRowCount()) {
            throw new IllegalArgumentException("Matrices cannot be multiplied: incompatible dimensions");
        }

        this.matrixA = matrixA;
        this.matrixB = matrixB;

        this.matrixC = new Matrix(matrixA.getRowCount(), matrixB.getColumnCount());
    }

    @Override
    public Matrix multiply() {
        for (int i = 0; i < matrixA.getRowCount(); i++) {
            for (int j = 0; j < matrixB.getColumnCount(); j++) {
                for (int k = 0; k < matrixA.getColumnCount(); k++) {
                    matrixC.setElement(i, j,
                            matrixC.getElement(i, j) + matrixA.getElement(i, k) * matrixB.getElement(k, j));
                }
            }
        }

        return matrixC;
    }
}
