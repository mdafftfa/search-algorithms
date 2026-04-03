package org.mdafftfa.a_star;

import java.util.*;

/**
 * The Graph class implements the A* (A-Star) search algorithm.
 * A* is an informed search algorithm that finds the shortest path between nodes
 * by combining the actual cost from the start (gScore) and an estimated
 * cost to the goal (heuristic h).
 */
public class Graph {

    /** Total number of vertices (nodes) in the graph. */
    private final int V;

    /** Adjacency list storing edges for each vertex using the List interface. */
    private final List<Edge>[] adj;

    /**
     * Inner class representing a weighted edge between two vertices.
     */
    static class Edge {
        /** The destination vertex of this edge. */
        int target;
        /** The weight (actual cost/distance) of this edge. */
        int weight;

        /**
         * Constructor for Edge.
         * @param target Destination node.
         * @param weight Travel cost to the target.
         */
        Edge(int target, int weight) {
            this.target = target;
            this.weight = weight;
        }
    }

    /**
     * Inner class representing a state in the search space.
     * Implements Comparable to allow the PriorityQueue to sort nodes by fScore.
     */
    static class Node implements Comparable<Node> {
        /** The unique identifier of the vertex. */
        final int id;
        /** The cost from the start node to this node (gScore). */
        int gScore;
        /** The total estimated cost (fScore = gScore + heuristic). */
        int fScore;

        /**
         * Constructor for Node.
         * @param id Vertex ID.
         * @param gScore Actual cost from start.
         * @param fScore Total estimated cost to goal.
         */
        Node(int id, int gScore, int fScore) {
            this.id = id;
            this.gScore = gScore;
            this.fScore = fScore;
        }

        /**
         * Comparison logic to prioritize nodes with the lowest fScore.
         * @param other The node to compare against.
         * @return Comparison result for the PriorityQueue.
         */
        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.fScore, other.fScore);
        }
    }

    /**
     * Constructor to initialize the graph with 'v' vertices.
     * @param v The number of vertices in the graph.
     */
    @SuppressWarnings("unchecked")
    public Graph(int v) {
        this.V = v;
        this.adj = new ArrayList[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new ArrayList<>();
        }
    }

    /**
     * Adds an undirected (bidirectional) edge between vertex u and v.
     * @param u Source vertex index.
     * @param v Destination vertex index.
     * @param weight Travel cost between the two nodes.
     */
    public void addEdge(int u, int v, int weight) {
        adj[u].add(new Edge(v, weight));
        adj[v].add(new Edge(u, weight));
    }

    /**
     * Performs the A* Search algorithm to find the shortest path from start to goal.
     * Uses a PriorityQueue (openList) to explore nodes with the lowest fScore.
     * @param start Starting vertex index.
     * @param goal Target vertex index.
     * @param h Array of heuristic values for each vertex (estimated distance to goal).
     */
    public void aStarSearch(int start, int goal, int[] h) {
        PriorityQueue<Node> openList = new PriorityQueue<>();

        int[] gScore = new int[V];
        Arrays.fill(gScore, Integer.MAX_VALUE);

        int[] parent = new int[V];
        Arrays.fill(parent, -1);

        gScore[start] = 0;
        int fInitial = gScore[start] + h[start];
        openList.add(new Node(start, gScore[start], fInitial));

        System.out.println("Starting A* search from " + start + " to " + goal + "...");

        while (!openList.isEmpty()) {
            Node current = openList.poll();

            if (current.id == goal) {
                printPath(parent, goal, gScore[goal]);
                return;
            }

            for (Edge edge : adj[current.id]) {
                int tentativeGScore = gScore[current.id] + edge.weight;

                if (tentativeGScore < gScore[edge.target]) {
                    parent[edge.target] = current.id;
                    gScore[edge.target] = tentativeGScore;
                    int fScore = gScore[edge.target] + h[edge.target];

                    openList.add(new Node(edge.target, gScore[edge.target], fScore));
                }
            }
        }
        System.out.println("Jalur tidak ditemukan.");
    }

    /**
     * Reconstructs and prints the shortest path from the goal to the start.
     * @param parent Array storing the predecessor of each node.
     * @param goal The destination node.
     * @param totalCost The total accumulated gScore at the goal.
     */
    private void printPath(int[] parent, int goal, int totalCost) {
        List<Integer> path = new ArrayList<>();
        for (int at = goal; at != -1; at = parent[at]) {
            path.add(at);
        }
        Collections.reverse(path);
        System.out.println("Shortest Path: " + path);
        System.out.println("Total cost (gScore): " + totalCost);
    }

    /**
     * Main method to demonstrate the A* search on a sample graph.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Graph g = new Graph(6);

        int[] h = {10, 8, 5, 7, 3, 0};

        g.addEdge(0, 1, 2);
        g.addEdge(0, 2, 4);
        g.addEdge(1, 2, 1);
        g.addEdge(1, 3, 7);
        g.addEdge(2, 4, 3);
        g.addEdge(3, 5, 1);
        g.addEdge(4, 5, 5);

        g.aStarSearch(0, 5, h);
    }
}