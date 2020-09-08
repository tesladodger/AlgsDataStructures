package math;

import com.tesladodger.dodgerlib.math.Vector2D;


public class Vector2DTest {

    public static void main (String[] args) {

        // todo better tests
        Vector2D a = new Vector2D(10, 20);
        Vector2D b = new Vector2D(60, 80);

        System.out.println("\n\n" + Math.toDegrees(a.angleBetween(b)));

        Vector2D c = Vector2D.add(a, b);
        b.add(a);

        System.out.println(c.getX() + " " + c.getY());
        System.out.println(b.getX() + " " + b.getY());

        c = Vector2D.normalize(a, 5);
        a.normalize(5);

        System.out.println("a " + a.getX() + " " + a.getY());
        System.out.println("c " + c.getX() + " " + c.getY());
        System.out.println(a.getMagnitude() + " " + c.getMagnitude());

        c.rotate(2 * Math.PI);

        System.out.println(c.getDirection());
    }

}
