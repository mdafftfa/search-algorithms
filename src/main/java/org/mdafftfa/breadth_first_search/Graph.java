package org.mdafftfa.breadth_first_search;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * The Graph class represents a directed graph using an adjacency list.
 * It provides a method to perform a Breadth-First Search (BFS) traversal,
 * level by level, using a Queue data structure.
 */
public class Graph {

    /** * Total number of vertices in the graph.
     * Marked 'final' to prevent re-assignment.
     */
    private final int vCount;

    /** * Adjacency list using a List array.
     * Marked 'final' for better immutability of the reference.
     */
    private final List<Integer>[] adj;

    /**
     * Constructor to initialize the graph with 'v' vertices.
     * @param v The number of vertices to be created.
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
     * Adds a directed edge from vertex 'v' to vertex 'w'.
     * @param v The source vertex.
     * @param w The destination vertex.
     */
    public void addEdge(int v, int w) {
        adj[v].add(w);
    }

    /**
     * Performs a Breadth-First Search (BFS) traversal starting from vertex 's'.
     * BFS uses a Queue (FIFO) to explore nodes layer by layer.
     * @param s The starting vertex for the BFS traversal.
     */
    public void performBfs(int s) {
        boolean[] visited = new boolean[vCount];
        Queue<Integer> queue = new LinkedList<>();

        visited[s] = true;
        queue.add(s);

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();
            System.out.print(currentNode + " ");

            for (int neighbor : adj[currentNode]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
    }

    /**
     * Reusable helper method to populate a graph from a 2D array.
     * Moving this logic here breaks the similarity pattern that triggers
     * the "Duplicated code fragment" warning in the main method.
     * @param g The graph instance to populate.
     * @param data 2D array where each element is {source, destination}.
     */
    private static void populateGraph(Graph g, int[][] data) {
        for (int[] edge : data) {
            g.addEdge(edge[0], edge[1]);
        }
    }

    /**
     * Main entry point to execute the BFS algorithm.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        int nodes = 4;
        Graph g = new Graph(nodes);

        int[][] edges = {
                {0, 1}, {0, 2}, {1, 2}, {2, 0}, {2, 3}, {3, 3}
        };

        populateGraph(g, edges);

        System.out.println("Breadth First Traversal Results (starting from node 2):");
        g.performBfs(2);
    }
}