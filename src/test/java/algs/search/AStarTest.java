package algs.search;

import com.tesladodger.dodgerlib.algs.search.AStar;
import com.tesladodger.dodgerlib.structures.Stack;


public class AStarTest {

    public static void main (String[] args) {

        AStar aStar = new AStar(5, 5);

        int[][] goals = {{4, 4}};
        aStar.addGoals(goals);

        aStar.addStart(new int[] {0,0});

        int[][] obstacles = {{1,0}, {1,1}, {1, 4}, {2, 3}, {3, 2}, {3, 1}};
        aStar.addObstacles(obstacles);

        /*
          4 |   | / |   |   | G |   Dots are the path, slashes are obstacles.
          3 |   |   | / |   | . |
          2 | . | . | . | / | . |
          1 | . | / | . | / | . |
          0 | S | / | . | . | . |
              0   1   2   3   4
         */

        Stack<Integer[]> path = aStar.algorithm();

        while (!path.isEmpty()) {
            Integer[] current = path.pop();
            System.out.println("x " + current[0] + "  y " + current[1]);
        }

    }

}
