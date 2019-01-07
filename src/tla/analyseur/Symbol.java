package tla.analyseur;

public class Symbol implements Comparable<Symbol> {

    private boolean dollar = false;
    private boolean epsilon = false;
    private boolean terminal = false;
    private String value = "";

    private Symbol(String v) {
        value = v;
    }

    public static Symbol createDollar() {
        Symbol s = new Symbol("DOLLAR");
        s.dollar = true;
        return s;
    }

    public static Symbol createEpsilon() {
        Symbol sb = new Symbol("");
        sb.epsilon = true;
        return sb;
    }

    public static Symbol createTerminal(String value) {
        Symbol s = new Symbol(value);
        s.terminal = true;
        return s;
    }

    public static Symbol createVariable(String value) {
        Symbol s = new Symbol(value);
        s.terminal = false;
        return s;
    }

    public int compareTo(Symbol s) {
        return value.compareTo(s.value);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Symbol)) {
            return false;
        }
        return value.equals(((Symbol) o).value);
    }

    public boolean equals(String s) {
        return value.equals(s);
    }

    public boolean isDollar() {
        return dollar;
    }

    public boolean isEpsilon() {
        return epsilon;
    }

    public boolean isTerminal() {
        return terminal;
    }

    public String toString() {
        return value;
    }
}
