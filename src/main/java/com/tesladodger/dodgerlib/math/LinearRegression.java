package com.tesladodger.dodgerlib.math;

import java.util.List;


public class LinearRegression {

    /**
     * Suppress constructor.
     */
    private LinearRegression () {}

    /**
     * Calculates the linear regression of a list of vectors.
     *
     * @param points list of 2D vectors;
     *
     * @return an array with the 'm' value at index 0 and b at index 1;
     */
    public static double[] function (List<Vector2D> points) {
        /*
         *      sum ( (X - aveX) * (Y - aveY) )
         *  m = ----------------------------
         *         sum ( (X - aveX) ^ 2 )
         *
         *  b = aveY - m * aveX
         */

        // Calculate the averages.
        double sumX = 0;
        double sumY = 0;
        for (Vector2D point : points) {
            sumX += point.getX();
            sumY += point.getY();
        }
        double aveX = sumX / points.size();
        double aveY = sumY / points.size();

        // Calculate the sums.
        double numerator = 0;
        double denominator = 0;
        for (Vector2D point : points) {
            numerator += (point.getX() - aveX) * (point.getY() - aveY);
            denominator += (point.getX() - aveX) * (point.getX() - aveX);
        }

        double m = numerator / denominator;
        double b = aveY - m * aveX;

        return new double[] {m, b};
    }

}
