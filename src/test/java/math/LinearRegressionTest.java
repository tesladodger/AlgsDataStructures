package math;

import com.tesladodger.dodgerlib.math.LinearRegression;
import com.tesladodger.dodgerlib.math.Vector2D;

import java.util.ArrayList;
import java.util.List;


public class LinearRegressionTest {

    public static void main (String[] args) {

        List<Vector2D> list = new ArrayList<>();

        list.add(new Vector2D(-5, -4.5));
        list.add(new Vector2D(3, 7.5));
        list.add(new Vector2D(1, 4.5));
        list.add(new Vector2D(-2, 0));

        double[] func = LinearRegression.function(list);
        System.out.println(func[0] + "x + " + func[1]);

    }

}
