package ca.mcmaster.se2aa4.mazerunner;

public class SolverFactory {
    public static Solver create(String method) {
        return switch (method.toLowerCase()) {
            case "right-hand" -> new RightHandSolver();
            case "tremaux" -> new TremauxSolver();
            default -> throw new IllegalArgumentException("Unknown solver method: " + method);
        };
    }
}
