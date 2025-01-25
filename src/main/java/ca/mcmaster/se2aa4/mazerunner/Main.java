package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");
        try {
            if(args.length == 2 && args[0].equals("-i")) {
                logger.info("**** Reading the maze from file " + args[1]);
                BufferedReader reader = new BufferedReader(new FileReader(args[1]));
                String line;
                while ((line = reader.readLine()) != null) {
                    StringBuilder rowLog = new StringBuilder();
                    for (int idx = 0; idx < line.length(); idx++) {
                        if (line.charAt(idx) == '#') {
                            System.out.print("WALL ");
                        } else if (line.charAt(idx) == ' ') {
                            System.out.print("PASS ");
                        }
                    }
                    System.out.print(System.lineSeparator());
                }
            } 
            else if (args.length == 4 && args[0].equals("-i") && args[2].equals("-p")) {
                logger.info("**** Reading the maze from file " + args[1]);
                MazeWalker walker = new MazeWalker(args[1]);
                if (walker.validatePath(args[3])) {
                    System.out.println("correct path");
                }
                else {
                    System.out.println("incorrect path");
                }
            }else {
                logger.warn("Usage: '-i' 'text file' and/or '-p' 'path'");
            }
        } catch(Exception e) {
            logger.error("/!\\ An error has occured /!\\");
        }
        logger.info("**** Computing path");
        logger.info("PATH NOT COMPUTED");
        logger.info("** End of MazeRunner");
    }
}
