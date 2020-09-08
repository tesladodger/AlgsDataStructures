package com.tesladodger.dodgerlib.algs.search;

import com.tesladodger.dodgerlib.structures.PairingHeap;
import com.tesladodger.dodgerlib.structures.Stack;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


/**
 * Contains the information of the nodes.
 */
class ASNode {
    final int x;
    final int y;

    int f;  // g + h
    int g;  // Distance from start.
    int h;  // Heuristic distance to goal.

    boolean isObstacle;
    boolean isGoal;

    List<ASNode> neighbors = new LinkedList<>();
    ASNode cameFrom;

    /**
     * Constructor.
     * @param x position;
     * @param y position;
     * @param inf = width * height;
     */
    ASNode (int x, int y, int inf) {
        this.x = x;
        this.y = y;
        f = inf;
        g = inf;
        h = 0;
    }
}


/**
 * Applies the A* algorithm to a square grid.
 * call constructor -> addGoals() -> addStart() -> addObstacles() -> algorithm()
 */
public class AStar {
    private int width;
    private int height;
    private int inf;

    private ASNode[][] grid;
    private Stack<Integer[]> path = new Stack<>();

    private ASNode start;
    // goals is a n*2 matrix. Each row has the x and y of a goal.
    private int[][] goals;

    /**
     * Constructor.
     * @param width of the grid;
     * @param height of the grid;
     */
    public AStar (int width, int height) {
        this.width = width;
        this.height = height;
        inf = height * width;
        grid = new ASNode[width][height];
    }


    /**
     * Populates the grid and defines the goals.
     * @param goals array with the IDs of the goals.
     */
    public final void addGoals (int[][] goals) {
        if (goals == null) throw new NullPointerException("Goals array is null");

        // Populate the grid.
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                ASNode node = new ASNode(i, j, inf);
                grid[i][j] = node;
            }
        }

        this.goals = goals;
        for (int[] goal : goals) {
            grid[goal[0]][goal[1]].isGoal = true;
        }
    }

    /**
     * Define the start node.
     * @param start node coordinates;
     */
    public final void addStart (int[] start) {
        this.start = grid[start[0]][start[1]];
    }

    /**
     * Define the obstacles.
     * @param obstacles n*2 array with the coordinates of the obstacles;
     */
    public final void addObstacles (int[][] obstacles) {
        for (int[] obstacle : obstacles) {
            grid[obstacle[0]][obstacle[1]].isObstacle = true;
        }
    }

    /**
     * Adds the neighbors to the list of a node.
     * @param node to add neighbors to;
     */
    private void addNeighbors (ASNode node) {
        int x = node.x; int y = node.y;
        if (x < width - 1) {
            grid[x][y].neighbors.add(grid[x+1][y]);  // Right
        }
        if (x > 0) {
            grid[x][y].neighbors.add(grid[x-1][y]);  // Left
        }
        if (y < height - 1) {
            grid[x][y].neighbors.add(grid[x][y+1]);  // Top
        }
        if (y > 0) {
            grid[x][y].neighbors.add(grid[x][y-1]);  // Bottom
        }
    }

    /**
     * Calculates the Manhattan distance.
     * @param x1 coordinate;
     * @param x2 coordinate;
     * @param y1 coordinate;
     * @param y2 coordinate;
     * @return distance;
     */
    private int manhattanDist(int x1, int x2, int y1, int y2) {
        return Math.abs(x2 - x1) + Math.abs(y2 - y1);
    }

    /**
     * Calculates the closest distance to a goal for a given node, the heuristic of that node.
     * @param node to evaluate;
     * @return best heuristic for that node;
     */
    private int calculateHeuristic (ASNode node) {
        int heuristic = width * height;
        for (int[] goal : goals) {
            int tempHeu = manhattanDist(goal[0], node.x, goal[1], node.y);
            if (tempHeu < heuristic) {
                heuristic = tempHeu;
            }
        }
        return heuristic;
    }

    /**
     * Method that goes through the cameFrom nodes and reconstructs the path. Since I use a stack,
     * the path is returned in order.
     * @param current the last node to be evaluated, which is the goal node;
     * @return a stack of integer arrays, each array has the coordinates of a node. The coordinates
     *      form the path from the start to the goal;
     */
    private Stack<Integer[]> reconstructPath (ASNode current) {
        path.clear();

        ASNode temp = current;
        path.push(new Integer[] {current.x, current.y});
        while (temp.cameFrom != null) {
            path.push(new Integer[] {temp.cameFrom.x, temp.cameFrom.y});
            temp = temp.cameFrom;
        }

        return path;
    }

    /**
     * Main algorithm.
     * @return stack of coordinates;
     */
    public Stack<Integer[]> algorithm () {

        // Purely heuristic value for the first node.
        start.g = 0;
        start.f = calculateHeuristic(start);

        // Open and closed sets.
        // The key of the pairing heap is the f score of the node. Lowest f is higher priority.
        PairingHeap<Integer, ASNode> openSet = new PairingHeap<>(PairingHeap.Type.MIN);
        HashSet<ASNode> closedSet = new HashSet<>();

        // Add the start node to the openSet.
        openSet.insert(start.f, start);

        // Algorithm loop.
        while (!openSet.isEmpty()) {

            // Pop node with best score.
            ASNode current = openSet.pop();

            if (current.isGoal) {
                // Done, let's recreate the path.
                return reconstructPath(current);
            }

            // Add current to the closedSet.
            closedSet.add(current);

            // Set the neighbors of current.
            addNeighbors(current);

            // For every neighbor of current:
            for (ASNode neighbor : current.neighbors) {

                // Ignore the neighbors in the closedSet and the obstacles.
                if (closedSet.contains(neighbor) || neighbor.isObstacle) {
                    continue;
                }

                // The cost of a move in the grid is always 1.
                int tentativeGScore = current.g + 1;

                // This path is equal or worse, ignore it.
                if (tentativeGScore >= neighbor.g) {
                    continue;
                }

                // If it gets here, this is the best path for this node until now.
                neighbor.cameFrom = current;
                neighbor.g = tentativeGScore;
                neighbor.h = calculateHeuristic(neighbor);
                neighbor.f = neighbor.g + neighbor.h;

                // Insert it in the opesSet anyway because I don't know how to decrease key and it
                // doesn't really matter.
                openSet.insert(neighbor.f, neighbor);

            }
        } // End of the A* loop.

        // If it gets here, there's no solution...
        return null;
    } // End of the algorithm method.

}
