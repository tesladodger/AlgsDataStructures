package com.tesladodger.dodgerlib.algs.search;

import com.tesladodger.dodgerlib.structures.HashTable;

import java.util.ArrayList;


/**
 * Solves the Traveling Salesman Problem by checking every possibility (brute force).
 * This assumes every node is connected to every other node in the graph.
 * The returned solution does not contain the start/end node.
 *
 * @param <T>
 */
public class BruteForceTSP<T extends Comparable<T>> {

    /**
     * Each node is a location in the TSP.
     */
    private class Node {
        /** ID for lookup in the Hash Table */
        T ID;

        /** Table to map the IDs of this node's neighbors to their distance to this node */
        HashTable<T, Double> edges;

        Node (T ID, HashTable<T, Double> edges) {
            this.ID = ID;
            this.edges = edges;
        }
    }

    /** Undirected graph. Maps the IDs to the nodes. */
    private final HashTable<T, Node> graph;

    /** Ordered list of IDs, representing the current path. */
    private final ArrayList<T> list;

    /** Start and end of the TSP. */
    private Node start;

    /**
     * Constructor.
     */
    public BruteForceTSP () {
        graph = new HashTable<>();
        list = new ArrayList<>();
        start = null;
    }

    /**
     * Creates the start and end node of the TSP.
     *
     * @param id of the start;
     * @param edges of the start;
     */
    public void addStart (T id, HashTable<T, Double> edges) {
        start = new Node(id, edges);
    }

    /**
     * Adds a new node to the graph and the list. The list insertion maintains a sorted state.
     *
     * @param id of the new node;
     * @param edges of the new node;
     */
    public void addNode (T id, HashTable<T, Double> edges) {
        if (id == null || edges == null) throw new NullPointerException();
        graph.put(id, new Node(id, edges));
        if (list.isEmpty()) {
            list.add(id);
            return;
        }

        // Find the first element larger than the new id.
        int newIndex = 0;
        for (T currentID : list) {
            if (currentID.compareTo(id) < 0) {
                newIndex++;
            }
            else break;
        }

        // If there's no such element, just add the new id.
        if (newIndex == list.size()){
            list.add(id);
            return;
        }

        // Insert the new element to the correct spot and shift every element after.
        T temp = list.get(newIndex);
        T next;
        list.set(newIndex, id);
        newIndex++;
        for (int i = newIndex; i < list.size(); i++) {
            next = list.get(i);
            list.set(i, temp);
            temp = next;
        }
        list.add(temp);
    }

    /**
     * Changes the list to the next permutation in its lexicographic order.
     * https://www.quora.com/How-would-you-explain-an-algorithm-that-generates-permutations-using-lexicographic-ordering
     */
    private void nextPermutation () {
        // Find largest x such that P[x]<P[x+1].
        int x = -1;
        for (int i = 0; i < list.size()-1; i++) {
            if (list.get(i).compareTo(list.get(i+1)) < 0) {
                x = i;
            }
        }

        // Find the largest y such that P[x]<P[y].
        int y = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(x).compareTo(list.get(i)) < 0) {
                y = i;
            }
        }

        // Swap P[x] and P[y].
        T temp = list.get(x);
        list.set(x, list.get(y));
        list.set(y, temp);

        // Reverse P[x+1 .. n].
        int i = x+1;
        int j = list.size()-1;
        while (i < j) {
            T t = list.get(i);
            list.set(i, list.get(j));
            list.set(j, t);
            i++; j--;
        }
    }

    /**
     * Calculates the total distance of the current permutation.
     *
     * @return distance;
     */
    private double calculateDistance () {
        //T current = start.ID;
        double distance = start.edges.get(list.get(0));
        T current = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            // I know this is disgusting, let me explain:
            //   - Check the graph for the current node;
            //   - list.get(i) is the id of the next node;
            //   - In the edges list of the current node, find that id;
            //   - Add the distance to that node;
            //   - Update the current node id;
            Node c = graph.get(current);
            distance += c.edges.get(list.get(i));
            current = list.get(i);
        }
        // Add the distance from the last node to the start.
        distance += graph.get(current).edges.get(start.ID);
        return distance;
    }

    /**
     * Main method. Checks which permutation has the smallest distance and returns the solution.
     *
     * @return list with the nodes that constitute the best path, not including the start node;
     */
    public ArrayList<T> solve () {
        if (start == null) throw new NullPointerException("Start node cannot be null.");

        // Number of permutations is the factorial of the size of the list.
        long totalPerms = 1;
        for (int i = 2; i <= list.size(); i++)
            totalPerms *= i;

        ArrayList<T> solution = new ArrayList<>(list);
        double shortestDistance = calculateDistance();
        // Find the permutation with the shortest distance.
        while (totalPerms > 1) {
            nextPermutation();
            double currentDistance = calculateDistance();
            if (currentDistance < shortestDistance) {
                shortestDistance = currentDistance;
                solution = new ArrayList<>(list);
            }
            totalPerms--;
        }

        return solution;
    }

}
