package tla.analyseur;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Controller {
	//P : ensemble de règle pour calculer le suivant
	public static ProductionSet P = new ProductionSet();
	public static SymbolStack S = new SymbolStack();
	//T : ensemble des terminaux
	public static SymbolSet T = new SymbolSet();

	//First
	public static HashMap<String,SymbolSet> first=new HashMap<String, SymbolSet>();

	public static HashMap<String,SymbolSet> firstForFollow=new HashMap<String, SymbolSet>();


	public static HashMap<String, SymbolSet> follow=new HashMap <>();
	private Table A = new Table();
	private String E = "";

	public Controller(String filename) {
		loadGrammar(filename);
		makeTable();
	}

	private void makeTable() {
		// TODO
	}

	private void loadGrammar(String filename) {
		try (BufferedReader r = new BufferedReader(new FileReader(filename))) {
			for (String s : r.readLine().trim().split(" ")) {
				T.add(s);
				//System.out.print(" | T : "+ s);
			}
			for (String line; (line = r.readLine()) != null; ) {
				P.add(line);
				//System.out.println("P : "+line);
			}
			//System.out.println(P.get(0));
			//Map <Symbol, SymbolSet> tmp=new HashMap<>();
			System.out.println("First : ");
			for (int i=0;i<P.size();i++) {
				first.put(P.get(i).getSymbol().toString(),Production.first(P.get(i).getSymbol()));
			}
			display(first);
			firstForFollow=deleteEpsilon(first);
			/*for (int i=0;i<P.size();i++) {
				System.out.println("OK");
				follow.put(P.get(i).getSymbol().toString(),Production.follow(P.get(i).getSymbol(),0));
			}*/
			follow.put(P.get(3).getSymbol().toString(),Production.follow(P.get(3).getSymbol(),0));
			//System.out.println("Follow : ");
			//display(follow);


			//Iterator <Symbol> testite=P.iterator();
			//System.out.println(test.isEmpty());
			//while(testite.hasNext()) {
			//	testite.next();
			//}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public HashMap<String,SymbolSet> deleteEpsilon(HashMap<String, SymbolSet> first) {
		HashMap<String, SymbolSet> tmp =first;
		for (Entry<String, SymbolSet> entry : tmp.entrySet()) {
			String key = entry.getKey();
			Iterator<Symbol> value = entry.getValue().iterator();
			while (value.hasNext()) {
				if (value.next().toString().equals("%epsilon")) {
					value.remove();
				}
			}
		}
		return tmp;
	}

	public void display(Map<String, SymbolSet> first) {
		System.out.println("-------------------------------");
		System.out.println();
		for(Entry<String,SymbolSet> entry : first.entrySet()) {
			String key =  entry.getKey();
			System.out.println("Symbole: [" + key +"]");

			Iterator <Symbol> value =  entry.getValue().iterator();
			while(value.hasNext()) {
				System.out.print("|"+value.next());
			}
			System.out.println("|");
			System.out.println("===========================");
		}
	}

	public void printError() {
		System.out.println(E);
	}

	public boolean parse(String filename) {
		// TODO
		return true;
	}
}
