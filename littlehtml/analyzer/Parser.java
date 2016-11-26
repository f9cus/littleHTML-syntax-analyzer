package littlehtml.analyzer;

import com.google.common.collect.Table;
import littlehtml.factory.TableFactory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Stack;

import static littlehtml.factory.RuleFactory.EPSILON;

public class Parser {

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

        terminals.add("</html>");
        terminals.add("name=");
        terminals.add("</li>");
    }

    String parse(String input) throws ParseException {
        int step = 0;
        String[] inputTerms = input.replaceAll(">","> ").replaceAll("<"," <").replaceAll("=","= ").split("\\s+");

        List<String> inputs = arrayToList(inputTerms);

        clearStack();

//        for (String term : inputs) {
//            output.print(term);
//        }

        for (String term : inputs) {
            output.print("-------");
            output.print("[" + ++step + "]\tParsing term: " + term, false);

            while (!term.equals(stack.peek())) {
                if (!isTerminal(term)) {
                    String recovered = recover(term);
                    if (recovered != null) {
                        output.print("Recovery performed: " + term + " -> " + recovered);
                        term = recovered;
                    } else {
                        throw new ParseException("Unknown term: " + term, step);
                    }
                }

                if (!isNonTerminal(stack.peek())) {
                    throw (stack.size() == 1) ?
                            new ParseException("Bottom of stack was reached, cannot continue", step)
                            : new ParseException("No rule defined for: " + term + " x " + stack.peek(), step);
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

        if (stack.size() > 1) {
            if (lexicalRecovery(stack)) {
                parse(input + "</html>");
                output.print("Applied recovery by adding </html>");
            }
            else {
                throw new ParseException("Finished parsing, but stack is not empty!", step);
            }
        }

        return ("Input was accepted");
    }

    private boolean lexicalRecovery(Stack<String> stack) {
        return stack.size() == 2 && stack.peek().equals("</html>");
    }

    private String recover(String term) {
        for (String terminal: terminals) {
            if (almostEqual(term, terminal)) {
                return terminal;
            }
        }
        return null;
    }

    private boolean almostEqual(String first, String second) {
        if (first.length() != second.length() || first.length() < 2) {
            return false;
        }

        int diff = 0;

        for (int i = 0; i < first.length(); i++) {
            if (first.charAt(i) != second.charAt(i)) {
                if (++diff > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    private void clearStack() {
        stack.clear();
        stack.push("$");
        stack.push("htmldocument");
    }

    private List<String> arrayToList(String[] inputTerms) {
        List<String> list = new ArrayList<>();

        for (String s : inputTerms) {
            if (s.length() < 1) {
                continue;
            }
            else if (s.endsWith(">") && !isTerminal(s) && recover(s) == null) {
                s = s.replaceAll(">", "");
                Collections.addAll(list, s.split(""));
                list.add(">");
                continue;
            }
            else if (!isTerminal(s) && recover(s) == null) {
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
