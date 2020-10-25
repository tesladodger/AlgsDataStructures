package com.tesladodger.dodgerlib.math;


public class Vector2D {

    /** Coordinates */
    private double x;
    private double y;

    /** Magnitude of the vector */
    private double mag;

    /** Angle of the vector */
    private double angle;

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

    /**
     * Add another vector to this vector.
     *
     * @param b other vector;
     */
    public void add (Vector2D b) {
        x += b.x;
        y += b.y;
        calculateEuclideanComponents();
    }

    /**
     * Add two vectors and return a new one.
     *
     * @param a first vector;
     * @param b second vector;
     *
     * @return new vector = a + b;
     */
    public static Vector2D add (Vector2D a, Vector2D b) {
        return new Vector2D(a.x + b.x, a.y + b.y);
    }

    /**
     * Cross product this vector with a second.
     *
     * @param b second vector;
     */
    public void cross (Vector2D b) {
        x *= b.x;
        y *= b.y;
        calculateEuclideanComponents();
    }

    /**
     * Cross product between two vectors.
     *
     * @param a first vector;
     * @param b second vector;
     *
     * @return new vector = a x b;
     */
    public static Vector2D cross (Vector2D a, Vector2D b) {
        return new Vector2D(a.x * b.x, a.y * b.y);
    }

    /**
     * Calculate the dot product between this vector and another.
     *
     * @param b second vector;
     *
     * @return dot product;
     */
    public double dot (Vector2D b) {
        return x * b.x + y * b.y;
    }

    /**
     * Calculate the dot product between two vectors.
     *
     * @param a first vector;
     * @param b second vector;
     *
     * @return dot product;
     */
    public static double dot (Vector2D a, Vector2D b) {
        return a.x * b.x + a.y * b.y;
    }

    /**
     * Return the distance between this vector and another.
     *
     * @param b second vector;
     *
     * @return distance between vectors;
     */
    public double dist (Vector2D b) {
        return Math.sqrt( (x - b.x) * (x - b.x) + (y - b.y) * (y - b.y) );
    }

    /**
     * Return the angle between this vector and another.
     *
     * @param b second vector;
     *
     * @return angle;
     */
    public double angleBetween (Vector2D b) {
        return b.angle - angle;
    }

    /**
     * Returns the angle between two vectors.
     *
     * @param a first vector;
     * @param b second vector;
     *
     * @return angle;
     */
    public static double angleBetween (Vector2D a, Vector2D b) {
        return b.angle - a.angle;
    }

    /**
     * Rotates the vector by a given angle.
     *
     * @param angle to rotate;
     */
    public void rotate (double angle) {
        this.angle += angle;
        // Angle between 0 and 2PI
        this.angle = ((this.angle / (2 * Math.PI)) % 1) * (2 * Math.PI);
        calculateCartesianComponents();
    }

    /**
     * Normalize to a unit vector.
     */
    public void normalize () {
        mag = 1;
        calculateCartesianComponents();
    }

    /**
     * Normalize to a value.
     *
     * @param value new magnitude;
     */
    public void normalize (int value) {
        mag = value;
        calculateCartesianComponents();
    }

    /**
     * Calculate the magnitude and direction.
     */
    private void calculateEuclideanComponents () {
        mag = Math.sqrt(x*x + y*y);
        if (x == 0) {
            if (y > 0)
                angle = Math.PI / 2;
            else if (y < 0)
                angle = - Math.PI / 2;
            else
                angle = 0;
        } else {
            double a = y / x;
            angle = Math.atan(a);
        }
    }

    /**
     * Calculate the x and y components.
     */
    private void calculateCartesianComponents () {
        x = mag * Math.cos(angle);
        y = mag * Math.sin(angle);
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
        return angle;
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
        this.angle = dir;
        calculateCartesianComponents();
    }

}
