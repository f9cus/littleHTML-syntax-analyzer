package littlehtml;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class Parser {

    private Stack<String> stack = new Stack<>();
    private Printer output;

    private static final Set<String> terminals = new HashSet<>();
    private static final Set<String> nonterminals = new HashSet<>();

    // maps input term to rule
    private static final Map<String, Rule> table = new HashMap<>();

    // maps rule id to rule
    private static final Map<Integer, Rule> rules = new HashMap<>();

    static {
        addRule(1, "S", "(", "S", ")");
        addRule(2, "S", "e");

        addToTable("(", 1);
        addToTable(")", 2);
        addToTable("$", 3);

        terminals.addAll(table.keySet());
        nonterminals.addAll(rules.values().stream().map(Rule::getLeftSide).collect(Collectors.toList()));
    }

    Parser (Printer printer) {
        this.output = printer;
    }

    String parse(String input) throws ParseException {
        int i = 0;
        String[] inputTerms = input.replaceAll(">","> ").replaceAll("<"," <").replaceAll("=","= ").split("\\s+");
        List<String> inputs = arrayToList(inputTerms);

        clearStack();
        System.out.println("nonterm:"  + nonterminals);

        for (String term : inputs) {
            output.print("-------");
            output.print("[" + ++i + "]\tParsing term: " + term, false);
            System.out.println("[" + i + "] Parsing term " + term);
            output.print(stack);

            while (!term.equals(stack.lastElement())) {
                System.out.println("while: " + term + "!=" + stack.lastElement());

                if (!isNonTerminal(stack.lastElement())) {
                    throw new ParseException("Unknown symbol on top of stack: " + stack.lastElement(), i);
                }

                if (!nonterminals.contains(term) && !terminals.contains(term)) {
                    throw new ParseException("Unknown term: " + term, i);
                }

                // if nonterminal, use rule from table
                stack.pop();
                Rule rule = table.get(term);
                List<String> rightSide = rule.getRightSide();
                output.print("Applying rule " + rule);

                // if epsilon rule -> only pop
                if ("e".equals(rightSide.get(0))) {
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
            return("Finished parsing, but stack is not empty!");
        }
    }

    private void clearStack() {
        stack.clear();
        stack.push("$");
        stack.push("S");
    }

    private List<String> arrayToList(String[] inputTerms) {
        List<String> list = new ArrayList<>();

        for (String s : inputTerms) {
            if (s.endsWith(">") && !isTerminal(s)) {
//                System.out.println("FCS REPLACING: " + s);
                s = s.replaceAll(">", "");
                list.add(s);
                list.add(">");
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

    private static void addToTable(String s, int ruleNumber) {
        table.put(s, rules.get(ruleNumber));
    }

    private static void addRule(int id, String left, String... right) {
        rules.put(id, new Rule(id, left, Arrays.asList(right)));
    }

}
