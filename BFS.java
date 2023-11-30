import java.util.*;

public class BFS {

    static int[][] moves = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    static boolean isGoal(int[][] state, int[][] goal) {
        return Arrays.deepEquals(state, goal);
    }

    static int[] findEmptyPosition(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    static int[][] copyArray(int[][] source) {
        int[][] copy = new int[source.length][];
        for (int i = 0; i < source.length; i++) {
            copy[i] = Arrays.copyOf(source[i], source[i].length);
        }
        return copy;
    }

    public static List<String> solve(int[][] initial, int[][] goal) {
        int n = initial.length;

        Queue<int[][]> queue = new LinkedList<>();
        Map<int[][], int[][]> parentMap = new HashMap<>();
        queue.add(initial);
        parentMap.put(initial, null);
        while (!queue.isEmpty()) {
            int[][] curr = queue.poll();

            if (isGoal(curr, goal)) {
                return reconstructPath(parentMap, curr);
            }

            int[] emptyPos = findEmptyPosition(curr);
            int emptyRow = emptyPos[0];
            int emptyCol = emptyPos[1];

            for (int[] move : moves) {
                int newRow = emptyRow + move[0];
                int newCol = emptyCol + move[1];

                if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < n) {
                    int[][] newBoard = copyArray(curr);
                    newBoard[emptyRow][emptyCol] = newBoard[newRow][newCol];
                    newBoard[newRow][newCol] = 0;

                    if (!parentMap.containsKey(newBoard)) {
                        parentMap.put(newBoard, curr);
                        queue.add(newBoard);
                    }
                }
            }
        }
        return null;
    }

    static List<String> reconstructPath(Map<int[][], int[][]> parentMap, int[][] goal) {
        List<String> path = new ArrayList<>();
        int[][] cur = goal;

        while (parentMap.get(cur) != null) {
            int[][] parent = parentMap.get(cur);
            int[] emptyPos = findEmptyPosition(cur);
            int[] parentEmptyPos = findEmptyPosition(parent);

            int rowDiff = parentEmptyPos[0] - emptyPos[0];
            int colDiff = parentEmptyPos[1] - emptyPos[1];

            if (rowDiff == 1) path.add("U");
            else if (rowDiff == -1) path.add("D");
            else if (colDiff == 1) path.add("L");
            else if (colDiff == -1) path.add("R");

            cur = parent;
        }

        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        int[][] initial = {
            {1, 2, 3},
            {4, 0, 5},
            {6, 7, 8}
        };

        int[][] goal = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };

        List<String> path = solve(initial, goal);
        if (path != null) {
            System.out.println("Optimal path: " + path);
        } else {
            System.out.println("No solution found.");
        }
    }
}

