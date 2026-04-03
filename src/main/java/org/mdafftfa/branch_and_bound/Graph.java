package org.mdafftfa.branch_and_bound;

import java.util.*;

/**
 * The Graph class implements the Traveling Salesperson Problem (TSP)
 * using the Branch and Bound algorithmic strategy.
 * This approach uses a state-space tree and a priority queue to find
 * the optimal (minimum cost) tour that visits every city exactly once.
 */
public class Graph {

    /** Total number of vertices (cities) in the graph */
    private final int vCount;

    /** Adjacency matrix representing the edge weights (distances) between cities */
    private final int[][] adjMatrix;

    /**
     * Constructor to initialize the graph with 'v' vertices.
     * @param v The number of cities in the TSP.
     */
    public Graph(int v) {
        this.vCount = v;
        this.adjMatrix = new int[v][v];
    }

    /**
     * Adds an undirected edge between city 'u' and city 'v' with a specific weight.
     * @param u Source city index.
     * @param v Destination city index.
     * @param weight Travel cost between the two cities.
     */
    public void addEdge(int u, int v, int weight) {
        adjMatrix[u][v] = weight;
        adjMatrix[v][u] = weight;
    }

    /**
     * Inner class representing a node in the State-Space Tree.
     * Each node maintains the state of the tour including cost, level, and visited cities.
     */
    static class Node implements Comparable<Node> {
        /** The depth of the node in the state-space tree */
        int level;
        /** The current city where the salesperson is located */
        int currentCity;
        /** The actual accumulated cost from the root to this node */
        int cost;
        /** The lower bound of the total cost for the subtree rooted at this node */
        int bound;
        /** Boolean array to track cities already visited in the current path */
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

        /**
         * Compares nodes based on their lower bound value.
         * This allows the PriorityQueue to always expand the node with the minimum bound.
         * @param other The other node to compare with.
         * @return Comparison result for sorting.
         */
        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.bound, other.bound);
        }
    }

    /**
     * Calculates the lower bound for a given node.
     * The bound represents the estimated minimum total cost if this path is completed.
     * @param node The current node to calculate the bound for.
     * @return The calculated lower bound cost.
     */
    private int calculateBound(Node node) {
        int bound = node.cost;
        for (int i = 0; i < vCount; i++) {
            if (!node.visited[i]) {
                int min = Integer.MAX_VALUE;
                for (int j = 0; j < vCount; j++) {
                    if (adjMatrix[i][j] > 0 && adjMatrix[i][j] < min) {
                        min = adjMatrix[i][j];
                    }
                }
                if (min != Integer.MAX_VALUE) {
                    bound += min;
                }
            }
        }
        return bound;
    }

    /**
     * Solves the Traveling Salesperson Problem using the Branch and Bound technique.
     * It uses a Least-Cost Search by maintaining a PriorityQueue of nodes.
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
                int totalCost = minNode.cost + adjMatrix[minNode.currentCity][0];
                if (totalCost < minFinalCost) {
                    minFinalCost = totalCost;
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
     * Main entry point to demonstrate the Branch and Bound TSP solver.
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