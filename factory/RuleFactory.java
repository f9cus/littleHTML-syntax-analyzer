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
        addRule(2, "documentheader", "<head>", "X", "</head>");
        addRule(3, "X", "headertag", "X");
        addRule(4, "X", EPSILON);
        addRule(5, "headertag", "titletag");
        addRule(6, "headertag", "metatag");
        addRule(7, "titletag", "<title>", "content", "</title>");
        addRule(8, "metatag", "<meta", "name=", "word", "content=", "word", ">");
        addRule(9, "documentbody", "<body>", "bodytags", "</body>");
        addRule(10, "bodytags", "table", "bodytags");
        addRule(11, "bodytags", "list", "bodytags");
        addRule(12, "bodytags", "paragraph", "bodytags");
        addRule(13, "bodytags", "content", "bodytags");
        addRule(14, "bodytags", EPSILON);
        addRule(15, "paragraph", "<p>", "bodytag");
        addRule(16, "paragraph", "<p>", "bodytag", "</p>");
        addRule(18, "table", "<table>", "tablerows", "</table>");
        addRule(19, "tablerows", "tablerow", "tablerows");
        addRule(21, "tablerows", EPSILON);
        addRule(22, "tablerow", "<tr>", "tablecells", "</tr>");
        addRule(23, "tablecells", "tablecell", "tablecells");
        addRule(25, "tablecells", EPSILON);
        addRule(26, "tablecell", "<td>", "bodytag");
        addRule(27, "tablecell", "<td>", "bodytag", "</td>");
        addRule(29, "list", "unordered");
        addRule(30, "list", "ordered");
        addRule(31, "list", "definitionlist");
        addRule(32, "unordered", "<ul>", "listitems", "</ul>");
        addRule(33, "ordered", "<ol>", "listitems", "</ol>");
        addRule(34, "listitems", "<li>", "bodytag", "listitems");
        addRule(36, "listitems", EPSILON);
        addRule(38, "A", "defterm", "A");
        addRule(39, "A", EPSILON);
        addRule(40, "defterm", "<dt>", "B");
        addRule(41, "B", "defterm", "B");
        addRule(42, "B", EPSILON);
        addRule(43, "defterm", "<dd>", "bodytag");
        addRule(44, "content", "word", "content");
        addRule(46, "content", EPSILON);
        addRule(47, "word", "char", "C");
        addRule(48, "C", "char C");
        addRule(49, "C", EPSILON);
        addRule(50, "char", "digit");
        addRule(51, "char", "letter");
        addRule(52, "char", ".");
        addRule(53, "char", ",");
        addRule(54, "char", "!");
        addRule(55, "char", "..");

        int n = 55;

        for (char c = '0'; c <= '9'; c++) {
            addRule(++n, "char", String.valueOf(c));
        }
        for (char c = 'A'; c <= 'z'; c++) {
            addRule(++n, "char", String.valueOf(c));
        }

///////////////////////////////////////////
        addRule(100, "htmldocument", "char", "CX");
        addRule(101, "CX", "char", "CX");
        addRule(102, "CX", EPSILON);

        addRule(1000, "S", "(", "S", ")");
        addRule(2000, "S", EPSILON);

    }

    public static Map<Integer, Rule> createRules() {
        return rules;
    }

    private static void addRule(int id, String left, String... right) {
        rules.put(id, new Rule(id, left, Arrays.asList(right)));
    }
}
