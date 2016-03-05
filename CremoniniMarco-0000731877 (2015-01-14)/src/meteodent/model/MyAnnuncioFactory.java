package meteodent.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

public class MyAnnuncioFactory implements AnnuncioFactory {

	public MyAnnuncioFactory() {
	
	}
	
	@Override
	public Annuncio crea(Collection<Previsione> previsioni) {

		if(previsioni == null || previsioni.isEmpty())
			throw new IllegalArgumentException("lista previsioni non valida");
		
		// I requisito: previsioni riferite alla stessa località / data
		Date date = null;
		String localita = null;
		for(Previsione p : previsioni) {
			if(localita == null) {
				localita = p.getLocalita();
				date = p.getDataOra();
			}
			else {
				if(!localita.equalsIgnoreCase(p.getLocalita())) {
					throw new IllegalArgumentException("lista previsioni comprende località diverse");
				}
				
				if(!DateUtils.isSameDayDate(date, p.getDataOra())) {
					throw new IllegalArgumentException("lista previsioni comprende date diverse");
				}
			}
		}
		
		// II requisito: generazione annuncio
		
		GregorianCalendar cal = new GregorianCalendar();
		float probPioggia = 0;
		ArrayList<Previsione> lstPrevisioni = new ArrayList<Previsione>(previsioni);
		int numPrevisioni = lstPrevisioni.size();
		int totOre = 0;
		
		if(numPrevisioni > 1) {			
			for(int i = 1; i < numPrevisioni; i++) {
				Previsione curr = lstPrevisioni.get(i);
				Previsione prec = lstPrevisioni.get(i - 1);
				
				cal.setTime(curr.getDataOra());
				int endHour = cal.get(GregorianCalendar.HOUR_OF_DAY);
				cal.setTime(prec.getDataOra());
				int startHour = cal.get(GregorianCalendar.HOUR_OF_DAY);
				
				int diffOre = endHour - startHour;
				probPioggia += (prec.getProbabilitaPioggia() * diffOre);
				totOre += diffOre;
			}
		}
		
		Previsione last = lstPrevisioni.get(numPrevisioni - 1);
		
		cal.setTime(last.getDataOra());
		int diffOre = 24 - cal.get(GregorianCalendar.HOUR_OF_DAY);
		probPioggia += (last.getProbabilitaPioggia() * diffOre);
		totOre += diffOre;
		
		probPioggia /= totOre;
		String testo;
		if(probPioggia < 5)
			testo = "Giornata serena";
		else if(probPioggia < 10)
			testo = "Giornata quasi serena";
		else if(probPioggia < 25)
			testo = "Giornata con possibili piogge sparse";
		else if(probPioggia < 50)
			testo = "Giornata con tempo variabile";
		else if(probPioggia < 65)
			testo = "Giornata con piogge diffuse";
		else if(probPioggia < 80)
			testo = "Giornata con pioggia";
		else
			testo = "Giornata con piogge insistenti e generalizzate";

		return new Annuncio(cal.getTime(), last.getLocalita(), Math.round(probPioggia), testo );
	}

}
