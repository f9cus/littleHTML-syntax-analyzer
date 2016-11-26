package littlehtml.factory;

import littlehtml.analyzer.Rule;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RuleFactory {

    public static final String EPSILON = "ε";

    private static final Map<Integer, Rule> rules = new HashMap<>();

    static {
        addRule(1, "htmldocument", "<html>", "documentheader", "documentbody", "</html>");
        addRule(2, "documentheader", "<head>", "A", "</head>");
        addRule(3, "A", "headertag", "A");
        addRule(4, "A", EPSILON);
        addRule(5, "headertag", "titletag");
        addRule(6, "headertag", "metatag");
        addRule(7, "titletag", "<title>", "content", "</title>");
        addRule(8, "metatag", "<meta", "name=", "content", "content=", "content", ">");
        addRule(9, "documentbody", "<body>", "bodytags", "</body>");
        addRule(10, "bodytags", "table", "bodytags");
        addRule(11, "bodytags", "list", "bodytags");
        addRule(12, "bodytags", "paragraph", "bodytags");
        addRule(13, "bodytags", "content");
        addRule(14, "bodytags", EPSILON);
        addRule(15, "paragraph", "<p>", "content", "</p>");
        addRule(16, "table", "<table>", "tablerows", "</table>");
        addRule(17, "tablerows", "tablerow", "tablerows");
        addRule(18, "tablerows", EPSILON);
        addRule(19, "tablerow", "<tr>", "tablecells", "</tr>");
        addRule(20, "tablecells", "tablecell", "tablecells");
        addRule(21, "tablecells", EPSILON);
        addRule(22, "tablecell", "<td>", "content", "C");
        addRule(23, "C", "</td>");
        addRule(24, "C", EPSILON);

        addRule(25, "list", "unordered");
        addRule(26, "list", "ordered");
        addRule(27, "list", "definitionlist");
        addRule(28, "unordered", "<ul>", "listitems", "</ul>");
        addRule(29, "ordered", "<ol>", "listitems", "</ol>");
        addRule(30, "listitems", "<li>", "content", "listitems");
        addRule(31, "listitems", EPSILON);
        addRule(32, "definitionlist", "<dl>", "D", "</dl>");
        addRule(33, "D", "defterm", "D");
        addRule(34, "D", EPSILON);

        addRule(35, "defterm", "<dt>", "E");
        addRule(36, "defterm", "<dd>", "content");
        addRule(37, "E", "defterm", "E");
        addRule(38, "E", EPSILON);
        addRule(39, "content", "char", "content");
        addRule(40, "content", EPSILON);

        addRule(41, "char", ".");
        addRule(42, "char", ",");
        addRule(43, "char", "!");
        addRule(44, "char", "..");

        int n = 44;

        for (char c = '0'; c <= '9'; c++) {
            addRule(++n, "char", String.valueOf(c));
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            addRule(++n, "char", String.valueOf(c));
        }
        for (char c = 'a'; c <= 'z'; c++) {
            addRule(++n, "char", String.valueOf(c));
        }
    }

    public static Map<Integer, Rule> createRules() {
        return rules;
    }

    private static void addRule(int id, String left, String... right) {
        rules.put(id, new Rule(id, left, Arrays.asList(right)));
    }
}
