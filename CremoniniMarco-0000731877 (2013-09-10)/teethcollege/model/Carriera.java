package teethcollege.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Carriera {

	private PianoDiStudi pianoDiStudi;
	private Map<Long, List<Esame>> esamiSostenuti; 
	
	public Carriera(PianoDiStudi pianoDiStudi) {
		if(pianoDiStudi == null)
			throw new IllegalArgumentException("piano di studi non vaido");
		
		this.pianoDiStudi = pianoDiStudi;
		this.esamiSostenuti = new HashMap<>();
	}
	
	public Carriera(PianoDiStudi pianoDiStudi, List<Esame> listaEsami) {
		if(pianoDiStudi == null || listaEsami == null)
			throw new IllegalArgumentException("impossibile costruire Carriera");
		
		this.pianoDiStudi = pianoDiStudi;
		this.esamiSostenuti = new HashMap<>();
		
		for(Esame e : listaEsami) {
			List<Esame> lst = esamiSostenuti.get(e.getCodice());
			if(lst == null) {
				lst = new ArrayList<Esame>();
				esamiSostenuti.put(e.getCodice(), lst);
			}
			
			lst.add(e);
		}
	}
	
	public PianoDiStudi getPianoDiStudi() {
		return pianoDiStudi;
	}
	
	public List<Esame> getListaEsami(long codice) {
		return esamiSostenuti.get(codice);
	}
	
	public void addEsame(Esame esame) {
		
		if(esame == null)
			throw new IllegalArgumentException("parametro non valido");
		
		if(!pianoDiStudi.getInsegnamenti().contains(esame.getInsegnamento()))
			throw new IllegalArgumentException("insegnamento non previsto dalla carriera");
		
		List<Esame> lst = esamiSostenuti.get(esame.getCodice());
		if(getEsameSuperato(lst) != null)
			throw new IllegalArgumentException("esame già superato");
		
		if(lst == null) {
			lst = new ArrayList<Esame>();
			esamiSostenuti.put(esame.getCodice(), lst);
		}		
		lst.add(esame);
	}
	
	public List<Esame> getEsamiSostenuti() {
		List<Esame> ret = new ArrayList<Esame>();
		
		for(List<Esame> lst : esamiSostenuti.values()) {
			ret.addAll(lst);
		}
		
		return ret;
	}
	
	public List<Esame> getEsamiSuperati() {
		List<Esame> ret = new ArrayList<Esame>();
		
		for(List<Esame> lst : esamiSostenuti.values()) {
			Esame e = getEsameSuperato(lst);
			if(e != null)
				ret.add(e);
		}
		
		return ret;
	}
	
	public int getCreditiAcquisiti() {
		int crediti = 0;
		
		for(List<Esame> lst : esamiSostenuti.values()) {
			Esame e = getEsameSuperato(lst);
			if(e != null)
				crediti += e.getInsegnamento().getCfu();
		}
		
		return crediti;
	}
	
	private Esame getEsameSuperato(List<Esame> lst) {
		if(lst != null) {
			for(Esame e : lst) {
				if(e.getValoreVoto() >= 18) {
					return e;
				}
			}
		}		
		return null;
	}
	
	public double getMediaPesata() {
		List<Esame> esami = getEsamiSuperati();
		double media = 0.0;
		int crediti = 0;
		
		for(Esame e : esami) {
			media += (e.getValoreVoto() * e.getInsegnamento().getCfu());
			crediti += e.getInsegnamento().getCfu();
		}
		
		if(crediti > 0)
			media /= crediti;
		
		return media;
	}
	
	public String toString() {
		return pianoDiStudi.toString();
	}
	
	public String toFullString(boolean mostraDettagli) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(toString() + System.lineSeparator());
		
		List<Esame> lstAll = getEsamiSostenuti();
		sb.append("Esami sostenuti: " + lstAll.size() + System.lineSeparator());
		sb.append("Esami superati: " + getEsamiSuperati().size() + System.lineSeparator());
		sb.append("Totale crediti acquisiti: " + getCreditiAcquisiti() + System.lineSeparator());
		sb.append("Media voto: " + String.format("%.02f", getMediaPesata()) + System.lineSeparator());
		
		if(mostraDettagli) {
			for(Esame e : lstAll) {
				sb.append(e.toString() + System.lineSeparator());
			}
		}
		
		return sb.toString();
	}
}
