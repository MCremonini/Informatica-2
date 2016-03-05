package compromeglio.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Monitoraggio {

	private Set<Bene> beni;
	private Map<Long, List<Rilevazione>> mappaRilevazioni;
	
	public Monitoraggio(Set<Bene> beni) {
		this.beni = beni;
		this.mappaRilevazioni = new HashMap<>();
		for(Bene b : beni) {
			mappaRilevazioni.put(b.getCodice(), new ArrayList<>());
		}
	}
	
	public Monitoraggio(Set<Bene> beni, List<Rilevazione> rilevazioni) {
		this.beni = beni;
		this.mappaRilevazioni = new HashMap<>();
		for(Bene b : beni) {
			
			List<Rilevazione> list = new ArrayList<>();
			for(Rilevazione r : rilevazioni) {
				if(r.getCodiceBene() == b.getCodice())
					list.add(r);
			}
			
			mappaRilevazioni.put(b.getCodice(), list);
		}
	}
	
	public void addRilevazione(Rilevazione rilevazione) {
		
		List<Rilevazione> list = mappaRilevazioni.get(rilevazione.getCodiceBene());
		if( list != null) {
			for(Rilevazione r : list) {
				if(r.getCodiceBene()==rilevazione.getCodiceBene() && 
				   r.getDate().equals(rilevazione.getDate()) &&
				   r.getLuogo().equals(rilevazione.getLuogo())) {
					throw new IllegalArgumentException("rilevazione già eseguita e registrata");
				}
			}
			
			list.add(rilevazione);
		}
	}
	
	public Bene getBene(long codice) {
		for(Bene b : beni) {
			if(b.getCodice() == codice)
				return b;
		}
		
		return null;
	}
	
	public Rilevazione getMigliorRilevazione(long codiceBene) throws NonRilevatoException {
		List<Rilevazione> list = getRilevazioni(codiceBene);
		if(list == null)
			throw new NonRilevatoException("nessuna rilevazione");
		
		Rilevazione min = list.get(0);
		for(Rilevazione r : list) {
			if(Float.compare(r.getPrezzo(), min.getPrezzo()) == -1 )
				min = r;
		}
		
		return min;
	}
	
	public float getPrezzoMedio(long codiceBene) throws NonRilevatoException {
		List<Rilevazione> list = getRilevazioni(codiceBene);
		if(list == null)
			throw new NonRilevatoException("nessuna rilevazione");
		
		return calcolaMedia(list);
	}
	
	public float getPrezzoMedio(Categoria c) throws NonRilevatoException {
		List<Rilevazione> list = new ArrayList<>();
		
		for(Bene b : beni) {
			if(b.getCategoria() == c) {
			
				List<Rilevazione> tmp = getRilevazioni(b.getCodice());
				
				if(tmp != null) {
					list.addAll(tmp);
				}
			}
		}
		
		if(list.isEmpty())
			throw new NonRilevatoException("nessuna rilevazione");
		
		return calcolaMedia(list);
	}
	
	private float calcolaMedia(List<Rilevazione> list) {
		float tot = 0;
		for(Rilevazione r : list) {
			tot += r.getPrezzo();
		}		
		
		return (tot / list.size());
	}
	
	public List<Rilevazione> getRilevazioni(long codice) {
		List<Rilevazione> list = mappaRilevazioni.get(codice);
		
		if(list == null || list.isEmpty())
			return null;
		
		return list;
	}
	
	public String toFullString() {
		StringBuilder sb = new StringBuilder();
			
		for(Map.Entry<Long, List<Rilevazione>> e : mappaRilevazioni.entrySet()) {
			
			for(Rilevazione r : e.getValue())  {
				sb.append(r.toString() + System.lineSeparator());
			}
				
		}
	
		return sb.toString();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		Map<Long, Bene> mappaBeni = new HashMap<>();
		
		for(Bene b : beni) {
			
			List<Rilevazione> list = mappaRilevazioni.get(b.getCodice());
			if(list != null && !list.isEmpty()) {
				
				mappaBeni.put(b.getCodice(), b);
				
				sb.append(b.toString() + System.lineSeparator());
			}
		}
		
		return sb.toString();
	}
}
