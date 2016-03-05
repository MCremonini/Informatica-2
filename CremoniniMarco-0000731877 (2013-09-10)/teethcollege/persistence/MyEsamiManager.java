package teethcollege.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import teethcollege.model.Esame;
import teethcollege.model.Insegnamento;

public class MyEsamiManager implements EsamiManager {

	DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT);
	
	@Override
	public List<Esame> caricaEsami(Reader reader, Map<Long, Insegnamento> mappaInsegnamenti) throws IOException {

		
		List<Esame> esami = new ArrayList<Esame>();
		String line = null;
		
		BufferedReader myReader = null;
		
		try {
			
			myReader = new BufferedReader(reader);
			
			while((line = myReader.readLine()) != null) {
				
				StringTokenizer tokens = new StringTokenizer(line, ",\n\r");
				if(tokens.countTokens() != 3)
					throw new MalformedFileException("numero inaspettato di campi attesi");
				
				String tok = tokens.nextToken().trim();
				if(tok == null || tok.isEmpty())
					throw new MalformedFileException("codice non valido");
				long codice = Long.parseLong(tok);
				
				Insegnamento ins = mappaInsegnamenti.get(codice);
				if(ins == null)
					throw new MalformedFileException("insegnamento non valido");
				
				
				tok = tokens.nextToken().trim();
				if(tok == null || tok.isEmpty())
					throw new MalformedFileException("voto non valido");
				int voto = Integer.parseInt(tok);
				
				
				tok = tokens.nextToken().trim();
				if(tok == null || tok.isEmpty())
					throw new MalformedFileException("data non valida");
				
				Date date = formatter.parse(tok);

				esami.add(new Esame(ins, voto, date));		
			}
			
			myReader.close();
		}
		catch(NoSuchElementException | NumberFormatException | ParseException e) {
			
			if(myReader != null)
				myReader.close();
					
			throw new MalformedFileException("formati dati non corretto");	
		}
		
		
		
		return esami;		
	}

	@Override
	public void salvaEsami(Writer writer, List<Esame> esami) throws IOException {

		PrintWriter myWriter = new PrintWriter(writer);
		for(Esame e : esami) {
			myWriter.println(e.toCanonicalString());
		}
		myWriter.close();
	}

}
