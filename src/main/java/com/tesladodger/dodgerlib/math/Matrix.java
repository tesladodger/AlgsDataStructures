package com.tesladodger.dodgerlib.math;

// todo: clean this, make it all static

public class Matrix {
    /* Number of rows */
    private int m;
    /* Number of columns */
    private int n;
    /* Actual matrix */
    private double[][] matrix;
    /* LU decomposition of the matrix */
    private double[][][] LU;

    /**
     * Constructor.
     * @param matrix 2D array of doubles;
     */
    public Matrix (double[][] matrix) {
        setMatrix(matrix);
    }


    /**
     * Verifies the array input, sets the matrix dimensions and the internal matrix array.
     * @param matrix 2d array;
     */
    public void setMatrix (double[][] matrix) {
        m = matrix.length;
        n = matrix[0].length;

        for (double[] row : matrix) {
            if (row.length != n) throw new RuntimeException("Row length must be constant.");
        }

        this.matrix = matrix;
        // If LU has been calculated before and a change has been made to the matrix, calculate it
        // again.
        if (LU != null) decompose(this);
    }

    /**
     * Returns the result of matrix multiplication between A and B.
     * @param A matrix;
     * @param B matrix;
     * @return result;
     */
    public static Matrix multiplication (Matrix A, Matrix B) {
        // Check if the number of columns in the first equals the number of rows in the second.
        if (A.n != B.m) {
            throw new RuntimeException("Number of columns in the first matrix must equal the" +
                    " number of rows in the second");
        }

        double[][] c = new double[A.m][B.n];

        for (int i = 0; i < A.m; i++) {
            for (int j = 0; j < B.n; j++) {
                c[i][j] = 0f;
                for (int k = 0; k < A.n; k++) {
                    c[i][j] += A.matrix[i][k] * B.matrix[k][j];
                }
            }
        }
        return new Matrix(c);
    }

    /**
     * Multiply a matrix by a scalar.
     * @param A matrix;
     * @param lambda scalar;
     * @return new matrix;
     */
    public static Matrix scalarMultiplication (Matrix A, double lambda) {
        double[][] b = new double[A.m][A.n];
        for (int i = 0; i < A.m; i++) {
            for (int j = 0; j < A.n; j++) {
                b[i][j] = A.matrix[i][j] * lambda;
            }
        }
        return new Matrix(b);
    }

    /**
     * Multiply the elements of this matrix by a scalar.
     * @param lambda scalar;
     */
    public void scalarMultiplication (double lambda) {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] *= lambda;
            }
        }
    }

    /**
     * Adds two matrices.
     * @param A matrix;
     * @param B matrix;
     * @return A + B;
     */
    public static Matrix addition (Matrix A, Matrix B) {
        if (A.m != B.m || A.n != B.n) {
            throw new RuntimeException("Matrix addition requires matrices of the same size.");
        }

        double[][] c = new double[A.m][A.n];

        for (int i = 0; i < A.m; i++) {
            for (int j = 0; j < A.n; j++) {
                c[i][j] = A.matrix[i][j] + B.matrix[i][j];
            }
        }
        return new Matrix(c);
    }

    /**
     * Transpose a matrix.
     * @param A matrix;
     * @return At;
     */
    public static Matrix transpose (Matrix A) {
        double[][] T = new double[A.n][A.m];
        for (int i = 0; i < A.n; i++) {
            for (int j = 0; j < A.m; j++) {
                T[i][j] = A.matrix[j][i];
            }
        }
        return new Matrix(T);
    }

    /**
     * Replaces the internal matrix with its transpose.
     */
    public void transpose () {
        matrix = transpose(this).matrix;
    }

    // todo
    //public static Matrix invert (Matrix a) {
    //}

    public double trace () {
        if (m != n) {
            throw new RuntimeException("Trace is only defined for square matrices.");
        }
        double sum = 0;
        for (int i = 0; i < m; i++) {
            sum += matrix[i][i];
        }
        return sum;
    }

    /**
     * Uses the LU factorization to calculate the determinant.
     * @return determinant;
     */
    public double determinant () {
        if (m != n) {
            throw new RuntimeException("Determinant is only defined for square matrices.");
        }
        // 1*1
        if (m == 1) {
            return matrix[0][0];
        }
        // 2*2
        else if (m == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }
        // n*n
        else {
            if (LU == null) decompose(this);
            double prod = 1;
            for (int i = 0; i < m; i++) {
                prod *= LU[1][i][i];
            }
            return prod;
        }
    }

    /**
     * Decomposes a matrix in LU, using the doolittle method.
     * @param A matrix;
     * @return double[][][] with the L and U matrices;
     */
    public static double[][][] decompose (Matrix A) {
        if (A.m != A.n) {
            throw new RuntimeException("Decomposition is only defined for square matrices.");
        }

        // Default value of int is 0.
        int n = A.m;
        double[][] L = new double[n][n];
        double[][] U = new double[n][n];
        double[][][] R = new double[2][n][n];

        // Doolittle's algorithm for LU decomposition.
        for (int k = 0; k < n; k++) {

            L[k][k] = 1;

            // Upper.
            for (int m = k; m < n; m++) {
                double sum = 0;
                for (int j = 0; j < k; j++) {
                    sum += (L[k][j] * U[j][m]);
                }
                U[k][m] = A.matrix[k][m] - sum;
            }

            // Lower.
            for (int i = k + 1; i < n; i++) {
                double sum = 0;
                for (int j = 0; j < k; j++) {
                    sum += (L[i][j] * U[j][k]);
                }
                L[i][k] = (A.matrix[i][k] - sum) / U[k][k];
            }
        }
        R[0] = L; R[1] = U;
        A.LU = R;
        return R;
    }

    public double[][][] decompose () {
        if (LU == null) decompose(this);
        return LU;
    }

    /**
     * Prints a matrix.
     * @param A matrix;
     */
    public static void print (Matrix A) {
        System.out.println("Matrix " + A + " :");
        for (int i = 0; i < A.m; i++) {
            for (int j = 0; j < A.n; j++) {
                System.out.printf("%-20s | ", (A.matrix[i][j]));
            }
            System.out.print("\n");
        }
    }

    public void print () {
        print(this);
    }

}
