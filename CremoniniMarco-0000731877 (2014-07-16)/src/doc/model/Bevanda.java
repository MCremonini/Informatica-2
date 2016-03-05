package doc.model;

import java.io.Serializable;
import java.util.Collection;

public class Bevanda implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private float prezzoMin;
	private float prezzoBase;
	private float prezzoMax;
	private DentoStrategia strategia;
	
	private final DentoStrategia defaultStrategia = new DefaultDentoStrategia(); 
	
	public Bevanda(String nome, float prezzoMin, float prezzoBase, float prezzoMax) {
		
		if(nome == null || nome.trim().isEmpty())
			throw new IllegalArgumentException("invalid name");
		
		if(prezzoMin < 0)
			throw new IllegalArgumentException("prezzo min non valido");
		
		if(prezzoMin > prezzoMax)
			throw new IllegalArgumentException("prezzo min e massimo scanbiati");
		
		if(prezzoMin > prezzoBase || prezzoMax < prezzoBase)
			throw new IllegalArgumentException("prezzo base out of range");
		
		this.nome = nome;
		this.prezzoBase = prezzoBase;
		this.prezzoMax = prezzoMax;
		this.prezzoMin = prezzoMin;
		this.strategia = null;
	}
	
	public String getNome() {
		return nome;
	}
	
	public float getPrezzo(Collection<Acquisto> acquisti, Cliente cliente) {
		DentoStrategia strategy = getStrategia();
		
		float sconto = strategy.calcolaSconto(acquisti, cliente);
		
		float prezzo = prezzoBase - (prezzoBase * sconto);
		
		return clipPrezzo(prezzo);
	}
	
	private float clipPrezzo(float prezzo) {
		if(Float.compare(prezzoMin, prezzo) > 0) 
			return prezzoMin;
		if(Float.compare(prezzoMax, prezzo) < 0) 
			return prezzoMax;
		return prezzo;
	}
	
	public float getPrezzoBase() {
		return prezzoBase;
	}
	
	public float getPrezzoMax() {
		return prezzoMax;
	}
	
	public float getPrezzoMin() {
		return prezzoMin;
	}
	
	public DentoStrategia getStrategia() {
		return (strategia != null) ? strategia : defaultStrategia;
	}
	
	public void setStrategia(DentoStrategia strategia) {
		this.strategia = strategia;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(nome);
		/*sb.append(nome + ":");
		sb.append(" Prezzo base: " + prezzoBase);
		sb.append(" Prezzo min: " + prezzoMin);
		sb.append(" Prezzo max: " + prezzoMax);*/
		
		return sb.toString();
	}
}
