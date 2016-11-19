package littlehtml;

import com.google.common.collect.Table;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Stack;

public class Parser {

    public static final String EPSILON = "ε";

    private static final Set<String> terminals = new HashSet<>();
    private static final Set<String> nonterminals = new HashSet<>();

    // table with rules
    private Table<String, String, Rule> table;

    private Stack<String> stack = new Stack<>();
    private Printer output;

    Parser (Printer printer) {
        this.output = printer;
        table = new TableFactory().createTable();
        terminals.addAll(table.columnKeySet());
        nonterminals.addAll(table.rowKeySet());

////////////////////////////
//        terminals.add("<html>");
//        terminals.add("</html>");
//        terminals.add("a");
//        terminals.add("b");
//        nonterminals.addAll(rules.values().stream().map(Rule::getLeftSide).collect(Collectors.toList()));
    }

    String parse(String input) throws ParseException {
        int step = 0;
        String[] inputTerms = input.replaceAll(">","> ").replaceAll("<"," <").replaceAll("=","= ").split("\\s+");

        List<String> inputs = arrayToList(inputTerms);

        clearStack();
        System.out.println("nonterm:"  + nonterminals);
        System.out.println("terminals:" + terminals);
        System.out.println("table:" + table);

        for (String term : inputs) {
            output.print(term);
        }

        for (String term : inputs) {
            output.print("-------");
            output.print("[" + ++step + "]\tParsing term: " + term, false);
            System.out.println("[" + step + "] Parsing term " + term);
            output.print(stack);

            while (!term.equals(stack.peek())) {
                System.out.println("while: " + term + "!=" + stack.peek());

                if (!isTerminal(term)) {
                    throw new ParseException("Unknown term: " + term, step);
                }

                if (!isNonTerminal(stack.peek())) {
                    throw (stack.size() == 1) ?
                            new ParseException("Bottom of stack was reached, cannot continue" , step)
                            : new ParseException("Unknown symbol on top of stack: " + stack.peek(), step);
                }

                // use rule from table
                Rule rule = table.column(term).get(stack.peek());
                if (rule == null) {
                    throw new ParseException("No rule defined for: " + term + " x " + stack.peek(), step);
                }
                List<String> rightSide = rule.getRightSide();
                output.print("Applying rule " + rule);

                stack.pop();

                // if epsilon rule -> only pop
                if (EPSILON.equals(rightSide.get(0))) {
                    output.print("Epsilon rule -> pop");
                    output.print(stack);
                    continue;
                }

                // push to stack in reverse order
                ListIterator it = rightSide.listIterator(rightSide.size());
                while (it.hasPrevious()) {
                    stack.push((String) it.previous());
                }

                output.print(stack);
            }

            output.print(term + " is on top of stack -> pop & next");
            stack.pop();
        }

        output.print("-------");
        output.print(stack);

        if (stack.size() <= 1) {
            return("DONE - SUCCESS");
        } else {
            throw new ParseException("Finished parsing, but stack is not empty!", step);
        }
    }

    private void clearStack() {
        stack.clear();
        stack.push("$");
//        stack.push("htmldocument");
        stack.push("S");
    }

    private List<String> arrayToList(String[] inputTerms) {
        List<String> list = new ArrayList<>();

        for (String s : inputTerms) {
            if (s.length() < 1) {
                continue;
            } else if (s.endsWith(">") && !isTerminal(s)) {
//                System.out.println("FCS REPLACING: " + s);
                s = s.replaceAll(">", "");
                list.add(s);
                list.add(">");
                continue;
            } else if (!isTerminal(s)) {
                // split to chars
                Collections.addAll(list, s.split(""));
                continue;
            }

            list.add(s);
        }
        return list;
    }

    private boolean isTerminal(String s) {
        return terminals.contains(s);
    }

    private boolean isNonTerminal(String s) {
        return nonterminals.contains(s);
    }

}
