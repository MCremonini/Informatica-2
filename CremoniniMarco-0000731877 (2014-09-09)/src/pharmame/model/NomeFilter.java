package pharmame.model;

public class NomeFilter implements Filter<Farmaco> {

	private String key;
	
	public NomeFilter(String key) {
		this.key = key;
	}
	
	@Override
	public boolean filter(Farmaco element) {
		String tmpKey = key.toLowerCase();
		String name = element.getNome().toLowerCase();
		if(name.indexOf(tmpKey) >= 0)
			return true;
		
		return false;
	}

}
