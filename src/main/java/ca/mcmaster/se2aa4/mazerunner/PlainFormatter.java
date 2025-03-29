package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;

public class PlainFormatter implements PathFormatter {
    @Override
    public String format(List<String> path) {
        return String.join("", path);
    }
}
