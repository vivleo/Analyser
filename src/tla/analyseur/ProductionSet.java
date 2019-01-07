package tla.analyseur;

import java.util.ArrayList;

public class ProductionSet extends ArrayList<Production> {

    public void add(String s) {
        this.add(Production.create(s));
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Production p : this) {
            s.append(p.toString()).append("\n");
        }
        return s.toString();
    }
}
