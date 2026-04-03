package org.mdafftfa.branch_and_bound;

import java.util.*;

/**
 * The Graph class implements the Traveling Salesperson Problem (TSP)
 * using the Branch and Bound algorithmic strategy with an improved Bound calculation.
 */
public class Graph {

    /** Total number of vertices (cities) in the graph */
    private final int vCount;

    /** Adjacency matrix representing the edge weights between cities */
    private final int[][] adjMatrix;

    /**
     * Constructor to initialize the graph with 'v' vertices.
     * @param v The number of cities.
     */
    public Graph(int v) {
        this.vCount = v;
        this.adjMatrix = new int[v][v];
    }

    /**
     * Adds an undirected edge between city 'u' and city 'v'.
     * @param u Source city index.
     * @param v Destination city index.
     * @param weight Travel cost.
     */
    public void addEdge(int u, int v, int weight) {
        adjMatrix[u][v] = weight;
        adjMatrix[v][u] = weight;
    }

    /**
     * Inner class representing a node in the State-Space Tree.
     */
    static class Node implements Comparable<Node> {
        int level;
        int currentCity;
        int cost;
        int bound;
        boolean[] visited;

        /**
         * Constructor for a new State-Space Tree Node.
         * @param level Current depth.
         * @param currentCity City index.
         * @param cost Accumulated travel cost.
         * @param visited Array tracking visited cities.
         */
        public Node(int level, int currentCity, int cost, boolean[] visited) {
            this.level = level;
            this.currentCity = currentCity;
            this.cost = cost;
            this.visited = visited.clone();
            this.visited[currentCity] = true;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.bound, other.bound);
        }
    }

    /**
     * Calculates a more accurate Lower Bound.
     * Improvement: It considers the minimum edge to enter each unvisited city
     * AND the minimum edge to return to the start (city 0).
     * @param node The current node to calculate the bound for.
     * @return The calculated lower bound cost.
     */
    private int calculateBound(Node node) {
        int bound = node.cost;

        for (int i = 0; i < vCount; i++) {
            if (!node.visited[i]) {
                int minEdge = Integer.MAX_VALUE;
                for (int j = 0; j < vCount; j++) {
                    if (i != j && adjMatrix[i][j] > 0) {
                        minEdge = Math.min(minEdge, adjMatrix[i][j]);
                    }
                }
                if (minEdge != Integer.MAX_VALUE) {
                    bound += minEdge;
                }
            }
        }

        if (node.level < vCount - 1) {
            int minReturn = Integer.MAX_VALUE;
            for (int i = 0; i < vCount; i++) {
                if (adjMatrix[i][0] > 0) {
                    minReturn = Math.min(minReturn, adjMatrix[i][0]);
                }
            }
            if (minReturn != Integer.MAX_VALUE) {
                bound += minReturn;
            }
        }

        return bound;
    }

    /**
     * Solves the Traveling Salesperson Problem using the Branch and Bound technique.
     */
    public void solveTSP() {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        boolean[] initialVisited = new boolean[vCount];

        Node root = new Node(0, 0, 0, initialVisited);
        root.bound = calculateBound(root);
        pq.add(root);

        int minFinalCost = Integer.MAX_VALUE;

        while (!pq.isEmpty()) {
            Node minNode = pq.poll();

            if (minNode.bound >= minFinalCost) {
                continue;
            }

            if (minNode.level == vCount - 1) {
                if (adjMatrix[minNode.currentCity][0] > 0) {
                    int totalCost = minNode.cost + adjMatrix[minNode.currentCity][0];
                    if (totalCost < minFinalCost) {
                        minFinalCost = totalCost;
                    }
                }
                continue;
            }

            for (int i = 0; i < vCount; i++) {
                if (!minNode.visited[i] && adjMatrix[minNode.currentCity][i] > 0) {
                    Node child = new Node(minNode.level + 1, i,
                            minNode.cost + adjMatrix[minNode.currentCity][i],
                            minNode.visited);

                    child.bound = calculateBound(child);

                    if (child.bound < minFinalCost) {
                        pq.add(child);
                    }
                }
            }
        }

        System.out.println("Minimum TSP route cost: " + minFinalCost);
    }

    /**
     * Main entry point.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        Graph g = new Graph(4);
        g.addEdge(0, 1, 10);
        g.addEdge(0, 2, 15);
        g.addEdge(0, 3, 20);
        g.addEdge(1, 2, 35);
        g.addEdge(1, 3, 25);
        g.addEdge(2, 3, 30);

        g.solveTSP();
    }
}