import java.util.*;

class State {
    int jugA, jugB;
    State parent;

    public State(int jugA, int jugB, State parent) {
        this.jugA = jugA;
        this.jugB = jugB;
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return jugA == state.jugA && jugB == state.jugB;
    }

    @Override
    public int hashCode() {
        return Objects.hash(jugA, jugB);
    }
}

public class A_Star {
    private int jugACapacity;
    private int jugBCapacity;
    private int targetACapacity;

    public A_Star(int jugACapacity, int jugBCapacity, int targetACapacity) {
        this.jugACapacity = jugACapacity;
        this.jugBCapacity = jugBCapacity;
        this.targetACapacity = targetACapacity;
    }

    public State solve() {
        Set<State> visited = new HashSet<>();
        PriorityQueue<State> openList = new PriorityQueue<>(Comparator.comparingInt(this::heuristic));
        openList.add(new State(0, 0, null));

        while (!openList.isEmpty()) {
            State currentState = openList.poll();
            visited.add(currentState);

            if (currentState.jugA == targetACapacity) {
                return currentState;
            }

            // Generate possible next states and enqueue them based on the heuristic
            generateAndEnqueueStates(currentState, openList, visited);
        }

        return null;
    }

    private void generateAndEnqueueStates(State currentState, PriorityQueue<State> openList, Set<State> visited) {
        int jugA = currentState.jugA;
        int jugB = currentState.jugB;

        // Pour water from A to B
        if (jugA > 0) {
            int amountToPour = Math.min(jugA, jugBCapacity - jugB);
            enqueueState(new State(jugA - amountToPour, jugB + amountToPour, currentState), openList, visited);
        }

        // Pour water from B to A
        if (jugB > 0) {
            int amountToPour = Math.min(jugB, jugACapacity - jugA);
            enqueueState(new State(jugA + amountToPour, jugB - amountToPour, currentState), openList, visited);
        }

        // Fill jug A
        enqueueState(new State(jugACapacity, jugB, currentState), openList, visited);

        // Fill jug B
        enqueueState(new State(jugA, jugBCapacity, currentState), openList, visited);

        // Empty jug A
        enqueueState(new State(0, jugB, currentState), openList, visited);

        // Empty jug B
        enqueueState(new State(jugA, 0, currentState), openList, visited);
    }

    private void enqueueState(State state, PriorityQueue<State> openList, Set<State> visited) {
        if (!visited.contains(state)) {
            openList.add(state);
        }
    }

    private int heuristic(State state) {
        return Math.abs(state.jugA - targetACapacity);
    }

    public void printSolution(State goalState) {
        if (goalState == null) {
            System.out.println("No solution found.");
            return;
        }

        List<State> path = new ArrayList<>();
        State currentState = goalState;

        while (currentState != null) {
            path.add(currentState);
            currentState = currentState.parent;
        }

        Collections.reverse(path);
        System.out.println("Solution:");

        for (State state : path) {
            System.out.println("(" + state.jugA + ", " + state.jugB + ") -> ");
        }

        System.out.println("GOAL");
    }
 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the capacity of Jug A: ");
        int jugACapacity = scanner.nextInt();
        System.out.print("Enter the capacity of Jug B: ");
        int jugBCapacity = scanner.nextInt();
        System.out.print("Enter the target capacity for Jug A: ");
        int targetACapacity = scanner.nextInt();
        scanner.close();

        A_Star solver = new A_Star(jugACapacity, jugBCapacity, targetACapacity);
        State goalState = solver.solve();

        solver.printSolution(goalState);
    }
}