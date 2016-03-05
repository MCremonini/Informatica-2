package zetabank.model;

public abstract class CalcolatoreRate {
	
	public static CalcolatoreRate of(SchemaFinanziamento schema) {
		switch (schema){
			case RATE_DECRESCENTI:  
				return new CalcolatoreRateDecrescenti();
			case RATE_COSTANTI:  
					return new CalcolatoreRateCostanti();
			default: throw new IllegalArgumentException(schema.toString());
		}
	}
	
	public static Finanziamento calcolaFinanziamento(TipoFinanziamento tipo, double capitaleDaFinanziare, int durataRichiesta, Periodicita periodicita) {
		if (durataRichiesta > tipo.getDurataMassima() ||  durataRichiesta < tipo.getDurataMinima()) 
				throw new IllegalArgumentException("durataRichiesta > tipo.getDurataMassima() ||  durataRichiesta < tipo.getDurataMinima()");
		//else
		CalcolatoreRate calcolatore = of(tipo.getCategoria());
		return calcolatore.calcola(tipo, capitaleDaFinanziare, durataRichiesta, periodicita);
	}

	/*
	 * Metodo factory che sintetizza una Rata nel caso di rate costanti di
	 * valore val.
	 * 
	 * @argument val il valore costante globale della rata
	 * 
	 * @argument n il numero totale delle rate
	 * 
	 * @argument k l'indice della rata corrente (1..n)
	 * 
	 * @argument i il tasso di interesse effettivo (numero puro, non
	 * percentuale)
	 */
	public static Rata split(double val, int n, int k, double i) {
		double quotaCapitale = val / Math.pow(1 + i, n - k + 1);
		double quotaInteressi = val - quotaCapitale;
		return new Rata(k, quotaCapitale, quotaInteressi);
	}
	
	public abstract Finanziamento calcola(TipoFinanziamento tipo, double capitaleDaFinanziare, int durataRichiesta, Periodicita periodicita);
	
}
