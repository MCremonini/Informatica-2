package pharmame.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import pharmame.model.Farmaco;

public class AifaCSVFarmacoReader implements FarmacoReader {

	@Override
	public Collection<Farmaco> readFrom(Reader inputReader)	throws BadFileFormatException, IOException {
		
		Collection<Farmaco> lista = new ArrayList<>();
		String line = null;
		
		try {
			BufferedReader myReader = new BufferedReader(inputReader);
			
			//scarto la prima riga
			myReader.readLine();
			
			while((line = myReader.readLine()) != null) {
				StringTokenizer tok = new StringTokenizer(line, ";\n\r");
				
				if(tok.countTokens() < 6)
					throw new BadFileFormatException("numero di campi per riga non valido");
				
				String principio = tok.nextToken().trim();
				checkIfValid(principio, "principio attivo");
				
				String gruppoequivalenza = tok.nextToken().trim();
				checkIfValid(gruppoequivalenza, "descrizione gruppo di equivalenza");
				
				String tmp = tok.nextToken().trim();
				StringTokenizer denom_conf = new StringTokenizer(tmp, "*");
				if(denom_conf.countTokens() != 2)
					throw new BadFileFormatException("formato di denominazione e confezione non corretto");
				String denominazione = denom_conf.nextToken().trim();
				checkIfValid(denominazione, "denominazione");
				String confezione = denom_conf.nextToken().trim();
				checkIfValid(confezione, "confezione");
				
				tmp = tok.nextToken().trim();
				checkIfValid(tmp, "prezzo");
				tmp = tmp.replace(",", ".");
				float prezzo = Float.parseFloat(tmp);
				
				String ditta = tok.nextToken().trim();
				checkIfValid(ditta, "ditta produttrice");
				
				tmp = tok.nextToken().trim();
				checkIfValid(tmp, "codice AIC");
				int codice = Integer.parseInt(tmp);
				
				
				lista.add(new Farmaco(codice, principio, gruppoequivalenza, denominazione, confezione, prezzo, ditta));
			
			}
		}
		catch(NoSuchElementException | NumberFormatException e) {
			throw new BadFileFormatException("formato dati non valido");
		}
		
		return lista;
	}
	
	private void checkIfValid(String nome, String token) throws BadFileFormatException {
		if(nome == null || nome.isEmpty())
			throw new BadFileFormatException(token);
	}

}
