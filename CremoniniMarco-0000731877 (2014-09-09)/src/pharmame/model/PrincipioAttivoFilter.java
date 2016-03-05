package pharmame.model;

public class PrincipioAttivoFilter implements Filter<Farmaco> {

	private String key;
	
	public PrincipioAttivoFilter(String key) {
		this.key = key;
	}

	@Override
	public boolean filter(Farmaco element) {
		String tmpKey = key.toLowerCase();
		String str = element.getPrincipioAttivo().toLowerCase();
		if(str.indexOf(tmpKey) >= 0)
			return true;
		
		return false;
	}

}
