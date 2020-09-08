package com.tesladodger.dodgerlib.algs.search;

import com.tesladodger.dodgerlib.structures.HashTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * Solves the Traveling Salesman Problem using a genetic algorithm.
 * The population is a list of permutations.
 *
 * @param <T>
 */
public class GeneticAlgTSP<T extends Comparable<T>> {

    /* Number of elements in a generation. */
    private static final int populationSize = 50;

    /* Probability of random mutation. */
    private static final double mutationRate = 0.4d;

    /* Array with the ordered IDs */
    private List[] population = new ArrayList[populationSize];

    /* Contains the fitness of the element in the population with the same index. */
    private Double[] fitness = new Double[populationSize];

    /* Counter for number of generations. */
    private int generation;

    /* Random generator used for selection and mutation */
    private Random ran;

    /**
     * Contains the information of a node.
     */
    private class Node {
        /* ID for lookup in the Hash Table */
        T ID;
        /* Table to map the IDs of this node's neighbors to their distance to this node */
        HashTable<T, Double> edges;

        Node (T ID, HashTable<T, Double> edges) {
            this.ID = ID;
            this.edges = edges;
        }
    }

    /* Undirected graph. Maps the IDs to the nodes for lookup. */
    private HashTable<T, Node> graph;

    /* Initial list of nodes. */
    private List<T> list;

    /* Start and end of the TSP. */
    private Node start;

    /* Shortest distance at a given point in the algorithm. */
    private Double currentBest;

    /**
     * Constructor.
     */
    public GeneticAlgTSP () {
        graph = new HashTable<>();
        list = new ArrayList<>();
        start = null;
        currentBest = Double.POSITIVE_INFINITY;
        generation = 0;
        ran = new Random();
    }

    /**
     * Creates the start and end node of the TSP.
     * @param id of the start;
     * @param edges of the start;
     */
    public void addStart (T id, HashTable<T, Double> edges) {
        if (id == null) throw new NullPointerException("Start id cannot be null.");
        start = new Node(id, edges);
    }

    /**
     * Adds a new node to the graph and the list.
     * @param id of the new node;
     * @param edges of the new node;
     */
    public void addNode (T id, HashTable<T, Double> edges) {
        if (id == null || edges == null) throw new NullPointerException("A node cannot have " +
                "null values.");
        graph.put(id, new Node(id, edges));
        list.add(id);
    }

    /**
     * Calculates the total distance of a permutation.
     * @return distance;
     */
    private double calculateDistance (List<T> permutation) {
        //T current = start.ID;
        double distance = start.edges.get(permutation.get(0));
        T current = permutation.get(0);
        for (int i = 1; i < permutation.size(); i++) {
            // I know this is disgusting, let me explain:
            //   - Check the graph for the current node;
            //   - permutation.get(i) is the id of the next node;
            //   - In the edges permutation of the current node, find that id;
            //   - Add the distance to that node;
            //   - Update the current node id;
            Node c = graph.get(current);
            distance += c.edges.get(permutation.get(i));
            current = permutation.get(i);
        }
        // Add the distance from the last node to the start.
        distance += graph.get(current).edges.get(start.ID);
        return distance;
    }

    /**
     * Normalizes the fitness values to a percentage (0 - 1).
     */
    private void normalizeFitness () {
        double sum = 0;
        for (Double d : fitness) {
            sum += d;
        }
        for (int i = 0; i < populationSize; i++) {
            fitness[i] /= sum;
        }
    }

    /**
     * Picks an element given the probability distribution.
     * @return index of the picked element;
     */
    private int randomElement () {
        int i = 0;
        double r = ran.nextDouble();
        while (r > 0) {
            r -= fitness[i];
            i++;
        }
        return --i;
    }

    /**
     * Randomly swaps two neighbors, with a probability given by the mutation rate.
     * @param current permutation being mutated;
     * @return mutated permutation;
     */
    private List<T> mutate (List<T> current) {
        if (ran.nextDouble() <= mutationRate) {
            int i = ran.nextInt(current.size());
            int j = (i + 1) % current.size();
            T temp = current.get(i);
            current.set(i, current.get(j));
            current.set(j, temp);
        }
        return current;
    }

    /**
     * Takes two lists and applies crossover.
     * @param a list;
     * @param b list;
     * @return list formed from the inputs;
     */
    private List<T> crossover (List<T> a, List<T> b) {
        List<T> child = new ArrayList<>();
        // Add the first half of 'a' to the child.
        for (int i = 0; i < a.size() / 2; i++) {
            child.add(a.get(i));
        }
        // Add the elements from 'b' that are not already in the child.
        for (T elem : b) {
            if (!child.contains(elem)) {
                child.add(elem);
            }
        }
        return child;
    }

    /**
     * Main method. Creates the first generation randomly and loops the genetic algorithm.
     * @param solution pre-calculated solution;
     * @return list with the nodes that constitute the best path, not including the start node;
     */
    public List<T> solve (List<T> solution) {

        // Create the population.
        for (int i = 0; i < populationSize; i++) {
            Collections.shuffle(list);
            population[i] = new ArrayList<>(list);
        }

        while (true) {
            generation++;

            // Rate the generation's fitness and find the current best value.
            for (int i = 0; i < populationSize; i++) {
                // noinspection unchecked
                double currentDist = calculateDistance(population[i]);
                if (currentDist < currentBest) {
                    currentBest = currentDist;

                    // Check if we found the solution.
                    boolean solutionFound = true;
                    for (int j = 0; j < solution.size(); j++) {
                        if (!population[i].get(j).equals(solution.get(j))) {
                            solutionFound = false;
                            break;
                        }
                    }
                    if (!solutionFound) {
                        solutionFound = true;
                        int k = 0;
                        for (int j = solution.size() - 1; j >= 0; j--) {
                            if (!population[i].get(k).equals(solution.get(j))) {
                                solutionFound = false;
                                break;
                            }
                            k++;
                        }
                    }

                    if (solutionFound) {
                        System.out.println("\nSolution found. \nNumber of generations: " + generation);
                        // noinspection unchecked
                        return population[i];
                    }
                }
                fitness[i] = 1 / currentDist;
            }

            normalizeFitness();

            // Create the next generation.
            List[] nextGeneration = new List[populationSize];
            for (int i = 0; i < populationSize; i++) {
                // Select two 'random' elements, apply crossover, mutate the result and insert it
                // in the new generation.
                // noinspection unchecked
                nextGeneration[i] = mutate(crossover(
                        population[randomElement()],
                        population[randomElement()]
                        ));
            }
            population = nextGeneration;
        } // End of the while loop.
    } // End of the solve method.

}
