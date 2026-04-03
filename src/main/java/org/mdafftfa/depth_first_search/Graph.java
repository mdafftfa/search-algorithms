package org.mdafftfa.depth_first_search;

import java.util.LinkedList;
import java.util.List;

/**
 * The Graph class represents a directed graph using an adjacency list.
 * It provides a recursive implementation of the Depth-First Search (DFS) algorithm.
 * This version uses modern Java practices such as 'final' fields,
 * List interfaces for abstraction, and data-driven initialization.
 */
public class Graph {

    /** * Total number of vertices (nodes) in the graph.
     * Marked 'final' because it is assigned once in the constructor.
     */
    private final int vCount;

    /** * An array of Lists representing the adjacency list.
     * Each index 'i' contains a list of integers representing nodes connected to node 'i'.
     */
    private final List<Integer>[] adj;

    /**
     * Constructor to initialize the graph with 'v' vertices.
     * @param v The number of vertices to be created in the graph.
     */
    @SuppressWarnings("unchecked")
    public Graph(int v) {
        this.vCount = v;
        this.adj = new LinkedList[v];
        for (int i = 0; i < v; ++i) {
            this.adj[i] = new LinkedList<>();
        }
    }

    /**
     * Adds a directed edge from source vertex 'v' to destination vertex 'w'.
     * @param v The starting vertex.
     * @param w The ending vertex.
     */
    public void addEdge(int v, int w) {
        adj[v].add(w);
    }

    /**
     * A private recursive helper method that performs the actual DFS traversal.
     * It uses the System Call Stack to explore as deep as possible along each branch.
     * @param current The vertex currently being visited.
     * @param visited A boolean array to keep track of nodes already visited.
     */
    private void dfsUtil(int current, boolean[] visited) {
        visited[current] = true;
        System.out.print(current + " ");
        for (int neighbor : adj[current]) {
            if (!visited[neighbor]) {
                dfsUtil(neighbor, visited);
            }
        }
    }

    /**
     * The public interface to start a Depth-First Search traversal.
     * It initializes the visited tracking array and calls the recursive utility.
     * @param startNode The index of the node where the traversal begins.
     */
    public void performDfs(int startNode) {
        boolean[] visited = new boolean[vCount];
        dfsUtil(startNode, visited);
    }

    /**
     * Helper method to create a specific graph structure for testing.
     * Uses a data-driven approach (2D array) to separate data from logic.
     * @return A Graph object pre-populated with nodes and edges.
     */
    private static Graph createUniqueGraph() {
        Graph g = new Graph(4);
        int[][] edges = {
                {0, 1}, {0, 2}, {1, 2}, {2, 0}, {2, 3}, {3, 3}
        };

        for (int[] edge : edges) {
            g.addEdge(edge[0], edge[1]);
        }
        return g;
    }

    /**
     * Main entry point of the program.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        Graph g = createUniqueGraph();

        System.out.println("Depth First Traversal Results (starting from node 2):");
        g.performDfs(2);
    }
}