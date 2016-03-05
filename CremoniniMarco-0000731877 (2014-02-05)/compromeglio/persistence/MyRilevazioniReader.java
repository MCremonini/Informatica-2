package compromeglio.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;



import compromeglio.model.Bene;
import compromeglio.model.Rilevazione;

public class MyRilevazioniReader implements RilevazioniReader {

	private BufferedReader reader;
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	public MyRilevazioniReader(Reader reader) {
		this.reader = new BufferedReader(reader);
	}
	
	
	@Override
	public List<Rilevazione> caricaRilevazioni(Map<Long, Bene> beni) throws IOException {
		List<Rilevazione> rilevazioni = new ArrayList<>();
		String line = null;
		
		try {
			
			while((line = reader.readLine()) != null) {
				
				StringTokenizer tokens = new StringTokenizer(line, ",\r\n");
				
				long codice = Long.parseLong(tokens.nextToken().toUpperCase().trim());
				Bene bene = beni.get(codice);
				if(bene == null)
					throw new MalformedFileException("formato file non valido: codice bene non trovato");
				
				
				String luogo = tokens.nextToken().trim();
				if(luogo.isEmpty())
					throw new MalformedFileException("formato file non valido: luogo mancante");
				
				String tmp = tokens.nextToken().trim();
				Date data = formatter.parse(tmp);
				
				
				float prezzo = Float.parseFloat(tokens.nextToken().trim());
				
				
				
				rilevazioni.add(new Rilevazione(bene, luogo, data, prezzo));
			}
			
		}
		/*
		catch( NumberFormatException e1) {
		// se si volesse specializzare rispetto a Illegal
		}
		 */
		catch(NoSuchElementException | NumberFormatException | ParseException e) {
			throw new MalformedFileException("formato sbagliato");
		}
		
		
		return rilevazioni;
	}

}
