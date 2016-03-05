package pharmame.model;

public class GruppoEquivalenzaFilter implements Filter<Farmaco> {

	private String key;
	
	public GruppoEquivalenzaFilter(String key) {
		this.key = key;
	}

	@Override
	public boolean filter(Farmaco element) {
		String tmpKey = key.toLowerCase();
		String str = element.getGruppoEquivalenza().toLowerCase();
		if(str.indexOf(tmpKey) >= 0)
			return true;
		
		return false;
	}
}
