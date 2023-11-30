import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Eight_queens_problem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of queens (n): ");
        int n = scanner.nextInt();
        scanner.close();

        CSP csp = new CSP(n);
        boolean isSolved = csp.solve();

        if (isSolved) {
            System.out.println("Solution found:");
            csp.printSolution();
        } else {
            System.out.println("No solution found.");
        }
    }
}

class CSP {
    private int boardSize;
    private List<Integer> solution;

    public CSP(int boardSize) {
        this.boardSize = boardSize;
        this.solution = new ArrayList<>();
    }

    public boolean solve() {
        return backtrack(0);
    }

    private boolean backtrack(int row) {
        if (row == boardSize) {
            return true;
        }

        for (int col = 0; col < boardSize; col++) {
            if (isSafe(row, col)) {
                solution.add(col);
                if (backtrack(row + 1)) {
                    return true;
                }
                solution.remove(solution.size() - 1);
            }
        }

        return false;
    }

    private boolean isSafe(int row, int col) {
        for (int i = 0; i < row; i++) {
            int prevCol = solution.get(i);
            if (prevCol == col || Math.abs(prevCol - col) == Math.abs(i - row)) {
                return false;
            }
        }
        return true;
    }

    public void printSolution() {
        if (solution.isEmpty()) {
            System.out.println("No solution found.");
        } else {
            for (int row = 0; row < boardSize; row++) {
                for (int col = 0; col < boardSize; col++) {
                    if (col == solution.get(row)) {
                        System.out.print("Q ");
                    } else {
                        System.out.print(". ");
                    }
                }
                System.out.println();
            }
        }
    }
}

