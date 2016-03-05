package zetabank.model;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class Finanziamento {

	private double capitale;
	private int  durata;
	private Periodicita  periodicita;
	private List<Rata>  rate;
	private TipoFinanziamento tipoFinanziamento;
	private int numeroTotaleRate;
	private int ultimaRataPagata;
	
	
	public Finanziamento(TipoFinanziamento tipo, int durata, Periodicita periodicitaRimborso, List<Rata> rate, double capitaleFinanziato) {
		
		if(tipo == null)
			throw new IllegalArgumentException("tipo è nullo");
		this.tipoFinanziamento = tipo;
		
		if(durata <= 0)
			throw new IllegalArgumentException("durata non valida");
		this.durata = durata;
		
		if(periodicitaRimborso == null)
			throw new IllegalArgumentException("periodicita nulla");
		// lancia IllegalArgumentException in automatico
		Periodicita.valueOf(periodicitaRimborso.name());
		if(!tipo.getPeriodicitaAmmesse().contains(periodicitaRimborso))
			throw new IllegalArgumentException("periodicità non valida");
		this.periodicita = periodicitaRimborso;
		
		if(rate == null || rate.isEmpty())
			throw new IllegalArgumentException("lista rate non valida");
		this.rate = rate;
		
		if(capitaleFinanziato <= 0)
			throw new IllegalArgumentException("capitale non valido");
		this.capitale = capitaleFinanziato;
		
		/*if(numeroTotaleRate <= 0)
			throw new IllegalArgumentException("numero di rate non valido");
		this.numeroTotaleRate = numeroTotaleRate;
		
		this.ultimaRataPagata = ultimaRataPagata;*/
	}
	
	public double getCapitaleFinanziato() {
		return capitale;
	}
	
	public int getDurata() {
		return durata;
	}
	
	public Periodicita getPeriodicitaRimborso() {
		return periodicita;
	}
	
	public List<Rata> getRate() {
		return rate;
	}
	
	public TipoFinanziamento getTipoFinanziamento() {
		return tipoFinanziamento;
	}
	
	public double sommaTotaleInteressi() {
		double totaleInteressi = 0;
		
		for(Rata r : rate) {
			totaleInteressi += r.getQuotaInteressi();
		}
		
		return totaleInteressi;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.ITALY);
		sb.append("Capitale finanziato: " + format.format(capitale) + System.lineSeparator());
		sb.append("Durata: " + durata + System.lineSeparator());
		sb.append("Periodicità: " + periodicita.toString() + System.lineSeparator());
		sb.append("Tipo di finanziamento: " + tipoFinanziamento.toString() + System.lineSeparator());
		sb.append("Numero totale di rate: " + numeroTotaleRate + System.lineSeparator());
		sb.append("Ultima rata pagata: " + ultimaRataPagata + System.lineSeparator());
		for(Rata r : rate)
			sb.append(r.toString() + System.lineSeparator());
		
		return sb.toString();
	}
}
