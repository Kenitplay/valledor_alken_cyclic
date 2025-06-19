import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class cyclic_dfs {
    private static List<Integer> dfsOrder = new ArrayList<>();

    private static boolean dfs(int node, List<List<Integer>> adj, int[] state) {
        state[node] = 1; 
        dfsOrder.add(node);  
        System.out.println("Visiting: " + (char)('a' + node)); 

        for (int neighbor : adj.get(node)) {
            if (state[neighbor] == 1) {
                return true;  
            }
            if (state[neighbor] == 0 && dfs(neighbor, adj, state)) {
                return true;
            }
        }

        state[node] = 2;  
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of nodes: ");
        int n = scanner.nextInt();

        if (n > 26) {
            System.out.println("Too many nodes! Maximum supported is 26 (a to z).");
            return;
        }

        // Print vertices
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
        boolean hasCycle = false;

        for (int i = 0; i < n; ++i) {
            if (state[i] == 0 && dfs(i, adj, state)) {
                hasCycle = true;
                break;
            }
        }

        if (hasCycle) {
            System.out.println("\nGraph is Cyclic.");
        } else {
            System.out.println("\nGraph is Acyclic.");
            System.out.print("DFS Traversal Order: ");
            for (int i = 0; i < dfsOrder.size(); ++i) {
                System.out.print((char)('a' + dfsOrder.get(i)));
                if (i != dfsOrder.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }
}
