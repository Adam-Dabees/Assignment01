package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;

public class FactorizedFormatter implements PathFormatter {
    @Override
    public String format(List<String> path) {
        StringBuilder sb = new StringBuilder();
        int count = 1;
        for (int i = 1; i <= path.size(); i++) {
            if (i < path.size() && path.get(i).equals(path.get(i - 1))) {
                count++;
            } else {
                if (count > 1) sb.append(count);
                sb.append(path.get(i - 1));
                count = 1;
            }
        }
        return sb.toString();
    }
}
