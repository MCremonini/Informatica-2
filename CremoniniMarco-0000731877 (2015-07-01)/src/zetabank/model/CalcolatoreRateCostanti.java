package zetabank.model;

import java.util.ArrayList;
import java.util.List;

public class CalcolatoreRateCostanti extends CalcolatoreRate {

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
		
		if(tipo.getCategoria() != SchemaFinanziamento.RATE_COSTANTI)
			throw new IllegalArgumentException("schema finanziamento inconsistente");
		
		int numeroRate = durataRichiesta * periodicita.numeroRatePerAnno();	
		double tassoEffettivo = (tipo.getTassoAnnuoNominale() / periodicita.numeroRatePerAnno()) / 100;
		
		double denom = Math.pow((1 + tassoEffettivo), numeroRate) - 1;
		double quotaRata = (1 + (1 / denom)) * tassoEffettivo * capitaleDaFinanziare;
		
		List<Rata> rate = new ArrayList<Rata>();
		
		for(int k = 1; k <= numeroRate; k++) {
			rate.add(CalcolatoreRate.split(quotaRata, numeroRate, k, tassoEffettivo));
		}
		
		return new Finanziamento(tipo, durataRichiesta, periodicita, rate, capitaleDaFinanziare);
	}

}
