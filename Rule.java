package littlehtml;

import java.util.List;

public class Rule {

    private int id;
    private String leftSide;
    private List<String> rightSide;

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
        return "#" + id + ": " + leftSide + " -> " + rightSide;
    }

}