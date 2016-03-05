package galliacapocciona.persistence;

import galliacapocciona.model.Collegio;
import galliacapocciona.model.Partito;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class MyCollegiReader implements CollegiReader {

	@Override
	public List<Collegio> caricaElementi(Reader reader) throws IOException,	BadFileFormatException {
		
		List<Collegio> collegi = new ArrayList<Collegio>();
		String line = null;
		
		BufferedReader myReader = new BufferedReader(reader);
		
		try {
			List<String> nomiPartiti = new ArrayList<>();
			
			if((line = myReader.readLine()) != null) {
				
				StringTokenizer tokens = new StringTokenizer(line, "\t\n\r");
				
				while(tokens.hasMoreTokens()) {
					String nome = tokens.nextToken().trim();
					if(!nome.isEmpty())
						nomiPartiti.add(nome);
				}
			}
			else
				throw new BadFileFormatException("empty file");	
			
				
			while((line = myReader.readLine()) != null) {
				StringTokenizer tokens = new StringTokenizer(line, "\t\n\r");
				
				if(tokens.countTokens() != (nomiPartiti.size() + 1))
					throw new BadFileFormatException("numero di campi numerici non corretto");
				
				int numCollegio = Integer.parseInt(tokens.nextToken().trim());
				
				SortedSet<Partito> partiti = new TreeSet<>();
				int index = 0;
				while(tokens.hasMoreTokens()) {
					int voti = Integer.parseInt(tokens.nextToken().trim());
					partiti.add(new Partito(nomiPartiti.get(index), voti));
					index++;
				}
				
				collegi.add(new Collegio(String.format("Collegio %d", numCollegio), partiti));
			}
			
		}
		catch(NumberFormatException | NoSuchElementException e) {
			throw new BadFileFormatException("formato file non corretto");
		}
		
		return collegi;
	}

}
