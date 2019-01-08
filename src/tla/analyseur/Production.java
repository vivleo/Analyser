package tla.analyseur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public class Production {

	private Expression expression;
	private Symbol symbol;

	public static Production create(String s) {
		Production p = new Production();
		String[] ss = s.split("::");
		if (ss.length != 2) {
			return p;
		}
		p.symbol = Symbol.createVariable(ss[0].trim());
		p.expression = Expression.create(ss[1].trim());
		return p;
	}

	// Epsilon()
	public boolean epsilon() {
		return this.expression.equals("%epsilon");
	}

	// Premier()
	public static SymbolSet first(Symbol s) {
		SymbolSet S = new SymbolSet();
		//Ensemble règle production
		Map<String,SymbolSet> firstWithoutF=Controller.firstForFollow;
		ProductionSet prodSetTemp=Controller.P;

		//ensemble terminaux
		SymbolSet termTemp=Controller.T;

		//ArrayList contenant les symboles déjà traités


		//Création set de travail vide

		ProductionSet workingProd=new ProductionSet();
		//Remplissage du Set de travail
		for (int i=0;i<prodSetTemp.size();i++){
			if (prodSetTemp.get(i).symbol.equals(s))
			{
				workingProd.add(prodSetTemp.get(i));
				//System.out.println(i+" : Symbole :"+prodSetTemp.get(i).symbol.toString()+" , Expression : "+ prodSetTemp.get(i).expression.toString());
			} 
		}
		/*for (int x=0;x<prodSetTemp.size();x++) {
			System.out.println(prodSetTemp.get(x).symbol+" ¡!¡ "+ prodSetTemp.get(x).expression);
		}*/

		for (int i=0;i<workingProd.size();i++) {
			if (termTemp.contains(workingProd.get(i).expression.get(0)) ) {
				//System.out.println("Terminal : ajout de |"+workingProd.get(i).expression.get(0)+"|");
				S.add(workingProd.get(i).expression.get(0));
				continue;
			}
			if(containEpsilon(workingProd)) {
				S.add(Symbol.createEpsilon());

			}
			if (workingProd.get(i).getExpression().size()>1) {
				if (!workingProd.get(i).expression.get(0).isTerminal() && !workingProd.get(i).expression.get(0).isEpsilon()) {
					//Fusionner les 2 sets obtenus

					if (first(workingProd.get(i).getExpression().get(0)).contains("%epsilon") && !workingProd.get(i).expression.get(1).isTerminal() && !workingProd.get(i).expression.get(1).isEpsilon()){
						S.addAll(first(workingProd.get(i).expression.get(0)));
						S.addAll(first(workingProd.get(i).expression.get(1)));
					}

				}
			}
		}
		
		
		return S;
	}
	public static SymbolSet firstWithoutEpsilon(Symbol s) {
		SymbolSet S = new SymbolSet();
		//Ensemble règle production

		ProductionSet prodSetTemp=Controller.P;
		ProductionSet prodSet=new ProductionSet();
		//ensemble terminaux
		SymbolSet termTemp=Controller.T;

		//ArrayList contenant les symboles déjà traités
		for (int i=0;i<prodSetTemp.size();i++){
			boolean notEpsilon=false;
			for (int j=0;j<prodSetTemp.get(i).getExpression().size();j++) {
				if (prodSetTemp.get(i).getExpression().get(j).toString().equals("%epsilon")) {
					notEpsilon=true;
				}else{
					continue;
				}
			}
			if (!notEpsilon){
				prodSet.add(prodSetTemp.get(i));
			}
		}

		//Création set de travail vide

		ProductionSet workingProd=new ProductionSet();
		//Remplissage du Set de travail
		for (int i=0;i<prodSet.size();i++){
			if (prodSet.get(i).symbol.equals(s))
			{
				workingProd.add(prodSet.get(i));
				//System.out.println(i+" : Symbole :"+prodSet.get(i).symbol.toString()+" , Expression : "+ prodSet.get(i).expression.toString());
			}
			else{
				continue;
			}
		}
		/*for (int x=0;x<prodSetTemp.size();x++) {
			System.out.println(prodSetTemp.get(x).symbol+" ¡!¡ "+ prodSetTemp.get(x).expression);
		}*/

		for (int i=0;i<workingProd.size();i++) {
			if (termTemp.contains(workingProd.get(i).expression.get(0)) ) {
				//System.out.println("Terminal : ajout de |"+workingProd.get(i).expression.get(0)+"|");
				S.add(workingProd.get(i).expression.get(0));
				continue;
			}
			if(containEpsilon(workingProd)) {
				continue;
			}
			if (!workingProd.get(i).expression.get(0).isTerminal() && !workingProd.get(i).expression.get(0).isEpsilon() ) {
				//Fusionner les 2 sets obtenus
				S.addAll(firstWithoutEpsilon(workingProd.get(i).expression.get(0)));
			}
		}
		return S;
	}



	public static boolean containEpsilon(ProductionSet p) {
		boolean containEps=false;
		for (int i=0;i<p.size();i++){
			for (int j=0;j<p.get(i).expression.size();j++) {
				if (p.get(i).expression.get(j).equals("%epsilon")) {
					containEps=true;
					break;
				}
			}
		}
		return containEps;
	}
	// Suivant()
	public static SymbolSet follow(Symbol s, int a) {
		SymbolSet S = new SymbolSet();
		//Ensemble règle production
		ProductionSet prodSetTemp=Controller.P;
		//ensemble terminaux
		SymbolSet termTemp=Controller.T;
		Map<String,SymbolSet> firstBis=Controller.first;
		Map<String,SymbolSet> firstTree=Controller.firstForFollow;
		String sym=s.toString();
		/*System.out.println("sym : "+s);
		System.out.println("Premier symbole : "+prodSetTemp.get(0).getSymbol().toString());
		System.out.println(prodSetTemp.get(0).getSymbol().toString().equals(s.toString()));*/
		ArrayList<Production> workingRule=new ArrayList<>();
		//System.out.println("Symbole s : "+prodSetTemp.get(0).symbol.toString());
		//System.out.println(prodSetTemp.size());
		if (prodSetTemp.get(0).getSymbol().toString().equals(s.toString())){
			S.add(Symbol.createDollar());

		}
		//Set de travail
		for (int i=0;i<prodSetTemp.size();i++) {
			if (prodSetTemp.get(i).getExpression().size()==1 && prodSetTemp.get(i).getExpression().toString().equals("%epsilon")){
				continue;
			}
			else if(prodSetTemp.get(i).getExpression().contains(s)) {
				workingRule.add(prodSetTemp.get(i));
				//System.out.println(prodSetTemp.get(i).symbol+" :: "+prodSetTemp.get(i).expression);
			}
		}
		//Règle par règle
		for (int i=0;i<workingRule.size();i++) {
			Production tmp = workingRule.get(i);
			int index=tmp.getExpression().indexOf(s);

			if (index == tmp.getExpression().size()-1) {
				if (tmp.getExpression().get(index).toString().equals(tmp.getSymbol().toString())) {
					S.add(Symbol.createDollar());
				}else {
					if (a<50){
						S.addAll(follow(tmp.getSymbol(), a+1));
					}
					else{
						S.add(Symbol.createDollar());
					}
				}
			}
			else if(index<tmp.getExpression().size()-1) {
				if (tmp.getExpression().get(index+1).isTerminal() && !tmp.getExpression().get(index+1).isEpsilon()) {
					S.add(tmp.getExpression().get(index+1));
				}
				else if (firstBis.get(tmp.getExpression().get(index + 1).toString()).contains(Symbol.createEpsilon().toString()) && !tmp.getExpression().get(index+1).isTerminal() && !tmp.getExpression().get(index+1).isEpsilon()) {
					S.addAll(follow(tmp.getSymbol(),a+1));
					S.addAll(firstBis.get(tmp.getExpression().get(index+1).toString()));
				}
				else if (firstBis.get(tmp.getExpression().get(index + 1).toString()).contains(Symbol.createEpsilon().toString()) && tmp.getExpression().get(index+1).isTerminal() && !tmp.getExpression().get(index+1).isEpsilon()) {
					S.addAll(follow(tmp.getSymbol(),a+1));
					S.add(tmp.getExpression().get(index+1));
				}
				else if (!firstBis.get(tmp.getExpression().get(index+1).toString()).contains(Symbol.createEpsilon().toString())) {
					S.addAll(firstTree.get(tmp.symbol.toString()));
					//S.add(Symbol.createEpsilon());
				}
			}

		}

		return S;
	}

	public Expression getExpression() {
		return expression;
	}

	public Symbol getSymbol() {
		return symbol;
	}
}
