package org.mdafftfa.dynamic_programming;

import java.util.Arrays;

/**
 * The Graph class implements the Floyd-Warshall algorithm using Dynamic Programming.
 * It calculates the shortest path between every pair of vertices in a weighted graph.
 */
public class Graph {

    /** The number of vertices in the graph */
    private final int V;

    /** * The distance matrix where dist[i][j] stores the shortest distance
     * from vertex i to vertex j. This acts as our DP Table.
     */
    private final int[][] dist;

    /** A large value representing Infinity for unreachable nodes */
    private static final int INF = 99999;

    /**
     * Constructor to initialize the graph with 'v' vertices.
     * @param v Number of vertices.
     */
    public Graph(int v) {
        this.V = v;
        this.dist = new int[v][v];

        for (int i = 0; i < v; i++) {
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0;
        }
    }

    /**
     * Adds a directed edge to the graph with a specific weight.
     * @param u Source vertex.
     * @param v Destination vertex.
     * @param weight Edge weight.
     */
    public void addEdge(int u, int v, int weight) {
        dist[u][v] = weight;
    }

    /**
     * Core Dynamic Programming logic (Floyd-Warshall Algorithm).
     * It iteratively updates the distance matrix by considering every vertex 'k'
     * as an intermediate point between source 'i' and destination 'j'.
     */
    public void solveDP() {
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {

                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }
        printSolution();
    }

    /**
     * Utility method to print the final Shortest Path Matrix.
     */
    private void printSolution() {
        System.out.println("Shortest Distance Matrix between every pair of nodes:");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (dist[i][j] == INF)
                    System.out.print("INF ");
                else
                    System.out.print(dist[i][j] + "   ");
            }
            System.out.println();
        }
    }

    /**
     * Main method to test the algorithm.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Graph g = new Graph(4);

        g.addEdge(0, 1, 5);
        g.addEdge(0, 2, 10);
        g.addEdge(1, 3, 3);
        g.addEdge(2, 3, 1);

        g.solveDP();
    }
}