package compromeglio.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.StringTokenizer;

import compromeglio.model.Bene;
import compromeglio.model.Categoria;

public class MyBeniReader implements BeniReader {

	private BufferedReader reader;
	
	public MyBeniReader(Reader reader) {
		this.reader = new BufferedReader(reader);
	}
	
	@Override
	public Set<Bene> caricaBeni() throws MalformedFileException, IOException {

		Set<Bene> beni = new HashSet<>();
		String line = null;
		
		try {
			
			while((line = reader.readLine()) != null) {
				
				StringTokenizer tokens = new StringTokenizer(line, "\t\r\n");
				
				if(tokens.countTokens() > 0) {
					long codice = Long.parseLong(tokens.nextToken().toUpperCase().trim());
					
					Categoria cat = Categoria.valueOf(tokens.nextToken().trim());
					String desc = tokens.nextToken().trim();
					if(desc.isEmpty())
						throw new MalformedFileException("formato file non valido: descrizione mancante");
					
					beni.add(new Bene(codice, desc, cat));
				}
			}
			
		}
		/*
		catch( NumberFormatException e1) {
		// se si volesse specializzare rispetto a Illegal
		}
		 */
		catch(NoSuchElementException | IllegalArgumentException e) {
			throw new MalformedFileException("formato di file non valido");
		}
		
		
		return beni;
	}

}
