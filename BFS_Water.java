import java.util.*;
import java.util.Queue;

public class BFS_Water{

    public static void main(String[] args) {
        int jugACapacity = 4;
        int jugBCapacity = 3;
        int targetVolume = 2;

        State initialState = new State(0, 0, null);
        Queue<State> queue = new LinkedList<>();
        queue.offer(initialState);
        boolean[][] visited = new boolean[jugACapacity + 1][jugBCapacity + 1];

        while (!queue.isEmpty()) {
            State currentState = queue.poll();

            if (isGoalState(currentState, targetVolume)) {
                printSolution(currentState);
                break;
            }

            if (!visited[currentState.jugA][currentState.jugB]) {
                visited[currentState.jugA][currentState.jugB] = true;

                State newState = new State(jugACapacity, currentState.jugB, currentState);
                queue.offer(newState);

                newState = new State(currentState.jugA, jugBCapacity, currentState);
                queue.offer(newState);

                newState = new State(0, currentState.jugB, currentState);
                queue.offer(newState);

                newState = new State(currentState.jugA, 0, currentState);
                queue.offer(newState);

                int amountToPour = Math.min(currentState.jugA, jugBCapacity - currentState.jugB);
                newState = new State(currentState.jugA - amountToPour, currentState.jugB + amountToPour, currentState);
                queue.offer(newState);

                amountToPour = Math.min(currentState.jugB, jugACapacity - currentState.jugA);
                newState = new State(currentState.jugA + amountToPour, currentState.jugB - amountToPour, currentState);
                queue.offer(newState);
            }
        }
    }

    static boolean isGoalState(State state, int targetVolume) {
        return state.jugA == targetVolume || state.jugB == targetVolume || (state.jugA + state.jugB == targetVolume);
    }

    static void printSolution(State goalState) {
        if (goalState == null) {
            System.out.println("No solution found.");
            return;
        }

        LinkedList<State> path = new LinkedList<>();
        State currentState = goalState;

        while (currentState != null) {
            path.addFirst(currentState);
            currentState = currentState.parent;
        }

        System.out.println("Solution:");

        for (State state : path) {
            System.out.println("(" + state.jugA + ", " + state.jugB + ")");
        }
    }
}

class State {
    int jugA;
    int jugB;
    State parent;

    State(int jugA, int jugB, State parent) {
        this.jugA = jugA;
        this.jugB = jugB;
        this.parent = parent;
    }
}