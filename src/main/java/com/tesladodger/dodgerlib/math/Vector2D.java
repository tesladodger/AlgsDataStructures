package com.tesladodger.dodgerlib.math;


public class Vector2D {
    private double x;
    private double y;
    private double mag;
    private double dir;

    /**
     * Constructor.
     * @param x coordinate;
     * @param y coordinate;
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
        calculateEuclideanComponents();
    }


    /* Operations */

    // ------------------------------------------ Add //
    public void add (Vector2D b) {
        x += b.x;
        y += b.y;
        calculateEuclideanComponents();
    }

    public static Vector2D add (Vector2D a, Vector2D b) {
        return new Vector2D(a.x + b.x, a.y + b.y);
    }

    // ------------------------------------------ Cross //
    public void cross (Vector2D b) {
        x *= b.x;
        y *= b.y;
        calculateEuclideanComponents();
    }

    public static Vector2D cross (Vector2D a, Vector2D b) {
        return new Vector2D(a.x * b.x, a.y * b.y);
    }

    // ------------------------------------------ Dot //
    /**
     * Calculate the dot product.
     * @param b some vector;
     * @return dot product;
     */
    public double dot (Vector2D b) {
        return x * b.x + y * b.y;
    }

    // ------------------------------------------ Distance //
    /**
     * Return the distance between two points.
     * @param b some vector;
     * @return distance from a to b;
     */
    public double dist (Vector2D b) {
        return Math.sqrt( (x - b.x) * (x - b.x) + (y - b.y) * (y - b.y) );
    }

    // ------------------------------------------ Angle //
    public double angleBetween (Vector2D b) {
        return b.dir - dir;
    }

    public static double angleBetween (Vector2D a, Vector2D b) {
        return b.dir - a.dir;
    }

    // ------------------------------------------ Rotate //
    /**
     * Rotates the vector.
     * @param angle to rotate;
     */
    public void rotate (double angle) {
        dir += angle;
        // Angle between 0 and 2PI
        System.out.println( (dir / (2 * Math.PI)) % 1);
        dir = ((dir / (2 * Math.PI)) % 1) * (2 * Math.PI);

        calculateCartesianComponents();
    }

    public static Vector2D rotate (Vector2D a, double angle) {
        a.dir += angle;
        a.calculateCartesianComponents();
        return new Vector2D(a.x, a.y);
    }

    // ------------------------------------------ Normalize //
    /**
     * Normalize to a unit vector.
     */
    public void normalize () {
        mag = 1;
        calculateCartesianComponents();
    }

    public static Vector2D normalize (Vector2D a) {
        a.mag = 1;
        a.calculateCartesianComponents();
        return new Vector2D(a.x, a.y);
    }

    /**
     * Normalize to a value.
     * @param value new magnitude;
     */
    public void normalize (int value) {
        mag = value;
        calculateCartesianComponents();
    }

    public static Vector2D normalize (Vector2D a, int value) {
        a.mag = value;
        a.calculateCartesianComponents();
        return new Vector2D(a.x, a.y);
    }

    // ------------------------------------------ Private methods //
    /**
     * Calculate the magnitude and direction.
     */
    private void calculateEuclideanComponents () {
        mag = Math.sqrt(x*x + y*y);
        if (x == 0) {
            if (y > 0) {
                dir = Math.PI / 2;
            }
            else if (y < 0) {
                dir = - Math.PI / 2;
            }
            else {
                dir = 0;
            }
        }
        else {
            double a = y / x;
            dir = Math.atan(a);
        }
    }

    /**
     * Calculate the x and y components.
     */
    private void calculateCartesianComponents () {
        x = mag * Math.cos(dir);
        y = mag * Math.sin(dir);
    }


    /* Accessors */

    public double getX () {
        return x;
    }

    public double getY () {
        return y;
    }

    public double getMagnitude () {
        return mag;
    }

    public double getDirection () {
        return dir;
    }

    public void setX (double x) {
        this.x = x;
        calculateEuclideanComponents();
    }

    public void setY (double y) {
        this.y = y;
        calculateEuclideanComponents();
    }

    public void setMagnitude (double mag) {
        this.mag = mag;
        calculateCartesianComponents();
    }

    public void setDirection (double dir) {
        this.dir = dir;
        calculateCartesianComponents();
    }

}
