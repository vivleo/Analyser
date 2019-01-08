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
import java.util.stream.Collectors;

public class Controller {
	//P : ensemble de règle pour calculer le suivant
	public static ProductionSet P = new ProductionSet();
	public static SymbolStack S = new SymbolStack();
	//T : ensemble des terminaux
	public static SymbolSet T = new SymbolSet();

	//First
	public static Map<String,SymbolSet> first=new HashMap<String, SymbolSet>();

	public static Map<String,SymbolSet> firstForFollow=new HashMap<String, SymbolSet>();


	public static Map<String, SymbolSet> follow=new HashMap <>();
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
			for (int i=0;i<P.size();i++) {
				firstForFollow.put(P.get(i).getSymbol().toString(),Production.firstWithoutEpsilon(P.get(i).getSymbol()));
			}
			for (int i=0;i<P.size();i++) {
				follow.put(P.get(i).getSymbol().toString(),Production.follow(P.get(i).getSymbol(),0));
			}

			display(first);
			follow=removeEpsilon(follow);
			System.out.println();
			System.out.println("Follow : ");
			display(follow);
			//System.out.println();
			//System.out.println("Display first après remplissage");
			//display(first);

			//firstForFollow = copy(first);
			//firstForFollow=(HashMap)((HashMap)first).clone();
			//firstForFollow=null;
			//firstForFollow=(HashMap)((HashMap)first).clone();
			//compare(first,firstForFollow);
			//firstForFollow.putAll(first);
			//System.out.println();
			//System.out.println("First après copie");
			//compare(first,firstForFollow);
			//display(first);






			/*firstForFollow=removeEpsilon(firstForFollow);
			System.out.println();
			System.out.println("First après remove epsilon");
			compare(first,firstForFollow);
			display(first);
			System.out.println();
			System.out.println("First for follow : ");
			display(firstForFollow);
			System.out.println(firstForFollow.size());
			compare(first,firstForFollow);*/



			/*display(follow);
			firstForFollow=removeEpsilon(follow);
			System.out.println("After removing epsilon");
			display(follow);
			follow.put(P.get(3).getSymbol().toString(),Production.follow(P.get(3).getSymbol(),0));*/





		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void compare(Object ob1, Object ob2){
		if (ob1 == ob2){
			System.out.println("Shallow copy");
		}else
		{
			System.out.println("Deep copy");
		}
	}

	private Map<String, SymbolSet> copy(Map<String, SymbolSet> m) {
		HashMap<String, SymbolSet> ret = new HashMap<>();

		for (Map.Entry<String, SymbolSet> es : m.entrySet()) {
			ret.put(es.getKey(), es.getValue());
		}

		return ret;
	}

	public Map<String, SymbolSet> removeEpsilon(Map<String, SymbolSet> firstMap) {
		Map<String, SymbolSet> tmp =new HashMap<>();
		tmp=copy(firstMap);
		for(Entry<String,SymbolSet> entry : tmp.entrySet()) {
			Iterator<Symbol> ite=entry.getValue().iterator();
			while(ite.hasNext()){
				if (ite.next().toString().equals("%epsilon")){
					ite.remove();
				}
			}

		}

		return tmp;
	}

	public void display(Map<String, SymbolSet> first) {
		System.out.println("-------------------------------");

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
