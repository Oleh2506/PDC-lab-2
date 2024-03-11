package main;

public class Matrix {
    private final int[][] matrix;

    public Matrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public Matrix(int rowNum, int colNum) { this.matrix = new int [rowNum][colNum]; }

    public int getRowCount() { return this.matrix.length; }

    public int getColumnCount() { return this.matrix[0].length; }

    public int getElement(int i, int j) { return this.matrix[i][j]; }

    public void setElement(int i, int j, int value) { this.matrix[i][j] = value; }

    public void print() {
        for (int[] row : this.matrix) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }

    boolean isEqual(Matrix m) {
        if (m.getColumnCount() != this.getColumnCount() || m.getRowCount() != this.getRowCount()) {
            return false;
        }

        for (int i = 0; i < m.getRowCount(); i++) {
            for (int j = 0; j < m.getColumnCount(); j++) {
                if (m.getElement(i, j) != this.getElement(i, j)) {
                    return false;
                }
            }
        }

        return true;
    }
}
