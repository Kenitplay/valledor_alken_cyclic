import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class cyclic_bfs {
    private static List<Integer> bfsOrder = new ArrayList<>();

    private static boolean bfs(int start, List<List<Integer>> adj, int[] state, int[] parent) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        state[start] = 1;  // visiting

        while (!queue.isEmpty()) {
            int node = queue.poll();
            bfsOrder.add(node);
            System.out.println("Visiting: " + (char)('a' + node)); // Optional visit log

            for (int neighbor : adj.get(node)) {
                if (state[neighbor] == 0) {
                    state[neighbor] = 1;
                    parent[neighbor] = node;
                    queue.add(neighbor);
                } else if (neighbor != parent[node]) {
                    return true; // Found a back-edge (cycle)
                }
            }
            state[node] = 2;  // visited
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of nodes: ");
        int n = scanner.nextInt();

        if (n > 26) {
            System.out.println("Too many nodes! Maximum supported is 26 (a to z).");
            scanner.close();
            return;
        }

        // Print list of vertices
        System.out.print("Vertices: ");
        for (int i = 0; i < n; i++) {
            System.out.print((char)('a' + i));
            if (i != n - 1) System.out.print(", ");
        }
        System.out.println();

        int[][] matrix = new int[n][n];
        System.out.println("Enter adjacency matrix (0 or 1) Ex. (1 0 0 0):");
        for (int i = 0; i < n; ++i) {
            System.out.print("Row for node " + (char)('a' + i) + ": ");
            for (int j = 0; j < n; ++j) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        // Build adjacency list
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            adj.add(new ArrayList<>());
            for (int j = 0; j < n; ++j) {
                if (matrix[i][j] == 1) {
                    adj.get(i).add(j);
                }
            }
        }

        int[] state = new int[n]; 
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = -1;
        }
        boolean hasCycle = false;

        for (int i = 0; i < n; ++i) {
            if (state[i] == 0 && bfs(i, adj, state, parent)) {
                hasCycle = true;
                break;
            }
        }

        if (hasCycle) {
            System.out.println("\nGraph is Cyclic.");
        } else {
            System.out.println("\nGraph is Acyclic.");
            System.out.print("BFS Traversal Order: ");
            for (int i = 0; i < bfsOrder.size(); ++i) {
                System.out.print((char)('a' + bfsOrder.get(i)));
                if (i != bfsOrder.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }

        scanner.close();
    }
}
