package galliacapocciona.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalcolatoreSeggiMaggioritario extends CalcolatoreSeggi {

	public CalcolatoreSeggiMaggioritario() {
	}

	@Override
	public Map<String, Integer> assegnaSeggi(int seggi,	List<Collegio> listaCollegi) {
		if(/*seggi == 0 ||*/ seggi != listaCollegi.size())
			throw new IllegalArgumentException("numero di seggi inconsistente");
		
		
		Map<String,Integer> mappaSeggi = new HashMap<String, Integer>();
		
		List<Partito> partiti = Collegio.generaListaPartiti(listaCollegi);
		
		for(Partito p : partiti)
			mappaSeggi.put(p.getNome(), 0);
		
		
		for(Collegio c : listaCollegi) {
			Partito p = c.getVincitore();
			Integer nSeggi = mappaSeggi.get(p.getNome());
			if(nSeggi != null)
				mappaSeggi.put(p.getNome(), nSeggi + 1);
		}
		
		return mappaSeggi;
	}

}
