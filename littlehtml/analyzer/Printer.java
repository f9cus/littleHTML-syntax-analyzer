package littlehtml.analyzer;

import javafx.scene.control.TextArea;

import java.util.Stack;

public class Printer {

    private TextArea log;

    public Printer(TextArea logArea) {
        this.log = logArea;
    }

    public void print(Stack stack) {
        log.appendText("\tStack: ");
        log.appendText(stack.toString() + "\n");
    }

    public void print(String s) {
        log.appendText("\t" + s + "\n");
    }

    public void print(String s, boolean indent) {
        if (indent) {
            print(s);
        } else {
            log.appendText(s + "\n");
        }
    }

}
