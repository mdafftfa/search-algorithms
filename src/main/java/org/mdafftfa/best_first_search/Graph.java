package org.mdafftfa.best_first_search;

import java.util.*;

/**
 * The Graph class implements the Best-First Search algorithm.
 * Best-First Search uses an evaluation function (heuristic) to decide
 * which adjacent node is most promising to explore next.
 */
public class Graph {

    /**
     * Inner class representing a node in the priority queue.
     * It holds the vertex index and its associated heuristic value.
     */
    static class Node implements Comparable<Node> {
        /** The vertex identifier */
        int v;
        /** The heuristic value (estimated distance to goal) */
        int heuristic;

        /**
         * Constructor for Node.
         * @param v Vertex index.
         * @param h Heuristic value.
         */
        Node(int v, int h) {
            this.v = v;
            this.heuristic = h;
        }

        /**
         * Compares nodes based on their heuristic values.
         * Lower heuristic values are given higher priority.
         * @param other The other node to compare with.
         * @return Comparison result of heuristic values.
         */
        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.heuristic, other.heuristic);
        }
    }

    /** Total number of vertices in the graph */
    private final int vCount;

    /** Adjacency list to store graph edges using List interface */
    private final List<Edge>[] adj;

    /**
     * Helper class representing an edge to a target vertex.
     */
    static class Edge {
        /** Destination vertex of the edge */
        int target;
        /**
         * Constructor for Edge.
         * @param t Target vertex index.
         */
        Edge(int t) { this.target = t; }
    }

    /**
     * Constructor to initialize the graph with 'v' vertices.
     * @param v Number of vertices to be created.
     */
    @SuppressWarnings("unchecked")
    public Graph(int v) {
        this.vCount = v;
        this.adj = new LinkedList[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    /**
     * Adds a directed edge from source to target.
     * @param source Starting vertex.
     * @param target Ending vertex.
     */
    public void addEdge(int source, int target) {
        adj[source].add(new Edge(target));
    }

    /**
     * Performs a Greedy Best-First Search from startNode to targetNode.
     * It uses a PriorityQueue to always expand the node with the lowest heuristic value.
     * @param startNode The starting node index.
     * @param targetNode The destination node index.
     * @param heuristics Array of heuristic values representing estimated distance to goal.
     */
    public void search(int startNode, int targetNode, int[] heuristics) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        boolean[] visited = new boolean[vCount];

        pq.add(new Node(startNode, heuristics[startNode]));
        visited[startNode] = true;

        System.out.println("Path explored by Best-First Search:");

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            System.out.print(current.v + " ");

            if (current.v == targetNode) {
                System.out.println("\nTarget " + targetNode + " found!");
                return;
            }

            for (Edge edge : adj[current.v]) {
                if (!visited[edge.target]) {
                    visited[edge.target] = true;
                    pq.add(new Node(edge.target, heuristics[edge.target]));
                }
            }
        }
    }

    /**
     * Main method to demonstrate the Best-First Search traversal.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        int nodes = 5;
        Graph graph = new Graph(nodes);

        int[] heuristics = {10, 8, 5, 7, 0};

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 4);

        graph.search(0, 4, heuristics);
    }
}