package pharmame.model;

import java.util.ArrayList;
import java.util.Collection;

public class MyFarmacoFilterFactory implements FarmacoFilterFactory {

	@Override
	public Filter<Farmaco> get(String name, String searchKey) {
		if(name.compareToIgnoreCase("Principio Attivo") == 0) {
			return new PrincipioAttivoFilter(searchKey);
		}
		else if(name.compareToIgnoreCase("Nome") == 0) {
			return new NomeFilter(searchKey);
		}
		else if(name.compareToIgnoreCase("Gruppo Equivalenza") == 0 ) {
			return new GruppoEquivalenzaFilter(searchKey);
		}
		
		throw new IllegalArgumentException("invalid parameter");
	}

	@Override
	public Collection<String> getNames() {
		Collection<String> list = new ArrayList<>();
		list.add("Principio Attivo");
		list.add("Gruppo Equivalenza");
		list.add("Nome");
		return list;
	}

}
