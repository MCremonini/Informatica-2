package galliacapocciona.model;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

public class CalcolatoreSeggiMisto extends CalcolatoreSeggi {

	public CalcolatoreSeggiMisto() {
	}

	@Override
	public Map<String, Integer> assegnaSeggi(int seggi,	List<Collegio> listaCollegi) {
		if(seggi == 0 || seggi < listaCollegi.size())
			throw new IllegalArgumentException("numero di seggi minore del numero di collegi");
		
		int seggiRimasti = seggi - listaCollegi.size();
		
		CalcolatoreSeggi calc = null;
		try {
			calc = getInstance("maggioritario");
		}
		catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		
		Map<String, Integer> mapSeggi = calc.assegnaSeggi(listaCollegi.size(), listaCollegi);
		
		if(seggiRimasti > 0) {
			try {
				calc = getInstance("proporzionale");
			}
			catch(NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			
			Map<String, Integer> mapSeggiRimasti = calc.assegnaSeggi(seggiRimasti, listaCollegi);
			
			for(Map.Entry<String, Integer> e : mapSeggiRimasti.entrySet()) {
				Integer currNumSeggi = mapSeggi.get(e.getKey());
				if(currNumSeggi != null)
					mapSeggi.put(e.getKey(), currNumSeggi + e.getValue());
			}
		}
		
		return mapSeggi;
	}

}
