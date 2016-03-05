package rs.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MyStimaSinteticaFactory implements StimaSinteticaFactory {

  @Override
  public StimaSintetica create(Collection<StimaRischio> previsioni) {
    
    if (previsioni == null || previsioni.isEmpty())
      throw new IllegalArgumentException("list of previsioni not valid");
    
    /////////////////////////////////////////////////////////////////////////////////////
    // TMPTMP: verificare come esegue lui questo controllo e documentare
    List<StimaRischio> list = new ArrayList<StimaRischio>(previsioni);
    StimaRischio first = list.get(0);
    
    for (int i = 0; i < list.size(); i++) {
      StimaRischio sr = list.get(i);
      if (first.getCitta().compareToIgnoreCase(sr.getCitta()) != 0 || first.getAnno() != sr.getAnno()) {
        // TMPTMP: è giusta questa eccezzione (documentare)
        throw new IllegalArgumentException("not all items in previsioni are of the same city or year");
      }
      
      for (int j = i + 1; j < list.size(); j++) {
        StimaRischio sr_tmp = list.get(j);
      
        if (sr.compareTo(sr_tmp) == 0) {
          // TMPTMP: è giusta questa eccezzione (documentare)
          throw new IllegalArgumentException("some items in previsioni are repetead");
        }
      }
    }
    //////////////////////////////////////////////////////////////////////////////////////
    
    // con i controlli precedenti sono sicuro che le StimeRischio sono dello stesso anno
    
    /////////////////////////////////////////
    // sort per mese 
    /////////////////////////////////////////
    // TMPTMP: verificare come esegue lui questo controllo e documentare    
    Collections.sort(list);
    
    
    /////////////////////////////////////////
    // calcolo idei giorni della durata di una stima
    /////////////////////////////////////////
    boolean bBisestile = (first.getAnno() % 4 == 0) ? true : false; 
    
    
    int [] giorniStima = new int[list.size()];
    int iStima = 0;
    int iGiorni = 0;
    int iMonth = 0;
    int iTotPercStime = 0;
    int prevRischio = 0;
    
    StimaRischio sr = list.get(iStima);
    for (; iMonth <= sr.getMese().toGregorianCalendarMonth(); iMonth++) {
      giorniStima[iGiorni] += MonthDays.values()[iMonth].getDays(bBisestile);
    }
    
    iStima++;
    if( iStima<list.size())
    {
    	prevRischio = sr.getRischio();
	    sr = list.get(iStima);
	    boolean bBreak = false;
	    for ( ; iMonth < Month.values().length && !bBreak; iMonth++) {
	      
	      if( iMonth >= sr.getMese().toGregorianCalendarMonth() ) {
	        iStima++;
	        
	        iTotPercStime += (prevRischio * giorniStima[iGiorni]);
        	
        	iGiorni++;
        	prevRischio = sr.getRischio();
        	
        	giorniStima[iGiorni] += MonthDays.values()[iMonth].getDays(bBisestile);
        	
	        if( iStima < list.size() ) {
	        	sr = list.get(iStima);
	        	
	        }
	        else
	        	bBreak = true;
	      }
	      else
	    	  giorniStima[iGiorni] += MonthDays.values()[iMonth].getDays(bBisestile);
	    }
      
    }
    for ( ; iMonth < Month.values().length; iMonth++) {
      giorniStima[iGiorni] += MonthDays.values()[iMonth].getDays(bBisestile);
    }
    
    iTotPercStime += (sr.getRischio() * giorniStima[iGiorni]);
    int iNumGiorni = bBisestile ? 366 : 365;
    int iProbabilitaFurto = Math.round(((float)iTotPercStime) / ((float)iNumGiorni));
    
    String testo;
    if(iProbabilitaFurto < 3)
    	testo = "a rischio zero";
    else if(iProbabilitaFurto < 10)
    	testo = "a rischio minimo";
    else if(iProbabilitaFurto < 25)
    	testo = "a rischio medio-basso";
    else if(iProbabilitaFurto < 50)
    	testo = "a rischio medio";
    else if(iProbabilitaFurto < 75)
    	testo = "ad alto rischio";
    else
    	testo = "ad altissimo rischio";
    
    return new StimaSintetica(sr.getAnno(), sr.getCitta(), iProbabilitaFurto, testo );
  }

}
