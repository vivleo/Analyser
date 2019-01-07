package tla.analyseur;

import java.util.Stack;

public class SymbolStack extends Stack<Symbol> {

    public void push(Expression expr) {
        for (Symbol s : expr) {
            this.push(s);
        }
    }

    public void replace(Expression expr) {
        this.pop();
        for (Symbol s : expr) {
            this.push(s);
        }
    }
}
