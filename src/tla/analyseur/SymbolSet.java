package tla.analyseur;

import java.util.TreeSet;

public class SymbolSet extends TreeSet<Symbol> {

    public void add(String s) {
        this.add(Symbol.createTerminal(s));
    }

    public boolean contains(String s) {
        return this.contains(Symbol.createTerminal(s));
    }

    public Symbol get(String s) {
        for (Symbol a : this) {
            if (a.equals(s)) {
                return a;
            }
        }
        return null;
    }

    public String toString() {
        String str = "";
        // TODO
        return str;
    }
}
