package zetabank.model;

import java.util.ArrayList;
import java.util.List;

public class CalcolatoreRateDecrescenti extends CalcolatoreRate {

	@Override
	public Finanziamento calcola(TipoFinanziamento tipo, double capitaleDaFinanziare, int durataRichiesta, Periodicita periodicita) {

		if(tipo == null)
			throw new IllegalArgumentException("tipo finanziamento non valido");
		
		if(periodicita == null || !tipo.getPeriodicitaAmmesse().contains(periodicita))
			throw new IllegalArgumentException("periodicità non valida");
		
		if(capitaleDaFinanziare <= 0)
			throw new IllegalArgumentException("capitale non valido");
		
		if(durataRichiesta < tipo.getDurataMinima() || durataRichiesta > tipo.getDurataMassima())
			throw new IllegalArgumentException("durata non valida");
		
		if(tipo.getCategoria() != SchemaFinanziamento.RATE_DECRESCENTI)
			throw new IllegalArgumentException("schema finanziamento inconsistente");
		
		int numeroRate = durataRichiesta * periodicita.numeroRatePerAnno();
		double quotaCapitale = capitaleDaFinanziare / numeroRate;
		
		double tassoEffettivo = (tipo.getTassoAnnuoNominale() / periodicita.numeroRatePerAnno()) / 100;
		
		List<Rata> rate = new ArrayList<Rata>();
		
		for(int k = 0; k < numeroRate; k++) {
			double quotaInteressi = tassoEffettivo * (capitaleDaFinanziare - (k * quotaCapitale));
			rate.add(new Rata(k+1, quotaCapitale, quotaInteressi));
		}
		
		return new Finanziamento(tipo, durataRichiesta, periodicita, rate, capitaleDaFinanziare);
	}

}
