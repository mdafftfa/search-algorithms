# Search Algorithms Collection in Java

This repository contains a comprehensive collection of fundamental search algorithms implemented in Java. It covers various paradigms including **Uninformed Search**, **Informed (Heuristic) Search**, **Dynamic Programming**, and **Optimization** (Branch and Bound).

---

## 📂 Project Structure

The project is organized into specific packages based on the algorithmic strategy and data structures used:

| Package | Algorithm | Strategy | Description |
| :--- | :--- | :--- | :--- |
| `org.mdafftfa.a_star` | **A* Search** | Informed Search | Finds the shortest path using $f(n) = g(n) + h(n)$. |
| `org.mdafftfa.best_first_search` | **Greedy BFS** | Heuristic Search | Explores the most promising nodes based only on heuristics ($h$). |
| `org.mdafftfa.branch_and_bound` | **Branch & Bound** | Optimization | Solves the Traveling Salesperson Problem (TSP) with pruning. |
| `org.mdafftfa.breadth_first_search`| **BFS** | Uninformed Search | Layer-by-layer traversal using a Queue (FIFO). |
| `org.mdafftfa.depth_first_search` | **DFS** | Uninformed Search | Recursive deep traversal using the System Call Stack (LIFO). |
| `org.mdafftfa.dynamic_programming` | **Floyd-Warshall** | DP | All-pairs shortest path solver using a distance matrix. |

---

## 🚀 Algorithm Overviews

### 1. A* (A-Star) Search
An informed search algorithm that is widely used in pathfinding and graph traversal. It combines the advantages of Dijkstra's Algorithm (shortest path) and Greedy Best-First Search (efficiency) by using a heuristic function to guide the search.


### 2. Greedy Best-First Search
A heuristic-based search that expands the node that appears to be closest to the goal according to the heuristic value ($h$), without considering the cost already traveled ($g$).

### 3. Traveling Salesperson (Branch and Bound)
A state-space search for solving the TSP. This implementation uses a **Lower Bound** calculation to "prune" or discard branches that cannot lead to a better solution than the current best, significantly reducing execution time compared to brute force.


### 4. Breadth-First Search (BFS)
A fundamental traversal that explores all neighbors at the current depth before moving to nodes at the next level. It is guaranteed to find the shortest path in unweighted graphs.


### 5. Depth-First Search (DFS)
Explores a branch as deep as possible before backtracking. This implementation utilizes recursion to manage the traversal of nodes.


### 6. Floyd-Warshall (Dynamic Programming)
A classic Dynamic Programming approach that computes the shortest paths between **all pairs** of vertices in a weighted graph. It works by iteratively considering every vertex as an intermediate point.


---

## 🛠️ How to Use

- Clone this repository `git clone https://github.com/mdafftfa/search-algorithms`
- Ensure Java 21 or Higher is installed on your computer.

### Compilation
Open CMD and Make sure the build folder was created by using command in below:
```bash
javac -d build (Get-ChildItem -Recurse src/*.java).FullName 
```

### How to Run
Use the following commands to run an individual search algorithm: 
```bash
java -cp build org.mdafftfa.a_star.Graph
java -cp build org.mdafftfa.best_first_search.Graph
java -cp build org.mdafftfa.branch_and_bound.Graph
java -cp build org.mdafftfa.breadth_first_search.Graph
java -cp build org.mdafftfa.depth_first_search.Graph
java -cp build org.mdafftfa.dynamic_programming.Graph
```