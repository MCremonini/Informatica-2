package doc.model;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class DentoStrategiaConfigurabile extends DentoStrategiaBase {
	
	private static final long serialVersionUID = 1L;
	
	private Map<String, Integer> punteggiBevande;
	private int ore;
	
	public DentoStrategiaConfigurabile(String nome, Map<String, Integer> punteggiBevande, int ore) {
		super(nome);
		
		if(nome == null || nome.trim().isEmpty())
			throw new IllegalArgumentException("invalid name");
		
		if(punteggiBevande == null)
			throw new IllegalArgumentException("map null");
		
		if(ore <= 0)
			throw new IllegalArgumentException("ore non valide");
		
		this.punteggiBevande = punteggiBevande;
		this.ore = ore;
	}
	
	@Override
	public float calcolaSconto(Collection<Acquisto> acquisti, Cliente cliente) {

		int numAcquisti = 0;
		int totPunteggio = 0;

		// ore to msec
		long msecOre = ore * 3600000;
		Date bound = new Date();
		bound.setTime(getNow().getTime() - msecOre);
		
		for(Acquisto a : acquisti) {
			if(cliente.equals(a.getCliente()) && bound.before(a.getData())) {
				numAcquisti++;
				
				Integer punteggio = punteggiBevande.get(a.getBevanda().getNome());
				if(punteggio != null) {
					totPunteggio += punteggio;
				}
			}
		}
		
		return  ((numAcquisti > 0) ? ((float)totPunteggio / numAcquisti /** 100*/) : 0);
	}
	
	public int getOre() {
		return ore;
	}
	
	public Map<String, Integer> getPunteggiBevande() {
		return punteggiBevande;
	}
}
