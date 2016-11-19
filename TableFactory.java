package littlehtml;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.Map;

public class TableFactory {

    private static final Table<String, String, Rule> table = HashBasedTable.create();
    private Map<Integer, Rule> rules;

    public Table<String, String, Rule> createTable() {
        this.rules = RuleFactory.createRules();

        addToTable("<html>", "htmldocument", 1);
        addToTable("</html>", "htmldocument", 2);
        addToTable("0", "char", 56);
        addToTable("0", "htmldocument", 100);
        addToTable("0", "CX", 101);

        addToTable("(", "S", 1000);
        addToTable(")", "S", 2000);
        addToTable("$", "S", 2000);

        return table;
    }

    private void addToTable(String terminal, String nonterminal, int ruleNumber) {
        table.put(nonterminal, terminal, rules.get(ruleNumber));
    }

}
