package littlehtml.factory;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import littlehtml.analyzer.Rule;

import java.util.Map;

public class TableFactory {

    private static final Table<String, String, Rule> table = HashBasedTable.create();

    private Map<Integer, Rule> rules;

    public Table<String, String, Rule> createTable() {
        this.rules = RuleFactory.createRules();

        addToTable("<html>", "htmldocument", 1);
        addToTable("<head>", "documentheader", 2);
        addToTable("</head>", "A", 4);

        addToTable("<title>", "A", 3);
        addToTable("<title>", "headertag", 5);
        addToTable("<title>", "titletag", 7);
        addToTable("</title>", "content", 40);

        addToTable("<meta", "A", 3);
        addToTable("<meta", "headertag", 6);
        addToTable("<meta", "metatag", 8);

        addToTable("content=", "content", 40);
        addToTable(">", "content", 40);

        addToTable("<body>", "documentbody", 9);
        addToTable("</body>", "bodytags", 14);
        addToTable("</body>", "content", 40);

        addToTable("<p>", "bodytags", 12);
        addToTable("<p>", "paragraph", 15);
        addToTable("</p>", "content", 40);

        addToTable("<table>", "bodytags", 10);
        addToTable("<table>", "table", 16);
        addToTable("</table>", "tablerows", 18);

        addToTable("<tr>", "tablerows", 17);
        addToTable("<tr>", "tablerow", 19);
        addToTable("</tr>", "tablecells", 21);
        addToTable("</tr>", "C", 24);
        addToTable("</tr>", "content", 40);

        addToTable("<td>", "tablecells", 20);
        addToTable("<td>", "tablecell", 22);
        addToTable("<td>", "C", 24);
        addToTable("<td>", "content", 40);
        addToTable("</td>", "C", 23);
        addToTable("</td>", "content", 40);

        addToTable("<ul>", "bodytags", 11);
        addToTable("<ul>", "list", 25);
        addToTable("<ul>", "unordered", 28);
        addToTable("</ul>", "listitems", 31);
        addToTable("</ul>", "content", 40);

        addToTable("<ol>", "bodytags", 11);
        addToTable("<ol>", "list", 26);
        addToTable("<ol>", "ordered", 29);
        addToTable("</ol>", "listitems", 31);
        addToTable("</ol>", "content", 40);

        addToTable("<li>", "listitems", 30);
        addToTable("<li>", "content", 40);

        addToTable("<dl>", "bodytags", 11);
        addToTable("<dl>", "list", 27);
        addToTable("<dl>", "definitionlist", 32);
        addToTable("</dl>", "D", 34);
        addToTable("</dl>", "E", 38);

        addToTable("<dt>", "D", 33);
        addToTable("<dt>", "defterm", 35);
        addToTable("<dt>", "E", 37);

        addToTable("<dd>", "D", 33);
        addToTable("<dd>", "defterm", 36);
        addToTable("<dd>", "E", 37);

        addToTable("..", "bodytags", 13);
        addToTable("..", "content", 39);
        addToTable("..", "char", 44);

        addToTable(".", "bodytags", 13);
        addToTable(".", "content", 39);
        addToTable(".", "char", 41);

        addToTable(",", "bodytags", 13);
        addToTable(",", "content", 39);
        addToTable(",", "char", 42);

        addToTable("!", "bodytags", 13);
        addToTable("!", "content", 39);
        addToTable("!", "char", 43);

        int n = 44;

        for (char c = '0'; c <= '9'; c++) {
            addToTable(String.valueOf(c), "bodytags", 13);
            addToTable(String.valueOf(c), "content", 39);
            addToTable(String.valueOf(c), "char", ++n);
        }

        for (char c = 'A'; c <= 'Z'; c++) {
            addToTable(String.valueOf(c), "bodytags", 13);
            addToTable(String.valueOf(c), "content", 39);
            addToTable(String.valueOf(c), "char", ++n);
        }

        for (char c = 'a'; c <= 'z'; c++) {
            addToTable(String.valueOf(c), "bodytags", 13);
            addToTable(String.valueOf(c), "content", 39);
            addToTable(String.valueOf(c), "char", ++n);
        }

        return table;
    }

    private void addToTable(String terminal, String nonterminal, int ruleNumber) {
        table.put(nonterminal, terminal, rules.get(ruleNumber));
    }

}
