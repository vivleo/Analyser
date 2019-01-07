package tla.analyseur;

import java.util.ArrayList;

public class Expression extends ArrayList<Symbol> {

    public static Expression create(String s) {
        Expression expr = new Expression();
        for (String ss : s.split(" ")) {
            if (Controller.T.contains(ss)) {
                expr.add(Symbol.createTerminal(ss));
            } else {
                expr.add(Symbol.createVariable(ss));
            }
        }
        return expr;
    }
}
