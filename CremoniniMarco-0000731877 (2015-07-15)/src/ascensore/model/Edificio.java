package ascensore.model;

public class Edificio {
	private int pianoMin, pianoMax;
	private String descrizione;
	private Ascensore ascensore;
	
	public Edificio(String descrizione, int pianoMin, int pianoMax, Ascensore ascensore) {
		this.descrizione = descrizione;
		this.pianoMin = pianoMin;
		this.pianoMax = pianoMax;
		this.ascensore = ascensore;
	}

	@Override
	public String toString() {
		return "Edificio " + descrizione + " [pianoMin=" + pianoMin + ", pianoMax=" + pianoMax
				+ ", ascensore " + ascensore.getTipo() + "]";
	}

	public int getPianoMin() {
		return pianoMin;
	}

	public int getPianoMax() {
		return pianoMax;
	}

	public String getDescrizione() {
		return descrizione;
	}
	
	public Ascensore getAscensore() {
		return ascensore;
	}	
}
