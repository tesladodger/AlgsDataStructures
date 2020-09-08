package math;

import com.tesladodger.dodgerlib.math.Matrix;


public class MatrixTest {

    public static void main (String[] args) {

        // Determinant //
        double[][] a = {
                {1, 0, 9, 5, 8, 13, 9, 8, 10, 23},
                {3, 5, 3, 8, 7, 1, 3, 2, 1, 12},
                {7, 9, 1, 12, 4, 9, 0, 2, 12, 9},
                {4, 0, 3, 7, 9, 3, 4, 8, 5, 9},
                {8, 6, 4, 9, 7, 7, 8, 9, 12, 21},

                {6, 7, 8, 9, 4, 9, 8, 7, 3, 5},
                {8, 7, 6, 9, 8, 5, 6, 7, 8, 7},
                {1, 7, 8, 4, 0, 9, 1, 2, 6, 5},
                {7, 6, 8, 9, 7, 4, 6, 8, 1, 2},
                {9, 4, 8, 1, 9, 3, 7, 3, 5, 8}
        };
        Matrix A = new Matrix(a);

        System.out.println("Big determinant: " + A.determinant());

        double[][] M = {
                {1, 3},
                {4, 2}
        };
        System.out.println("Small determinant: " + (new Matrix(M).determinant()));


        // Decomposition //
        System.out.println("\nDecomposition \nM:");
        Matrix.print(new Matrix(M));
        System.out.println("L");
        Matrix.print(new Matrix(Matrix.decompose(new Matrix(M))[0]));
        System.out.println("U");
        Matrix.print(new Matrix(Matrix.decompose(new Matrix(M))[1]));


        // Multiplication //
        A.setMatrix(new double[][]{
                {1, 3, 1},
                {8, 4, 6},
                {0, 3, 5}
        });

        double[][] b = {
                {4, 8, 0},
                {1, 2, 4},
                {7, 1, 5}
        };
        Matrix B = new Matrix(b);

        System.out.println("\nMultiplication");
        Matrix C = Matrix.multiplication(A, B);
        C.print();


        // Transpose //
        System.out.println("\nTranspose");
        Matrix.print(C);
        Matrix.print(Matrix.transpose(C));
        C.transpose();
        Matrix.print(C);


        // Add //
        System.out.println("\nAdd");
        Matrix.print(Matrix.addition(A, B));


        // Scalar multiplication //
        System.out.println("\nScalar multiplication");
        double[][] D = {{0, 1, 2, 3},{4, 5, 6, 7}};
        Matrix.print(Matrix.scalarMultiplication(new Matrix(D), 5));

        // Trace //
        System.out.println("\nTrace");
        System.out.println(A.trace());

    }

}
