package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");
        try {
            if ((args.length == 2 && args[0].equals("-i")) || (args.length == 4 && args[0].equals("-i") && args[2].equals("-method"))) {
                // Determine the maze file and solver type
                String mazeFile = args[1];
                String solverType = (args.length == 4) ? args[3] : "right-hand"; // Default to right-hand rule

                logger.info("**** Reading the maze from file: " + mazeFile);
                logMazeFile(mazeFile);

                Solver solver = SolverFactory.create(solverType);
                
                if (solver == null) {
                    logger.error("Invalid solver type: " + solverType);
                    return;
                }

                MazeWalker walker = new MazeWalker(mazeFile, solver, new FactorizedFormatter());
                String correctPath = walker.findCorrectPath();
                
                // Print the correct path (only necessary output)
                System.out.println(correctPath);
                
            } 
            else if (args.length >= 4 && args[0].equals("-i") && args[2].equals("-p")) {
                // Validate the provided path
                String mazeFile = args[1];

                // Parse the remaining arguments as the full path (space-separated moves)
                String inputPath = Arrays.stream(Arrays.copyOfRange(args, 3, args.length))
                                         .collect(Collectors.joining(" "));

                logger.info("**** Reading the maze from file: " + mazeFile);
                logMazeFile(mazeFile);

                // Default solver for validation (Right-Hand Rule)
                Solver solver = new RightHandSolver(); 
                MazeWalker walker = new MazeWalker(mazeFile, solver, new PlainFormatter());

                logger.info("**** Validating path: " + inputPath);
                boolean isValid = walker.validatePath(inputPath);

                // Print only the correct/incorrect path message
                if (isValid) {
                    System.out.println("Correct path");
                } else {
                    System.out.println("Incorrect path");
                }
            } 
            else {
                logger.warn("Incorrect usage. Refer to the command structure.");
                logger.info("Usage: '-i <maze file>' OR '-i <maze file> -method <solver>' OR '-i <maze file> -p <path>'");
                logger.info("  - To compute path (default solver: right-hand): -i <maze file>");
                logger.info("  - To compute path with specific method: -i <maze file> -method <solver>");
                logger.info("  - To validate path: -i <maze file> -p <path>");
                logger.info("  - Available solvers: right-hand, tremaux");
            }
        } catch (Exception e) {
            logger.error("/!\\ An error has occurred /!\\", e);
        }
        logger.info("** End of Maze Runner");
    }

    private static void logMazeFile(String mazeFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(mazeFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                StringBuilder rowLog = new StringBuilder();
                for (char c : line.toCharArray()) {
                    rowLog.append(c == '#' ? "WALL " : "PASS ");
                }
                logger.trace(rowLog.toString());
            }
        } catch (Exception e) {
            logger.error("Error reading maze file", e);
        }
    }
}