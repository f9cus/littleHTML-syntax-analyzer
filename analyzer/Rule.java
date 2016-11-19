package littlehtml.analyzer;

import java.util.List;

public class Rule {

    private final int id;
    private final String leftSide;
    private final List<String> rightSide;

    public Rule(int id, String leftSide, List<String> rightSide) {
        this.id = id;
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    public String getLeftSide() {
        return leftSide;
    }

    public List<String> getRightSide() {
        return rightSide;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "#" + id + ": " + leftSide + " -> " + String.join(" ", rightSide);
    }

}